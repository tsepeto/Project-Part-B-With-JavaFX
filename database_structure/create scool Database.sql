-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema scool
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `scool` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `scool` ;

-- -----------------------------------------------------
-- Table `scool`.`assignments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scool`.`assignments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `description` TEXT NOT NULL,
  `sub_dateTime` DATE NOT NULL,
  `oral_mark` INT NOT NULL,
  `total_mark` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `scool`.`courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scool`.`courses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `stream` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `scool`.`courses_assignments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scool`.`courses_assignments` (
  `assignment_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  PRIMARY KEY (`assignment_id`, `course_id`),
  INDEX `fk_assignment_has_course_course1_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_assignment_has_course_assignment1_idx` (`assignment_id` ASC) VISIBLE,
  CONSTRAINT `fk_assignment_has_course_assignment1`
    FOREIGN KEY (`assignment_id`)
    REFERENCES `scool`.`assignments` (`id`),
  CONSTRAINT `fk_assignment_has_course_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `scool`.`courses` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `scool`.`students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scool`.`students` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `birthday` DATE NOT NULL,
  `tuition_fees` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `scool`.`courses_students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scool`.`courses_students` (
  `student_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  PRIMARY KEY (`student_id`, `course_id`),
  INDEX `fk_student_has_course_course1_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_student_has_course_student_idx` (`student_id` ASC) VISIBLE,
  CONSTRAINT `fk_student_has_course_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `scool`.`courses` (`id`),
  CONSTRAINT `fk_student_has_course_student`
    FOREIGN KEY (`student_id`)
    REFERENCES `scool`.`students` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `scool`.`trainers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scool`.`trainers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `subject` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `scool`.`courses_trainers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scool`.`courses_trainers` (
  `course_id` INT NOT NULL,
  `trainer_id` INT NOT NULL,
  PRIMARY KEY (`course_id`, `trainer_id`),
  INDEX `fk_course_has_trainer_trainer1_idx` (`trainer_id` ASC) VISIBLE,
  INDEX `fk_course_has_trainer_course1_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `fk_course_has_trainer_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `scool`.`courses` (`id`),
  CONSTRAINT `fk_course_has_trainer_trainer1`
    FOREIGN KEY (`trainer_id`)
    REFERENCES `scool`.`trainers` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `scool`.`studentgrades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scool`.`studentgrades` (
  `oral_mark` INT NOT NULL,
  `total_mark` INT NOT NULL,
  `student_id` INT NOT NULL,
  `assignment_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  PRIMARY KEY (`student_id`, `assignment_id`, `course_id`),
  INDEX `fk_studentgrades_assignment1_idx` (`assignment_id` ASC) VISIBLE,
  INDEX `fk_studentgrades_course1_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `fk_studentgrades_assignment1`
    FOREIGN KEY (`assignment_id`)
    REFERENCES `scool`.`assignments` (`id`),
  CONSTRAINT `fk_studentgrades_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `scool`.`courses` (`id`),
  CONSTRAINT `fk_studentgrades_student1`
    FOREIGN KEY (`student_id`)
    REFERENCES `scool`.`students` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
