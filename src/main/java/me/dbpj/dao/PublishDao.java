package me.dbpj.dao;

import me.dbpj.entity.Publish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublishDao extends JpaRepository<Publish,Long> {
    List<Publish> findByAuthor_AuthorName(String authorName);

    Publish findByPublishId(Integer id);
}
