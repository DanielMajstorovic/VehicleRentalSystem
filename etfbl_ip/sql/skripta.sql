-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema etfbl_ip
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `etfbl_ip` ;

-- -----------------------------------------------------
-- Schema etfbl_ip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `etfbl_ip` DEFAULT CHARACTER SET utf8 ;
USE `etfbl_ip` ;

-- -----------------------------------------------------
-- Table `etfbl_ip`.`MANUFACTURER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`MANUFACTURER` (
  `ManufacturerID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(75) NOT NULL,
  `Country` VARCHAR(75) NOT NULL,
  `Address` VARCHAR(75) NOT NULL,
  `Phone` VARCHAR(25) NULL,
  `Fax` VARCHAR(25) NULL,
  `Email` VARCHAR(128) NOT NULL,
  `Deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`ManufacturerID`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`VEHICLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`VEHICLE` (
  `VehicleID` INT NOT NULL AUTO_INCREMENT,
  `UID` VARCHAR(75) NOT NULL,
  `PurchasePrice` DECIMAL(10,2) NOT NULL,
  `Model` VARCHAR(75) NOT NULL,
  `MANUFACTURER_ManufacturerID` INT NOT NULL,
  `Status` ENUM('AVAILABLE', 'RENTED', 'BROKEN') NOT NULL,
  `Deleted` TINYINT NOT NULL DEFAULT 0,
  `PricePerSecond` DECIMAL(12,8) NOT NULL,
  `X` DECIMAL(9,6) NOT NULL,
  `Y` DECIMAL(9,6) NULL,
  PRIMARY KEY (`VehicleID`),
  UNIQUE INDEX `UID_UNIQUE` (`UID` ASC) VISIBLE,
  INDEX `fk_VEHICLE_MANUFACTURER_idx` (`MANUFACTURER_ManufacturerID` ASC) VISIBLE,
  CONSTRAINT `fk_VEHICLE_MANUFACTURER`
    FOREIGN KEY (`MANUFACTURER_ManufacturerID`)
    REFERENCES `etfbl_ip`.`MANUFACTURER` (`ManufacturerID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`CAR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`CAR` (
  `VEHICLE_VehicleID` INT NOT NULL,
  `PurchaseDate` DATE NOT NULL,
  `Description` VARCHAR(512) NULL,
  PRIMARY KEY (`VEHICLE_VehicleID`),
  CONSTRAINT `fk_CAR_VEHICLE1`
    FOREIGN KEY (`VEHICLE_VehicleID`)
    REFERENCES `etfbl_ip`.`VEHICLE` (`VehicleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`E_BIKE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`E_BIKE` (
  `VEHICLE_VehicleID` INT NOT NULL,
  `Autonomy` INT NOT NULL,
  PRIMARY KEY (`VEHICLE_VehicleID`),
  CONSTRAINT `fk_E_BIKE_VEHICLE1`
    FOREIGN KEY (`VEHICLE_VehicleID`)
    REFERENCES `etfbl_ip`.`VEHICLE` (`VehicleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`E_SCOOTER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`E_SCOOTER` (
  `VEHICLE_VehicleID` INT NOT NULL,
  `MaxSpeed` INT NOT NULL,
  PRIMARY KEY (`VEHICLE_VehicleID`),
  CONSTRAINT `fk_E_SCOOTER_VEHICLE1`
    FOREIGN KEY (`VEHICLE_VehicleID`)
    REFERENCES `etfbl_ip`.`VEHICLE` (`VehicleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`BREAKDOWN`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`BREAKDOWN` (
  `BreakdownID` INT NOT NULL AUTO_INCREMENT,
  `Description` VARCHAR(512) NOT NULL,
  `BreakdownTime` DATETIME NOT NULL,
  `RepairTime` DATETIME NULL,
  `VEHICLE_VehicleID` INT NOT NULL,
  `Deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`BreakdownID`),
  INDEX `fk_BREAKDOWN_VEHICLE1_idx` (`VEHICLE_VehicleID` ASC) VISIBLE,
  CONSTRAINT `fk_BREAKDOWN_VEHICLE1`
    FOREIGN KEY (`VEHICLE_VehicleID`)
    REFERENCES `etfbl_ip`.`VEHICLE` (`VehicleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`USER` (
  `UserID` INT NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(50) NOT NULL,
  `Password` VARCHAR(50) NOT NULL,
  `FirstName` VARCHAR(50) NOT NULL,
  `LastName` VARCHAR(50) NOT NULL,
  `Deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`UserID`),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`EMPLOYEE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`EMPLOYEE` (
  `USER_UserID` INT NOT NULL,
  `Role` ENUM('ADMINISTRATOR', 'OPERATOR', 'MANAGER') NOT NULL,
  PRIMARY KEY (`USER_UserID`),
  CONSTRAINT `fk_EMPLOYEE_USER1`
    FOREIGN KEY (`USER_UserID`)
    REFERENCES `etfbl_ip`.`USER` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`CLIENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`CLIENT` (
  `USER_UserID` INT NOT NULL,
  `IDNumber` VARCHAR(25) NULL,
  `PassportNumber` VARCHAR(25) NULL,
  `Email` VARCHAR(128) NOT NULL,
  `Phone` VARCHAR(25) NOT NULL,
  `HasAvatarImage` TINYINT NOT NULL DEFAULT 0,
  `Balance` DECIMAL(10,2) NOT NULL DEFAULT 100000.00,
  PRIMARY KEY (`USER_UserID`),
  CONSTRAINT `fk_CLIENT_USER1`
    FOREIGN KEY (`USER_UserID`)
    REFERENCES `etfbl_ip`.`USER` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`POST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`POST` (
  `PostID` INT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(128) NOT NULL,
  `Content` VARCHAR(512) NOT NULL,
  PRIMARY KEY (`PostID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`PROMOTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`PROMOTION` (
  `PromotionID` INT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(128) NOT NULL,
  `Description` VARCHAR(512) NOT NULL,
  `StartsAt` DATETIME NOT NULL,
  `EndsAt` DATETIME NOT NULL,
  PRIMARY KEY (`PromotionID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etfbl_ip`.`RENTAL`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etfbl_ip`.`RENTAL` (
  `RentalID` INT NOT NULL AUTO_INCREMENT,
  `StartTime` DATETIME NOT NULL,
  `StartX` DECIMAL(9,6) NOT NULL,
  `StartY` DECIMAL(9,6) NOT NULL,
  `EndTime` DATETIME NULL,
  `EndX` DECIMAL(9,6) NULL,
  `EndY` DECIMAL(9,6) NULL,
  `Duration` INT NULL,
  `DriversLicense` VARCHAR(25) NOT NULL,
  `PaymentCard` VARCHAR(25) NULL,
  `TotalAmount` DECIMAL(10,2) NULL,
  `VEHICLE_VehicleID` INT NOT NULL,
  `CLIENT_USER_UserID` INT NOT NULL,
  PRIMARY KEY (`RentalID`),
  INDEX `fk_RENTAL_VEHICLE1_idx` (`VEHICLE_VehicleID` ASC) VISIBLE,
  INDEX `fk_RENTAL_CLIENT1_idx` (`CLIENT_USER_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_RENTAL_VEHICLE1`
    FOREIGN KEY (`VEHICLE_VehicleID`)
    REFERENCES `etfbl_ip`.`VEHICLE` (`VehicleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RENTAL_CLIENT1`
    FOREIGN KEY (`CLIENT_USER_UserID`)
    REFERENCES `etfbl_ip`.`CLIENT` (`USER_UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
