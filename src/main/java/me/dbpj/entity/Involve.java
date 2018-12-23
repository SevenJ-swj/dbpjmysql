package me.dbpj.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="involve")
@Data
public class Involve {
    @Id
    @Column(name="involveId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer involveId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="paperId")
    private Paper paper;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fieldId")
    private Field field;
}
