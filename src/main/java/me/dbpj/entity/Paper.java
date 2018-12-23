package me.dbpj.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Jeremy
 * @Date: 2018/11/3 14:45
 */
@Entity
@Table(name="paper")
@Data
public class Paper {
    /**
     * 主键
     */
    @Id
    @Column(name="paperId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    /**
     * 论文题目
     */
    @Column(name="paperTitle")
    private String pTitle;

    /**
     * 论文摘要
     */
    @Column(name="paperAbstract")
    private String pAbstract;
    /**
     * 论文页码
     */
    @Column(name="paperPage")
    private Integer pPage;

    /**
     * 引用次数
     */
    @Column(name="paperCitation")
    private Integer pCitation;

    /**
     * 发表年份
     */
    @Column(name="publishYear")
    private Integer pYear;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="conferenceId")
    private Conference conference;
}
