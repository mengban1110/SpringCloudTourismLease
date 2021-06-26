package cn.doo.zuul.config;

import cn.doo.framework.utils.DooUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
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
                String returnContext = "第" + count + "次非法访问接口,超过五次将封禁1小时";
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
        String url = context.getRequest().getRequestURI().toLowerCase();

//        //ip校验
//        if (ipCheck(request)) {
//            System.out.println("本次请求ip地址 没有黑名单中 正常访问...");
//        } else {
//            //封禁ip处理
//            checkIp(context, response);
//            return false;
//        }

        //过滤登录
        if ("/api/login/login/sendcode".equals(url) || "/api/login/login/login".equals(url)) {
            return true;
        }

        //过滤下载图片
        if (url.contains("/api/personnel/employee/img")) {
            return true;
        }

        //过滤下载收据
        if (url.contains("/api/lease/download")) {
            return true;
        }

        //token校验
        if (tokenCheck(request)) {
            return true;
        } else {
            returnResponse(context, response, "未登录");
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
        //获取本次请求发送来的请求头
        String myToken = request.getHeader("token");
        String myUsername = request.getHeader("username");

        Map<String, Object> tokenVerifyResult = getTokenVerifyResult(new TokenVerify(myUsername, myToken), redisUtil);

        System.out.println("tokenVerifyResult = " + tokenVerifyResult);
        if (tokenVerifyResult != null) {
            return false;
        }
        return true;
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
        context.setResponseBody("{\"code\":-1,\"msg\":\"" + info + "\"}");
    }

    /**
     * 获取真实ip
     *
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

    /**
     * 获取token校验  Redis : token:mengban
     *
     * @param tokenVerify
     * @return
     */
    public static Map<String, Object> getTokenVerifyResult(TokenVerify tokenVerify, RedisUtil jedis) {
        Boolean hasKey = jedis.hasKey(tokenVerify.getRedisKey());
        //token过期了
        if (!hasKey) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        //获取token 进行对比是否相同
        String token = jedis.get(tokenVerify.getRedisKey());
        boolean result = StringUtils.equals(token, tokenVerify.getToken());
        if (!result) {
            return DooUtils.print(-1, "未登录", null, null);
        }
        return null;
    }
}
