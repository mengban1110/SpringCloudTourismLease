package cn.doo.framework.entity.caigou;

import lombok.Data;

/**
 * @author 闫佳溢
 * @desc
 * @time 2021-05-31-16:36
 */
@Data
public class Employee {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String avater; //头像
    private Integer level;//人事等级

}
