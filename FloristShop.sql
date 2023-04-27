-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema florist_catalog
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema florist_catalog
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `florist_catalog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `florist_catalog` ;

-- -----------------------------------------------------
-- Table `florist_catalog`.`store`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist_catalog`.`store` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `total_value` DECIMAL(10,2) NULL DEFAULT '0.00',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `florist_catalog`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist_catalog`.`product` (
  `idproduct` INT NOT NULL AUTO_INCREMENT,
  `store_id` INT NULL DEFAULT '1',
  PRIMARY KEY (`idproduct`),
  INDEX `fk_product_store1_idx` (`store_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_store1`
    FOREIGN KEY (`store_id`)
    REFERENCES `florist_catalog`.`store` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `florist_catalog`.`Ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist_catalog`.`Ticket` (
  `idTicket` INT NOT NULL AUTO_INCREMENT,
  `productQuantity` INT NOT NULL,
  `store_id` INT NOT NULL,
  `product_idproduct` INT NOT NULL,
  `SalesTickets` DOUBLE NULL DEFAULT '0',
  PRIMARY KEY (`idTicket`),
  INDEX `fk_Ticket_store1_idx` (`store_id` ASC) VISIBLE,
  INDEX `fk_Ticket_product1_idx` (`product_idproduct` ASC) VISIBLE,
  CONSTRAINT `fk_Ticket_product1`
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `florist_catalog`.`product` (`idproduct`),
  CONSTRAINT `fk_Ticket_store1`
    FOREIGN KEY (`store_id`)
    REFERENCES `florist_catalog`.`store` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `florist_catalog`.`decorations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist_catalog`.`decorations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `material` ENUM('wood', 'plastic') NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `price` DOUBLE NOT NULL,
  `quantity` INT NOT NULL,
  `product_idproduct` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_decorations_product1_idx` (`product_idproduct` ASC) VISIBLE,
  CONSTRAINT `fk_decorations_product1`
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `florist_catalog`.`product` (`idproduct`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `florist_catalog`.`flowers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist_catalog`.`flowers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `color` VARCHAR(255) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `price` DOUBLE NOT NULL,
  `quantity` INT NOT NULL,
  `product_idproduct` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_flowers_product1_idx` (`product_idproduct` ASC) VISIBLE,
  CONSTRAINT `fk_flowers_product1`
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `florist_catalog`.`product` (`idproduct`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `florist_catalog`.`trees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist_catalog`.`trees` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `height` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `price` DOUBLE NOT NULL,
  `quantity` INT NOT NULL,
  `product_idproduct` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_trees_product1_idx` (`product_idproduct` ASC) VISIBLE,
  CONSTRAINT `fk_trees_product1`
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `florist_catalog`.`product` (`idproduct`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
