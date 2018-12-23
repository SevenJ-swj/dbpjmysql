package me.dbpj.dao;

import me.dbpj.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDao     extends JpaRepository<Author,Long> {
    Author findByAuthorId(Integer id);

    Author findByAuthorName(String name);
}
