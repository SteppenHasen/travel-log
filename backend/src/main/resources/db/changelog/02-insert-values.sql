--liquibase formatted sql

--changeset postgres:2

insert into
	travellog (id, traveldate, registrationnumber, description, route, owner, startodometer, endodometer)
values
	('852a751c-d99f-4eb8-aded-844ddb386ab8', '2022-12-02', '121 ABT', 'going to Tartu', 'Tallin-Tartu', 'Baltazar', 12554, 2546),
	('b3204c4c-c6f0-4db2-bc32-3ba138ef1f4d', '2022-12-22', '121 ABT', 'trip to Tallin zoo', 'Tartu-Tallin', 'Baltazar', 12554, 2546),
	('6c39d2da-f8d3-4a69-bc31-d3d272832215', '2022-08-15', '121 ABT', 'buying a boat', 'Naberegni Chelni - Odessa', 'Baltazar', 12554, 2546),
	('1121fcb0-0bc0-4417-98ba-5c46dc5e2a86', '2022-08-08', '121 ABT', 'travel in Estonia', 'Pjarnu-Tartu', 'Baltazar', 12554, 2546),
	('a40831a7-ef5f-49f6-ba54-5dfb332cff1e', '2022-01-01', '121 ABT', 'gone to STO', 'Baltazar', 'Tartu-Tartu', 'Baltazar', 12554, 2546),
	('88d497db-f8b2-4c60-9580-4912bffee1eb', '2022-02-12', '532 TM', 'volonteering', 'Kiev-Wrotslaw', 'Dawidek', 12554, 2546),
	('544a6512-8ef8-4966-9e71-baf6ff77f65d', '2022-07-30', '532 TM', 'it was too long', 'Orsk-Kotsk', 'Dawidek', 12554, 2546),
	('4616c755-31fe-420e-ab0f-2a52dda62430', '2022-04-04', '532 TM', 'just wanna go home', 'Tartu-Dnepr', 'Dawidek', 12554, 2546),
	('e81f5626-3235-4a04-a2c7-938676cb0b2a', '2022-03-10', '532 TM', 'sous le siel de Paris', 'Tallin-Paris', 'Dawidek', 12554, 2546),
	('4a7fb1c4-1994-43a0-add6-27b1190ad614', '2022-11-23', '532 TM', 'need to take my cat home', 'Nikopol-Tartu', 'Dawidek', 12554, 2546);