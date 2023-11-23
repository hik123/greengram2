DROP TABLE t_user;
CREATE TABLE `t_user` (
                          `iuser` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                          `uid` VARCHAR(25) unique NOT NULL ,
                          `upw` VARCHAR(25) NOT NULL ,
                          `nm` VARCHAR(25) NOT NULL ,
                          `pic` VARCHAR(2100),
                          `created_at` DATETIME NOT NULL DEFAULT current_timestamp,
                          `updated_at` DATETIME ON UPDATE current_timestamp,
                          PRIMARY KEY (`iuser`)
);

CREATE TABLE `t_feed` (
                          `ifeed` INT UNSIGNED AUTO_INCREMENT,
                          `iuser` INT UNSIGNED NOT NULL,
                          `contents` VARCHAR(1000),
                          `location` VARCHAR(30),
                          `created_at` DATETIME NOT NULL DEFAULT current_timestamp(),
                          `updated_at` DATETIME ON UPDATE current_timestamp(),
                          PRIMARY KEY (`ifeed`),
                          FOREIGN KEY (`iuser`) REFERENCES `t_user` (`iuser`)
);


CREATE TABLE `t_feed_pics` (
                               `ifeed_pics` INT UNSIGNED AUTO_INCREMENT,
                               `ifeed` INT UNSIGNED NOT NULL,
                               `pic` VARCHAR(2100) NOT NULL,
                               `created_at` DATETIME NOT NULL DEFAULT current_timestamp(),
                               PRIMARY KEY (`ifeed_pics`),
                               FOREIGN KEY (`ifeed`) REFERENCES `t_feed` (`ifeed`)
);

CREATE TABLE `t_feed_fav` (
                              `ifeed` INT UNSIGNED NOT NULL,
                              `iuser` INT UNSIGNED NOT NULL,
                              `created_at` DATETIME NOT NULL DEFAULT current_timestamp(),
                              PRIMARY KEY (`ifeed`, `iuser`),
                              FOREIGN KEY (`ifeed`) REFERENCES `t_feed` (`ifeed`),
                              FOREIGN KEY (`iuser`) REFERENCES `t_user` (`iuser`)
);

DROP TABLE t_feed_comment;

-- 댓글
CREATE TABLE t_feed_comment (
                                ifeed_comment INT UNSIGNED AUTO_INCREMENT PRIMARY KEY
    , iuser INT UNSIGNED NOT NULL
    , ifeed INT UNSIGNED NOT NULL
    , COMMENT VARCHAR(500) NOT NULL
    , created_at DATETIME NOT NULL DEFAULT current_timestamp
    , updated_at DATETIME NOT NULL DEFAULT current_timestamp
                                    ON UPDATE CURRENT_TIMESTAMP
    , FOREIGN KEY (iuser) REFERENCES t_user(iuser)
    , FOREIGN KEY (ifeed) REFERENCES t_feed(ifeed)
);













