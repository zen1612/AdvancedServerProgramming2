
CREATE TABLE room(
                     id INT PRIMARY KEY AUTO_INCREMENT,
                     name VARCHAR(100) NOT NULL,
                     capacity INT NOT NULL CHECK (capacity BETWEEN 1 AND 20)
);

CREATE TABLE reservations(
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             room_id BIGINT NOT NULL,
                             reserved_by VARCHAR(50) NOT NULL,
                             start_time DATETIME NOT NULL,
                             end_time DATETIME NOT NULL,
                             FOREIGN KEY (room_id) REFERENCES room(id)
);

INSERT into room (name, capacity)
VALUES
    ('Seminar A', 8),
    ('Study Pod', 4),
    ('Rooftop Room', 12);

INSERT into reservations (room_id, reserved_by, start_time, end_time)
VALUES
    (1, 'Mina', '2026-10-06 10:00:00', '2026-10-06 11:00:00'),
    (1, 'Omar', '2026-10-06 13:00:00', '2026-10-06 15:00:00'),
    (2, 'Mina', '2026-10-06 09:00:00', '2026-10-06 10:00:00'),
    (1, 'Lucas', '2026-10-07 10:00:00', '2026-10-06 12:00:00'),
    (2, 'Aiko', '2026-10-07 14:00:00', '2026-10-06 15:00:00');

--Q1
SELECT * FROM room
WHERE capacity > 6
ORDER BY capacity DESC;
--Q2
SELECT * FROM reservations
WHERE reserved_by = 'Mina';
--Q3
SELECT
    res.id,
    r.name,
    reserved_by,
    start_time,
    end_time
FROM reservations res
         INNER JOIN room r ON r.id = res.room_id;
--Q4
SELECT * FROM(
                 SELECT
                     res.id,
                     r.name,
                     reserved_by,
                     start_time,
                     end_time
                 FROM reservations res
                          INNER JOIN room r ON r.id = res.room_id)
                 AS joined
WHERE name = 'Seminar A' AND start_time < '2026-10-07'
--Q5
SELECT
    r.id,
    r.name,
    COUNT(res.id) AS reservation_count
FROM room r
         INNER JOIN reservations res ON r.id = res.room_id
GROUP BY r.id, r.name;
--Q6
SELECT
    r.id,
    r.name,
    COUNT(res.id) AS reservation_count
FROM room r
         LEFT JOIN reservations res ON r.id = res.room_id
GROUP BY r.id, r.name;
--Q7
SELECT
    r.id,
    r.name
FROM room r
         LEFT JOIN reservations res ON r.id = res.room_id
WHERE res.id IS NULL;
--Q8
SELECT
    r.id,
    r.name
FROM room r
         LEFT JOIN reservations res ON r.id = res.room_id
GROUP BY r.id, r.name
HAVING COUNT(res.id) > 2;
