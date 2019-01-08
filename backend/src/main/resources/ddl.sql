CREATE TABLE `ALIMY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `STATUS` char(1) NOT NULL DEFAULT '1',
  `SUBJECT` varchar(45) NOT NULL,
  `MESSAGE` varchar(200) NOT NULL,
  `REG_DATETIME` datetime DEFAULT NULL,
  `MOD_DATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ALIMY_UNITS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ALIMY_ID` int(11) NOT NULL,
  `UNIT_TYPE` varchar(3) NOT NULL,
  `UNIT_VALUE` varchar(40) NOT NULL,
  `REG_DATETIME` datetime DEFAULT NULL,
  `MOD_DATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ALIMY_UNIT_UNIQUE` (`ALIMY_ID`,`UNIT_TYPE`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `PRIVILEGE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ROLES` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ROLES_PRIVILEGES` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `PRIVILEGE_ID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `USERS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROVIDER_USER_ID` int(11) NOT NULL,
  `PASSWORD` varchar(45) DEFAULT NULL,
  `USER_NAME` varchar(45) NOT NULL,
  `EMAIL` varchar(45) DEFAULT NULL,
  `IMAGE_URL` varchar(100) DEFAULT NULL,
  `ACCESS_TOKEN` varchar(100) NOT NULL,
  `REFRESH_TOKEN` varchar(100) NOT NULL,
  `EXPIRED_TIME` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PROVIDER_USER_ID_UNIQUE` (`PROVIDER_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `USERS_ROLES` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `USER_ID_USER_ID_UNIQUE` (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

CREATE TABLE LOGS
(
  ID int PRIMARY KEY AUTO_INCREMENT,
  TYPE int NOT NULL,
  VALUE JSON
  CHECK (JSON_VALID(VALUE))
);
