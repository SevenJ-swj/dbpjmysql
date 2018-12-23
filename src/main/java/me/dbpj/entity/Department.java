package me.dbpj.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Jeremy
 * @Date: 2018/11/3 18:37
 */
@Entity
@Table(name="department")
@Data
public class Department {

    @Id
    @Column(name="departmentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 单位名称
     */
    @Column(name="departmentName")
    private String dName;

    /**
     * 单位地址
     */
    //@Column(name="departmentAddress")
    //private String dAddress;
}
