package cn.doo.framework.entity.caigou;

import lombok.Data;

/**
 * @desc   实体类:Facility
 * @author CHF
 * @time   2021--05--31
 *
 */
@Data
public class Facility {
	private Integer facid;//器械id
	private String facname;//器械名称
	private Species species;//种类
	private String facprice;//器械单价
	private String facimage;//器械图片
	private String facsupplier;//供应商
	private String factext;//器械描述
}
