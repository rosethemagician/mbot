package com.rs.mb.services;

import com.rs.mb.domain.entities.Usuario;
import com.rs.mb.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Async
    public void saveNewUser(Usuario usuario) {
        Optional<Usuario> usuarioOptional = findByNome(usuario.getNome());

        if (!usuarioOptional.isPresent()) {
            usuario.setDataCadastro(LocalDateTime.now());
            this.usuarioRepository.save(usuario);
        }
    }

    public Optional<Usuario> findByNome(String nome) {
        return this.usuarioRepository.findByNome(nome);
    }

}
