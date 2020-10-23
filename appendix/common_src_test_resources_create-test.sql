CREATE TABLE COUNTRY
(
    ID      bigint AUTO_INCREMENT PRIMARY KEY NOT NULL,
    NAME    varchar(55)                       NOT NULL,
    VERSION integer
);

CREATE TABLE HOTEL
(
    ID         bigint AUTO_INCREMENT PRIMARY KEY NOT NULL,
    NAME       varchar(55)                       NOT NULL,
    PHONE      varchar(55),
    STARS      TINYINT                           NOT NULL,
    COUNTRY_ID bigint                            NOT NULL,
    VERSION    integer,
    CONSTRAINT HOTEL_COUNTRY_ID_FK FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (ID)
);

CREATE TABLE TOUR
(
    ID          bigint AUTO_INCREMENT PRIMARY KEY NOT NULL,
    PHOTO       varchar(55),
    DATE        date                              NOT NULL,
    DURATION    INTEGER                           NOT NULL,
    DESCRIPTION varchar(255),
    COST        decimal(65535, 32767)             NOT NULL,
    TOUR_TYPE   VARCHAR(255)                      NOT NULL,
    COUNTRY_ID  bigint                            NOT NULL,
    HOTEL_ID    bigint                            NOT NULL,
    VERSION     integer,
    CONSTRAINT TOUR_COUNTRY_ID_FK FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (ID),
    CONSTRAINT TOUR_HOTEL_ID_FK FOREIGN KEY (HOTEL_ID) REFERENCES HOTEL (ID)
);

CREATE TABLE USERS
(
    ID       bigint AUTO_INCREMENT PRIMARY KEY NOT NULL,
    PASSWORD VARCHAR(255)                      NOT NULL,
    LOGIN    varchar(55)                       NOT NULL,
    VERSION  integer
);

CREATE TABLE TOUR_USER
(
    TOUR_ID bigint NOT NULL,
    USER_ID bigint NOT NULL,
    CONSTRAINT CONSTRAINT_D6 PRIMARY KEY (USER_ID, TOUR_ID),
    CONSTRAINT TOUR_USER_TOUR_ID_FK FOREIGN KEY (TOUR_ID) REFERENCES TOUR (ID),
    CONSTRAINT TOUR_USER_USERS_ID_FK FOREIGN KEY (USER_ID) REFERENCES USERS (ID)
);

CREATE TABLE REVIEW
(
    ID      bigint AUTO_INCREMENT PRIMARY KEY NOT NULL,
    CONTENT varchar(255)                      NOT NULL,
    TOUR_ID bigint                            NOT NULL,
    USER_ID bigint                            NOT NULL,
    VERSION integer,
    CONSTRAINT REVIEW_TOUR_ID_FK FOREIGN KEY (TOUR_ID) REFERENCES TOUR (ID),
    CONSTRAINT REVIEW_USERS_ID_FK FOREIGN KEY (USER_ID) REFERENCES USERS (ID)
);