package com.rs.mb.repositories;

import com.rs.mb.domain.entities.Canal;
import com.rs.mb.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CanalRepository extends JpaRepository<Canal, String> {

    Optional<Canal> findByNome(String nome);

}
