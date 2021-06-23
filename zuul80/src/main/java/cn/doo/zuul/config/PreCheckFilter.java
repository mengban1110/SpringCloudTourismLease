package cn.doo.zuul.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author 梦伴
 * @desc
 * @time 2021-06-22-0:54
 */
public class PreCheckFilter extends ZuulFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        /****/RequestContext ctx = RequestContext.getCurrentContext();
        String url = ctx.getRequest().getRequestURI().toLowerCase();
        System.out.println("本次发起的请求url:" + url);
        // 这里判断url逻辑
        if (url.contains("/api")) {
            System.out.println("本次请求的url：" + url + " 满足条件可以执行");
            return true;
        }

        //请求ip
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String remoteAddr = getIpAddress(request);

        if (redisUtil.hasKey("blackIp:" + remoteAddr)) {
            int count = Integer.parseInt(redisUtil.get("blackIp:" + remoteAddr));
            if (count != 0 && count < 5) {
                redisUtil.setEx("blackIp:" + remoteAddr, String.valueOf(count + 1), 10, TimeUnit.MINUTES);
                //设置返回信息
                String returnContext = "第"+count+"次非法访问接口,超过五次将封禁1小时";
                returnResponse(context, response, returnContext);
            } else {
                redisUtil.setEx("blackIp:" + remoteAddr, "0", 1, TimeUnit.HOURS);
                System.out.println("访问非法路径过多!!!!已经进行封禁处理");
                System.out.println("本次请求的url:" + url + " 不满足条件不在继续后续的过滤执行了");
                //设置返回信息
                String returnContext = "您已被封禁1小时,请稍后再试";
                returnResponse(context, response, returnContext);
            }
        } else {
            redisUtil.setEx("blackIp:" + remoteAddr, "1", 10, TimeUnit.MINUTES);
            //设置返回信息
            String returnContext = "请勿非法访问不存在的接口!超过五次将封禁1小时!";
            returnResponse(context, response, returnContext);
        }

        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();


        //ip校验
        if (ipCheck(request)) {
            System.out.println("本次请求ip地址 没有黑名单中 正常访问...");
        } else {
            //封禁ip处理
            checkIp(context, response);
            return false;
        }

        //token校验
        if (tokenCheck(request)) {
            return true;
        } else {
            String returnContext = "本次请求的token值不对，访问失败！";
            returnResponse(context, response, returnContext);
            return false;
        }

    }

    /**
     * ip校验
     *
     * @param request
     * @return
     */
    private boolean ipCheck(HttpServletRequest request) {
        //请求ip
        String remoteAddr = getIpAddress(request);
        //ip校验
        Boolean hasKey = redisUtil.hasKey("blackIp:" + remoteAddr);
        System.out.println("hasKey = " + hasKey);
        return !hasKey;
    }


    /**
     * token校验
     *
     * @param request
     * @return
     */
    private boolean tokenCheck(HttpServletRequest request) {
        //假定用户登陆之后java 后台产生的token值就是wyh
        String access_token = "wyh";
        //获取本次请求发送来的请求头
        String myToken = request.getHeader("myToken");
        System.out.println("本次请求的myToken:" + myToken);
        //判断请求头token是否一致，如果一致就本次过滤通过然后执行调用hystrix-8001查询获取数据
        if (access_token.equals(myToken)) {
            System.out.println("本次请求的token正确，放行了...." + "然后就会执行本次URL请求，然后你就可以获取到数据");
            return true;
        }
        return false;
    }

    /**
     * 封禁ip处理
     *
     * @param context
     * @param response
     */
    private void checkIp(RequestContext context, HttpServletResponse response) {
        //请求ip
        String remoteAddr = getIpAddress(context.getRequest());

        String keyResult = redisUtil.get("blackIp:" + remoteAddr);
        Long expire = redisUtil.getExpire("blackIp:" + remoteAddr, TimeUnit.MINUTES);
        if ("0".equals(keyResult)) {
            String returnContext = "您的Ip已被封禁，访问失败！剩余结束时间封禁时间 " + expire.toString() + " 分钟";
            returnResponse(context, response, returnContext);
        }
    }

    /**
     * 返回信息
     *
     * @param context
     * @param response
     * @param info
     */
    private void returnResponse(RequestContext context, HttpServletResponse response, String info) {
        //设置响应编码集为UTF-8
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //对该请求禁止路由，也就是禁止访问下游服务
        context.setSendZuulResponse(false);
        //设定responseBody供本次进行响应返回
        context.setResponseBody("{\"status\":500,\"message\":\"" + info + "\"}");
    }

    /**
     * 获取真实ip
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getIpAddress(request);
        }
        return ip;
    }
}
