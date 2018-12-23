package me.dbpj.dao;

import javafx.application.Application;
import me.dbpj.entity.Paper;
import org.apache.catalina.core.ApplicationContext;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.Configuration;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Repository
public interface PaperDao extends JpaRepository<Paper,Long>{
    //List<Paper> findBy
    Paper findPaperById(Integer id);
}
