package com.example.demo1.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2020-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("FOO")
public class Foo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private Integer id;

    @TableField("BAR")
    private String bar;



}
