INSERT INTO users(id, initials, timesheet) VALUES
(10, 'Test1', 123456),
(11, 'Test2', 123457),
(12, 'Test3', 123458);

INSERT INTO presences(id, user_timesheet, time_mark) VALUES
(10, 123456, '2024-03-15 14:30:45'),
(11, 123456, '2024-03-15 10:01:00'),
(12, 123457, '2024-03-15 11:30:05');

INSERT INTO departments(id, user_timesheet, department) VALUES
(10, 123456, 123),
(11, 123457, 12),
(12, 123456, 1);

INSERT INTO weekends(id, user_timesheet, weekend_date, reason, start_time, end_time) VALUES
(10, 123456, '2024-03-16', 'Проспал', '08:00:00', '08:15:00'),
(11, 123457, '2024-03-17', 'Пробки', '08:00:00', '08:45:17'),
(12, 123456, '2024-03-18', 'Просто не хотел идти', '08:00:00', '23:59:00');

