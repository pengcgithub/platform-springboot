package com.yingfeng.cms.commons.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 基础对象
 *
 * @author pengc
 * @since 2017/09/05
 */
@Data
public class BaseBean {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

}
