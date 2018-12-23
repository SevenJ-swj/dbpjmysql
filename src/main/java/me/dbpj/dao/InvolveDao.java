package me.dbpj.dao;

import me.dbpj.entity.Involve;
import me.dbpj.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvolveDao     extends JpaRepository<Involve,Long> {
    
}
