package com.time.managment.service;

import com.time.managment.dto.PresenceDTO;
import com.time.managment.entity.Presence;
import com.time.managment.mapper.PresenceMapper;
import com.time.managment.repository.PresenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PresenceService {
    private final PresenceRepository repository;
    private final UserService userService;
    private final PresenceMapper mapper;

    public List<PresenceDTO> getPresences(Integer timeSheet) {
        return repository.getPresencesByUserTimeSheet(userService.getUser(timeSheet)).stream()
                .map(mapper::toPresenceDTO)
                .collect(Collectors.toList());
    }

    public PresenceDTO savePresence(Presence presence) {
        return mapper.toPresenceDTO(repository.save(presence));
    }
}
