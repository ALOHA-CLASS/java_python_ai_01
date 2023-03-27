-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema human
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema human
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `human` DEFAULT CHARACTER SET utf8mb4;
USE `human` ;

-- -----------------------------------------------------
-- Table `human`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `human`.`users` (
  `user_no` INT NOT NULL AUTO_INCREMENT COMMENT '회원 번호',
  `user_id` VARCHAR(50) NOT NULL COMMENT '회원 ID',
  `user_pw` VARCHAR(100) NOT NULL COMMENT '회원 PW',
  `nickname` VARCHAR(50) NOT NULL COMMENT '회원 닉네임',
  `name` VARCHAR(50) NOT NULL COMMENT '회원 이름',
  `email` VARCHAR(100) NOT NULL COMMENT '회원 E-mail',
  `reg_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '회원 등록 일자',
  `upd_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '회원 수정 일자',
  `enabled` INT NULL DEFAULT '1' COMMENT '회원 활성화 여부',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `unique_name` (`user_nick` ASC, `email` ASC) ,
  UNIQUE INDEX `user_no_UNIQUE` (`user_no` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '회원 테이블';


-- -----------------------------------------------------
-- Table `human`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `human`.`board` (
  `board_no` INT NOT NULL AUTO_INCREMENT COMMENT '게시글 번호',
  `title` VARCHAR(50) NOT NULL COMMENT '게시글 제목',
  `user_id` VARCHAR(50) NOT NULL,
  `content` TEXT NULL DEFAULT NULL COMMENT '게시글 내용',
  `join_cnt` INT NOT NULL DEFAULT '0' COMMENT '게시글 조회수',
  `reg_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '게시글 등록 일자',
  `upd_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '게시글 수정 일자',
  PRIMARY KEY (`board_no`),
  INDEX `fk_board_users1_idx` (`user_id` ASC) ,
  CONSTRAINT `fk_board_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `human`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '게시판 테이블';


-- -----------------------------------------------------
-- Table `human`.`charts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `human`.`charts` (
  `id` INT NOT NULL COMMENT 'charts 식별',
  `name` VARCHAR(400) NOT NULL COMMENT '노래 제목',
  `artist` VARCHAR(50) NOT NULL COMMENT '가수 이름',
  `img` TEXT NOT NULL COMMENT '앨범 이미지',
  `video_url` TEXT NOT NULL COMMENT '영상 링크',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '차트 테이블';


-- -----------------------------------------------------
-- Table `human`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `human`.`comment` (
  `comment_no` INT NOT NULL AUTO_INCREMENT COMMENT '댓글 번호',
  `board_no` INT NOT NULL COMMENT '게시글 번호',
  `user_id` VARCHAR(50) NOT NULL,
  `content` TEXT NULL DEFAULT NULL COMMENT '댓글 내용',
  `group_no` INT NOT NULL DEFAULT '0' COMMENT '댓글 그룹 번호',
  `parent_no` INT NOT NULL DEFAULT '0' COMMENT '댓글 부모 번호',
  `depth_no` INT NOT NULL DEFAULT '0' COMMENT '댓글 계층 번호',
  `seq_no` INT NOT NULL DEFAULT '0' COMMENT '댓글 순서 번호',
  `sub_cnt` INT NOT NULL DEFAULT '0' COMMENT '댓글 자식 갯수',
  `reg_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '댓글 등록 일자',
  `upd_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '댓글 수정 일자',
  PRIMARY KEY (`comment_no`),
  INDEX `FK_board_TO_comment` (`board_no` ASC) ,
  INDEX `fk_comment_users1_idx` (`user_id` ASC) ,
  CONSTRAINT `FK_board_TO_comment`
    FOREIGN KEY (`board_no`)
    REFERENCES `human`.`board` (`board_no`),
  CONSTRAINT `fk_comment_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `human`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '댓글 테이블';


-- -----------------------------------------------------
-- Table `human`.`persistent_logins`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `human`.`persistent_logins` (
  `username` VARCHAR(50) NOT NULL COMMENT '회원 ID',
  `series` VARCHAR(64) NOT NULL COMMENT '토큰 인증 키',
  `token` VARCHAR(64) NOT NULL COMMENT '회원 인증 토큰',
  `last_used` TIMESTAMP NOT NULL COMMENT '마지막 사용 시간',
  PRIMARY KEY (`series`),
  UNIQUE INDEX `unique_name` (`username` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '로그인 유지 테이블';


-- -----------------------------------------------------
-- Table `human`.`user_auth`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `human`.`user_auth` (
  `auth_no` INT NOT NULL AUTO_INCREMENT COMMENT '회원 번호',
  `auth` VARCHAR(20) NOT NULL COMMENT '회원 권한 등급',
  `user_id` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`auth_no`),
  INDEX `fk_user_auth_users1_idx` (`user_id` ASC) ,
  CONSTRAINT `fk_user_auth_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `human`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '회원 권한 테이블';


-- -----------------------------------------------------
-- Table `human`.`user_social`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `human`.`user_social` (
  `social_no` INT NOT NULL AUTO_INCREMENT COMMENT '소셜 번호',
  `user_id` VARCHAR(50) NOT NULL,
  `social_type` VARCHAR(20) NOT NULL COMMENT '소셜 가입 구분',
  `access_token` VARCHAR(200) NULL COMMENT '소셜 인증 토큰',
  PRIMARY KEY (`social_no`),
  INDEX `fk_user_social_users1_idx` (`user_id` ASC) ,
  CONSTRAINT `fk_user_social_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `human`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '회원 소셜 테이블';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
