CREATE DATABASE luxurioushotel;
SET TIME_ZONE="+7:00";
SHOW VARIABLES LIKE '%time_zone%';

USE luxurioushotel;

CREATE TABLE amenity(
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

CREATE TABLE room_amenity(
	id_room_type int,
	id_amenity int,
	
	primary key(id_room_type, id_amenity)
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
	image text,
	
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
	reservation_time timestamp,
	create_date timestamp default now(),
	
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
	paid_amount double,
	total decimal,
	create_date timestamp default now(),
	
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

ALTER TABLE room_amenity ADD CONSTRAINT fk_id_room_type_room_amenity FOREIGN KEY(id_room_type) REFERENCES room_type(id);
ALTER TABLE room_amenity ADD CONSTRAINT fk_id_amenity_room_amenity FOREIGN KEY(id_amenity) REFERENCES amenity(id);
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


INSERT INTO bed_type (name)
VALUES ('Single Bed'),
('Double Bed'),
('Queen Bed'),
('King Bed'),
('Super King Bed'),
('Extra  Bed'),
('2 King Bed'),
('3 King Bed')
;

INSERT INTO room_type (name, overview, price, area, capacity, id_bed_type, image)
VALUES ('Standard Room', '', 200, 40, 2, 3,''),
('Superior Roomm', '', 300, 50, 2, 4,''),
('Deluxe Room', '', 500, 60, 2, 5,''),
('Suite Room', '', 1000, 300, 4, 5,''),
('Family Room', '', 900, 200, 6, 8,'')
;

INSERT INTO amenity (description) VALUES
('Free Wi-Fi'),
('Swimming Pool'),
('Fitness Center'),
('24-Hour Reception'),
('Room Service'),
('Laundry Service'),
('Parking'),
('Air Conditioning'),
('Breakfast Included'),
('Airport Shuttle'),
('Spa and Wellness Center'),
('Pet-Friendly'),
('Non-Smoking Rooms'),
('Conference Facilities'),
('Restaurant'),
('Bar/Lounge'),
('Business Center'),
('Cable/Satellite TV'),
('Mini-Bar'),
('Balcony/Terrace')
;

-- Tiện ích cho Standard Room
INSERT INTO room_amenity (id_room_type, id_amenity) VALUES
(1, 1), -- Free Wi-Fi
(1, 4), -- 24-Hour Reception
(1, 7), -- Parking
(1, 8), -- Air Conditioning
(1, 17), -- Cable/Satellite TV
(1, 14), -- Conference Facilities
(1, 13); -- Non-Smoking Rooms

-- Tiện ích cho Superior Room
INSERT INTO room_amenity (id_room_type, id_amenity) VALUES
(2, 1), -- Free Wi-Fi
(2, 4), -- 24-Hour Reception
(2, 6), -- Laundry Service
(2, 8), -- Air Conditioning
(2, 13), -- Non-Smoking Rooms
(2, 14), -- Conference Facilities
(2, 17); -- Cable/Satellite TV

-- Tiện ích cho Deluxe Room
INSERT INTO room_amenity (id_room_type, id_amenity) VALUES
(3, 1), -- Free Wi-Fi
(3, 2), -- Swimming Pool
(3, 3), -- Fitness Center
(3, 4), -- 24-Hour Reception
(3, 5), -- Room Service
(3, 8), -- Air Conditioning
(3, 10), -- Airport Shuttle
(3, 13), -- Non-Smoking Rooms
(3, 17), -- Cable/Satellite TV
(3, 18), -- Mini-Bar
(3, 19); -- Balcony/Terrace

-- Tiện ích cho Suite Room
INSERT INTO room_amenity (id_room_type, id_amenity) VALUES
(4, 1), -- Free Wi-Fi
(4, 2), -- Swimming Pool
(4, 3), -- Fitness Center
(4, 4), -- 24-Hour Reception
(4, 5), -- Room Service
(4, 8), -- Air Conditioning
(4, 9), -- Breakfast Included
(4, 10), -- Airport Shuttle
(4, 12), -- Pet-Friendly
(4, 13), -- Non-Smoking Rooms
(4, 16), -- Bar/Lounge
(4, 17), -- Cable/Satellite TV
(4, 18), -- Mini-Bar
(4, 19); -- Balcony/Terrace

-- Tiện ích cho Family Room
INSERT INTO room_amenity (id_room_type, id_amenity) VALUES
(5, 1), -- Free Wi-Fi
(5, 2), -- Swimming Pool
(5, 3), -- Fitness Center
(5, 4), -- 24-Hour Reception
(5, 5), -- Room Service
(5, 7), -- Parking
(5, 8), -- Air Conditioning
(5, 9), -- Breakfast Included
(5, 11), -- Spa and Wellness Center
(5, 13), -- Non-Smoking Rooms
(5, 15), -- Restaurant
(5, 17), -- Cable/Satellite TV
(5, 18), -- Mini-Bar
(5, 19); -- Balcony/Terrace