package com.rs.mb.services;

import com.rs.mb.domain.entities.Canal;
import com.rs.mb.repositories.CanalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CanalService {

    private final CanalRepository canalRepository;

    public List<Canal> findAll() {
        return this.canalRepository.findAll();
    }

    public Canal save(Canal newChannel) {
        return this.canalRepository.save(newChannel);
    }
}
