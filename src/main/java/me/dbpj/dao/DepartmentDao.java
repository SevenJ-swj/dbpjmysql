package me.dbpj.dao;

import me.dbpj.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentDao     extends JpaRepository<Department,Long> {
}
