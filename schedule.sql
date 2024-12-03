CREATE TABLE `Schedule` (
	`schedule_id`	BIGINT	NOT NULL	DEFAULT AUTO_INCREMENT,
	`title`	VARCHER(100)	NOT NULL,
	`password`	VARCHER(100)	NOT NULL,
	`contents`	VARCHAR(200)	NOT NULL,
	`create_date`	DATETIME	NOT NULL,
	`edit_date`	DATETIME	NOT NULL,
	`user_id` BIGINT,
	`user_name`	VARCHER(50)	NOT NULL,
	FOREIGN KEY (user_id)
	REFERENCES User(user_id)
);

CREATE TABLE `User` (
	`user_id`	BIGINT	NOT NULL	DEFAULT AUTO_INCREMENT,
	`user_name`	VARCHER(50)	NOT NULL,
	`email`	VARCHAR(100)	NOT NULL,
	`create_date`	DATETIME	NOT NULL
);

