package com.time.managment.service;

import com.time.managment.dto.HandlerDto;
import com.time.managment.dto.PresenceDTO;
import com.time.managment.entity.Presence;
import com.time.managment.entity.User;
import com.time.managment.mapper.PresenceMapper;
import com.time.managment.repository.PresenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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
                .sorted(Comparator.comparing(PresenceDTO::getTimeMark).reversed())
                .collect(Collectors.toList());
    }

    public PresenceDTO savePresenceForREST(Presence presence) {
        return mapper.toPresenceDTO(repository.save(presence));
    }

    public HandlerDto savePresenceForView(Presence presence) {
        try {
            final PresenceDTO saved = mapper.toPresenceDTO(repository.save(presence));

            return new HandlerDto()
                    .setSuccess(true)
                    .setMessage("Сохранено: " + saved.getUser().getUsername());

        } catch (Exception ex) {
            return new HandlerDto()
                    .setSuccess(false)
                    .setMessage("Ошибка: " + ex.getMessage());
        }
    }

    public List<Presence> getAllPresence() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Presence::getTimeMark, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }


    // Обработка строки от СКУД и создание записи о присутствии
    public PresenceDTO processPresenceString(String scudData) {
        // Разбираем строку, делим по разделителю ";"
        final String[] data = scudData.split(";");

        if (data.length != 5) {
            throw new IllegalArgumentException("Неверный формат строки от СКУД");
        }

        String timeSheet = data[1];
        String date = data[3];
        String timeMark = data[4].replace("\"", "").trim();

        // Парсим дату в формате "dd.MM.yyyy"
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(date, dateFormatter);

        // Парсим время
        LocalTime localTime = LocalTime.parse(timeMark);

        // Объединяем дату и время
        LocalDateTime time = LocalDateTime.of(localDate, localTime);

        // Ищем пользователя по табельному номеру
        User user = userService.getUser(Integer.valueOf(timeSheet));

        // Создаём новую запись Presence
        Presence presence = new Presence()
                .setUserTimeSheet(user)
                .setTimeMark(time);

        // Сохраняем в базе данных
        Presence savedPresence = repository.save(presence);

        // Возвращаем DTO для ответа
        return mapper.toPresenceDTO(savedPresence);
    }
}
