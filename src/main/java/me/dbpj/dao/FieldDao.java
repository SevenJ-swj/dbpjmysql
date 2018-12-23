package me.dbpj.dao;

import me.dbpj.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldDao     extends JpaRepository<Field,Long> {
}
