-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gift_certificate
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gift_certificate
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gift_certificate` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `gift_certificate`;

-- -----------------------------------------------------
-- Table `gift_certificate`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificate`.`tag`
(
    `id`   BIGINT(100)  NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(200) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificate`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificate`.`gift_certificate`
(
    `id`             BIGINT(100)   NOT NULL AUTO_INCREMENT,
    `name`           VARCHAR(200)  NOT NULL,
    `description`    VARCHAR(1000) NOT NULL,
    `price`          DECIMAL(7, 2) NOT NULL,
    `createDate`     DATETIME      NOT NULL,
    `lastUpdateDate` DATETIME      NOT NULL,
    `duration`       INT           NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificate`.`tag_has_gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificate`.`tag_has_gift_certificate`
(
    `tag_id`              BIGINT(100) NOT NULL,
    `gift_certificate_id` BIGINT(100) NOT NULL,
    PRIMARY KEY (`tag_id`, `gift_certificate_id`),
    INDEX `fk_tag_has_gift_certificate_gift_certificate1_idx` (`gift_certificate_id` ASC) VISIBLE,
    INDEX `fk_tag_has_gift_certificate_tag_idx` (`tag_id` ASC) VISIBLE,
    CONSTRAINT `fk_tag_has_gift_certificate_tag`
        FOREIGN KEY (`tag_id`)
            REFERENCES `gift_certificate`.`tag` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_tag_has_gift_certificate_gift_certificate1`
        FOREIGN KEY (`gift_certificate_id`)
            REFERENCES `gift_certificate`.`gift_certificate` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;