package me.dbpj.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="author")
@Data
public class Author {
    @Id
    @Column(name="authorId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorId;

    @Column(name="authorName")
    private String authorName;

    @Column(name="authorUrl")
    private String authorUrl;

}
