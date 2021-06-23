package cn.doo.framework.entity;

import cn.doo.framework.entity.pojo.GoodstypePojo;
import lombok.Data;

@Data
public class Repertory {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Integer count;

    /**
     *
     */
    private Integer money;

    /**
     *
     */
    private Integer unitprice;

    /**
     *
     */
    private Integer unitdeposit;

    /**
     *
     */
    private GoodstypePojo type;

}

