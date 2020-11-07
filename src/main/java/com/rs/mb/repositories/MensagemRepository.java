package com.rs.mb.repositories;

import com.rs.mb.domain.entities.Mensagem;
import com.rs.mb.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    @Query("SELECT MAX(m) FROM Mensagem m LEFT JOIN m.usuario u WHERE u.nome = :target")
    Optional<Mensagem> fetchLastSeen(String target);

    List<Mensagem> findAllByUsuario(Usuario user);

    List<Mensagem> findAllByCanalNome(String sourceId);

    List<Mensagem> findAllByUsuarioAndCanalNome(Usuario user, String sourceId);
}
