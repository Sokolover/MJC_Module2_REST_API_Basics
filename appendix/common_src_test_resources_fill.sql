INSERT INTO country (name, version)
VALUES ('Poland', 0),
       ('Italy', 0),
       ('Spain', 0);

INSERT INTO hotel (name, phone, stars, country_id, version)
VALUES ('Hilton', '125698745', 5, 1, 0),
       ('Europe', '125698745', 4, 2, 0);

INSERT INTO tour (photo, date, duration, description, cost, tour_type, country_id, hotel_id, version)
VALUES ('d:\photo\1.jpg', '1989-05-11', 15, 'goodbye', 1200.00, 'FAMILY_TOUR', 1, 1, 0),
       ('d:\photo\2.jpg', '1989-05-11', 15, 'good', 1000.00, 'BUS_TOUR', 2, 2, 0);

INSERT INTO users (password, login, version)
VALUES ('0123', 'galina123', 0),
       ('789546', 'sergey123', 0),
       ('4fdgkfds', 'sasha123', 0);

INSERT INTO tour_user (tour_id, user_id)
VALUES (2, 2),
       (2, 1),
       (1, 2);

INSERT INTO review (content, tour_id, user_id, version)
VALUES ('bla bla bla', 1, 1, 0),
       ('bla bla bla', 1, 2, 0),
       ('bla bla bla', 1, 3, 0);