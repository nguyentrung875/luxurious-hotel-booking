CREATE DATABASE luxurioushotel;
SET TIME_ZONE="+7:00";
SHOW VARIABLES LIKE '%time_zone%';

USE luxurioushotel;

CREATE TABLE amenities(
	id int auto_increment,
	description varchar(255),
	
	primary key(id)
);

CREATE TABLE food_menu(
	id_menu int,
	id_food int,
	
	primary key(id_menu, id_food)
);

CREATE TABLE food(
	id int auto_increment,
	name varchar(255),
	description text,
	price decimal,
	
	primary key(id)
);

CREATE TABLE room_status(
	id int auto_increment,
	description varchar(255),
	
	primary key(id)
);

CREATE TABLE room_amenties(
	id_room_type int,
	id_amenities int,
	
	primary key(id_room_type, id_amenities)
);

CREATE TABLE bed_type(
	id int auto_increment,
	name varchar(255),
	
	primary key(id)
);

CREATE TABLE menu(
	id int auto_increment,
	name varchar(255),
	
	primary key(id)
);

CREATE TABLE orders(
	id int auto_increment,
	id_food int,
	id_reservation int,
	quantity int,
	order_time timestamp default now(),
	id_status int,
	primary key(id)
);

CREATE TABLE order_status(
	id int auto_increment,
	name varchar(100),
	primary key(id)
);


CREATE TABLE room(
	id int auto_increment,
	name varchar(255) unique,
	id_room_type int,
	
	primary key(id)
);

CREATE TABLE room_type(
	id int auto_increment,
	name varchar(255) unique,
	overview varchar(255),
	price decimal,
	area float,
	capacity int,
	id_bed_type int,
	images text,
	
	primary key(id)
);

CREATE TABLE tables(
	id int auto_increment,
	name varchar(255),
	
	primary key(id)
);

CREATE TABLE room_booking(
	id_room int ,
	id_booking int,
	
	primary key(id_room, id_booking)
);

CREATE TABLE user(
	id int auto_increment,
	username varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE,
	password varchar(100),
	first_name varchar(100),
	last_name varchar(100),
	dob date,
	phone varchar(20) unique not null,
	email varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE,
	address text,
	summary text,
	id_role int,
	
	primary key(id)
);

CREATE TABLE reservation(
	id int auto_increment,
	id_guest int ,
	id_table int,
	guest_number int,
	reservation_time timestamp default now(),
	
	primary key(id)
);


CREATE TABLE booking(
	id int auto_increment,
	check_in timestamp,
	check_out timestamp,
	room_number int,
	id_guest int ,
	adult_number int,
	children_number int,
	id_payment_status int,
	id_payment int,
	id_status int,
	paid_amount decimal,
	total decimal,
	
	primary key(id)
);

CREATE TABLE role(
	id int auto_increment,
	name varchar(50) unique,
	description varchar(100),
	primary key(id)
);

CREATE TABLE payment_status(
	id int auto_increment,
	name varchar(50),
	primary key(id)
	
);

CREATE TABLE payment_method(
	id int auto_increment,
	name varchar(50),
	primary key(id)
);

CREATE TABLE booking_status(
	id int auto_increment,
	name varchar(50),
	primary key(id)
);

ALTER TABLE room_amenties ADD CONSTRAINT fk_id_room_type_room_amenties FOREIGN KEY(id_room_type) REFERENCES room_type(id);
ALTER TABLE room_amenties ADD CONSTRAINT fk_id_amenities_room_amenties FOREIGN KEY(id_amenities) REFERENCES amenities(id);
ALTER TABLE food_menu ADD CONSTRAINT fk_id_menu_food_menu FOREIGN KEY(id_menu) REFERENCES menu(id);
ALTER TABLE food_menu ADD CONSTRAINT fk_id_food_food_menu FOREIGN KEY(id_food) REFERENCES food(id);
ALTER TABLE orders ADD CONSTRAINT fk_id_food_orders FOREIGN KEY(id_food) REFERENCES food(id);
ALTER TABLE orders ADD CONSTRAINT fk_id_reservation_orders FOREIGN KEY(id_reservation) REFERENCES reservation(id);
ALTER TABLE orders ADD CONSTRAINT fk_id_status_orders FOREIGN KEY(id_status) REFERENCES order_status(id);
ALTER TABLE room_type ADD CONSTRAINT fk_id_bed_type_room_type FOREIGN KEY(id_bed_type) REFERENCES bed_type(id);
ALTER TABLE room ADD CONSTRAINT fk_id_room_type_room FOREIGN KEY(id_room_type) REFERENCES room_type(id);
ALTER TABLE reservation ADD CONSTRAINT fk_id_guest_reservation FOREIGN KEY(id_guest) REFERENCES user(id);
ALTER TABLE reservation ADD CONSTRAINT fk_id_table_reservation FOREIGN KEY(id_table) REFERENCES tables(id);
ALTER TABLE room_booking ADD CONSTRAINT fk_id_room_room_booking FOREIGN KEY(id_room) REFERENCES room(id);
ALTER TABLE room_booking ADD CONSTRAINT fk_id_booking_room_booking FOREIGN KEY(id_booking) REFERENCES booking(id);
ALTER TABLE user ADD CONSTRAINT fk_id_role_user FOREIGN KEY(id_role) REFERENCES user(id);
ALTER TABLE booking ADD CONSTRAINT fk_id_guest_booking FOREIGN KEY(id_guest) REFERENCES user(id);
ALTER TABLE booking ADD CONSTRAINT fk_id_payment_status_booking FOREIGN KEY(id_payment_status) REFERENCES payment_status(id);
ALTER TABLE booking ADD CONSTRAINT fk_id_payment_booking FOREIGN KEY(id_payment) REFERENCES payment_method(id);
ALTER TABLE booking ADD CONSTRAINT fk_id_status_booking FOREIGN KEY(id_status) REFERENCES booking_status(id);




