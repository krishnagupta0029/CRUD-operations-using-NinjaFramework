-- the first script for migration
CREATE TABLE Project (
 id bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 project_title varchar(20),
 project_description varchar(20)
);
CREATE TABLE Article (
 id bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 content varchar(5000),
 postedAt timestamp,
 title varchar(255)
);
 
CREATE TABLE Article_authorIds (
 Article_id bigint UNSIGNED not null,
 authorIds bigint UNSIGNED
);
CREATE TABLE Project_authorIds (
 Project_id bigint UNSIGNED not null,
 authorIds bigint UNSIGNED
);
 
CREATE TABLE User (
 id bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 fullname varchar(255),
 isAdmin boolean not null,
 password varchar(255),
 username varchar(255)
);
ALTER TABLE Article_authorIds
add foreign key (Article_id)
references Article(id);
 

ALTER TABLE Project_authorIds
add foreign key (Project_id)
references Project(id);
