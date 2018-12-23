package me.dbpj.dao;

import me.dbpj.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceDao extends JpaRepository<Conference,Long> {
    Conference findById(Integer id);

    Conference findByCName(String name);

    void deleteByCName(String name);
}
