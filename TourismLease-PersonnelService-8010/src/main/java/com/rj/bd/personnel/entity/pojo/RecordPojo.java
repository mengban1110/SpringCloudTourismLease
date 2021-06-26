package com.rj.bd.personnel.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Desc: Record 的 Pojo
 * @Auther: LuHoYn
 * @Date: 2021-06-26 21:16
 */
@Data
@TableName(value = "record")
public class RecordPojo {

    @TableId(value = "repairid", type = IdType.AUTO)
    private Integer repairid;

    private Integer id;

    private String breakinfo;

    private Integer state;


}
