package cn.doo.framework.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 梦伴
 * @desc
 * @time 2021-06-27-10:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Verify {
    private String email;
    private String code;
    private Integer type;
    private String uuid;
}
