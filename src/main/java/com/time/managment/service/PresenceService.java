package com.time.managment.service;

import com.time.managment.dto.PresenceDTO;
import com.time.managment.entity.Presence;
import com.time.managment.entity.User;
import com.time.managment.mapper.PresenceMapper;
import com.time.managment.repository.DepartmentRepository;
import com.time.managment.repository.PresenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public PresenceDTO savePresence(Presence presence) {
        return mapper.toPresenceDTO(repository.save(presence));
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

        // Извлекаем данные
        //String fullName = data[0];        // ФамилияИмяОтчество
        String timeSheet = data[1];       // Табельный номер
        //String department = data[2];      // Отдел (не используется для поиска, но может быть полезно)
        String date = data[3];            // Дата (например, "2025-04-11")
        String timeMark = data[4];        // Временная метка (например, "08:00:00")

        // Убираем лишние символы (например, кавычки)
        timeMark = timeMark.replace("\"", "").trim();

        // Преобразуем строку даты и времени
        String dateTimeString = date + "T" + timeMark;
        LocalDateTime time = LocalDateTime.parse(dateTimeString);

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
