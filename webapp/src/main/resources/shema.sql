
--DROP TABLE IF EXISTS ROLE;
--CREATE TABLE ROLE (ID INT constraint role_pk  PRIMARY KEY, role VARCHAR(255) );
--DROP TABLE IF EXISTS user;
--CREATE TABLE USER (ID INT  constraint user_pk PRIMARY KEY, email VARCHAR(255),  password VARCHAR(255),  fullname VARCHAR(255),  enabled VARCHAR(255) );


--DROP TABLE IF EXISTS contacts;
--CREATE TABLE CONTACTS (ID INT PRIMARY KEY, email VARCHAR(255), url VARCHAR(255), status VARCHAR(255),date DATE);

--DROP TABLE IF EXISTS notes;
--CREATE TABLE NOTES (ID INT PRIMARY KEY, title VARCHAR(255), content VARCHAR(1000000), update VARCHAR(255) );


--DROP TABLE IF EXISTS users_roles;
--CREATE TABLE USERS_ROLES ( user_id int constraint users_roles_user_id_fk  references user, role_id int  constraint users_roles_role_id_fk references role);


---postgress
---CREATE TABLE CONTACTS (ID serial, email VARCHAR(255), url VARCHAR(255), status VARCHAR(255),date DATE);