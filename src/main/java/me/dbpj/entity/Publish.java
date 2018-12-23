package me.dbpj.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="publish")
@Data
public class Publish {
    @Id
    @Column(name="publishId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer publishId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="authorId")
    private Author author
            ;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="paperId")
    private Paper paper;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="departmentId")
    private Department department;

    @Column(name="authorPos")
    private Long authorPos;
}
