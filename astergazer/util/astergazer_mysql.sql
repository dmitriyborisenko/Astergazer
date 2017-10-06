CREATE DATABASE astergazer;
USE astergazer;

CREATE TABLE dlp_script (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    modification_stamp varchar(1024),
    PRIMARY KEY (id),
    UNIQUE idx_name (name)

);

CREATE TABLE dlp_context (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE idx_name (name)
);

CREATE TABLE dlp_extension (
    id bigint NOT NULL AUTO_INCREMENT,
    context_id int NOT NULL REFERENCES dlp_context(id),
    script_id int,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE dlp_block (
    id bigint NOT NULL AUTO_INCREMENT,
    script_id int NOT NULL REFERENCES dlp_script(id),
    local_id int NOT NULL,
    caption varchar(50) NOT NULL,
    type varchar(50) NOT NULL,
    is_locked boolean,
    pos_x int NOT NULL,
    pos_y int NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE dlp_block_param (
    id bigint NOT NULL AUTO_INCREMENT,
    block_id bigint NOT NULL REFERENCES dlp_block(id),
    value varchar(1024),
    order_index int,
    PRIMARY KEY (id)
);

CREATE TABLE dlp_connection (
    id bigint NOT NULL AUTO_INCREMENT,
    script_id int NOT NULL REFERENCES dlp_script(id),
    source_block_local_id int NOT NULL,
    target_block_local_id int NOT NULL,
    is_locked boolean,
    PRIMARY KEY (id)
);

CREATE TABLE dlp_checklist (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE idx_name (name)    
);

CREATE TABLE dlp_checklist_entry (
    id bigint NOT NULL AUTO_INCREMENT,
    checklist_id int NOT NULL REFERENCES dlp_checklist(id),
    control_value varchar(1024),
    return_value varchar(1024),
    PRIMARY KEY (id)
);

CREATE TABLE configuration (
    name varchar(50) NOT NULL,
    value varchar(1024),
    PRIMARY KEY (name)
);

CREATE USER astergazer@'%' IDENTIFIED BY 'Ga45Sfg36%9Bc';
GRANT ALL PRIVILEGES ON astergazer . * TO astergazer@'%';
CREATE USER astergazer@localhost IDENTIFIED BY 'Ga45Sfg36%9Bc';
GRANT ALL PRIVILEGES ON astergazer . * TO astergazer@localhost;
FLUSH PRIVILEGES;
