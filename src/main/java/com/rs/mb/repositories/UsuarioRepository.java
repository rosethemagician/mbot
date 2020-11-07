package com.rs.mb.repositories;

import com.rs.mb.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByNome(String nome);
}
