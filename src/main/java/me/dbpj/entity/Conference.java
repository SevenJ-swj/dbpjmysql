package me.dbpj.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Jeremy
 * @Date: 2018/11/3 20:12
 */
@Entity
@Table(name="conference")
@Data
public class Conference {

    @Id
    @Column(name="conferenceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会议名称
     */
    @Column(name="conferenceName")
    private String cName;
}
