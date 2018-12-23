package me.dbpj.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Jeremy
 * @Date: 2018/11/3 19:33
 */
@Entity
@Table(name="field")
@Data
public class Field {

    @Id
    @Column(name="fieldId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 研究领域
     */
    @Column(name="fieldName")
    private String fieldName;
}
