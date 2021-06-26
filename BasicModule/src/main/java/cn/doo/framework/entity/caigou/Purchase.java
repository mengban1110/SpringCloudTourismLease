package cn.doo.framework.entity.caigou;

import lombok.Data;

/**
 * @author 闫佳溢
 * @desc
 * @time 2021-05-31-19:20
 */
@Data
public class Purchase {
    private String purid;
    private Facility facility;
    private String pursum;
    private String purstart;
    private String purend;
    private Employee employee;
    private String purpriceall;
    private String purstate;//采购单状态

}
