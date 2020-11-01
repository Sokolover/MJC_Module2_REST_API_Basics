INSERT INTO tag (name)
VALUES ('first'),
       ('second'),
       ('third');

INSERT INTO tag (id, name)
VALUES (13, 'films'),
       (14, 'fun');

INSERT INTO gift_certificate (name, description, price, createDate, lastUpdateDate, duration)
VALUES ('netflix', '5 any films', 6.7, '2020-12-23T09:37:39', '2020-12-23T09:37:39', 5),
       ('ivi', '2 any films', 3.5, '2019-12-23T09:37:39', '2019-12-23T09:37:39', 2),
       ('megogo', '3 any films', 7.4, '2018-12-23T09:37:39', '2018-12-23T09:37:39', 13);

INSERT INTO gift_certificate(id, name, description, price, createDate, lastUpdateDate, duration)
VALUES (15, 'netflix', '5 any films', '5.55', '2020-10-23 09:37:39', '2020-10-23 14:37:39', '10'),
       (16, 'netflix', '5 any films', '5.55', '2020-10-23 09:37:39', '2020-10-23 15:37:39', '10'),
       (17, 'ivi', '7 any films', '5.55', '2020-10-23 09:37:39', '2020-10-23 16:37:39', '10'),
       (18, 'ivi', '7 any films', '5.55', '2020-10-23 09:37:39', '2020-10-23 17:37:39', '10');

INSERT INTO tag_has_gift_certificate (tag_id, gift_certificate_id)
VALUES (13, 16),
       (13, 17),
       (13, 18),
       (13, 19),
       (14, 16),
       (14, 17),
       (14, 18),
       (14, 19)
