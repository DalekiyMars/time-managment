package com.time.managment.service;

import com.time.managment.dto.WeekendDTO;
import com.time.managment.entity.Weekend;
import com.time.managment.mapper.WeekendMapper;
import com.time.managment.repository.WeekendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeekendService {
    private final WeekendMapper mapper;
    private final WeekendRepository repository;
    private final UserService userService;

    public List<WeekendDTO> getWeekends(Integer timeSheet){
        return repository.getWeekendsByUserTimeSheet(userService.getUser(timeSheet))
                .stream()
                .map(mapper::toWeekendDTO)
                .collect(Collectors.toList());
    }

    public WeekendDTO saveWeekend(Weekend weekend){
        return mapper.toWeekendDTO(repository.save(weekend));
    }

    public void deleteWeekend(Weekend weekend){
        log.info("Deleting weekend " + weekend.toString());
        repository.delete(weekend);
    }
}
