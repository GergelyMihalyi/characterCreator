create table weapons(id bigint auto_increment, name varchar(30) not null, damage int not null, weight int not null, weapon_type ENUM('CUTTING','CRUSHING','STABBING') not null,  primary key(id));

insert into weapons(name,damage,weight,weapon_type) values ('Axe',10,10,'CUTTING');
insert into weapons(name,damage,weight,weapon_type) values ('Mace',6,20,'CRUSHING');

create table characters(id bigint auto_increment, name varchar(60) not null, age int, armor_class int, base_attack int, health_point int not null, weapon_id bigint,  primary key(id));
ALTER TABLE characters
    ADD CONSTRAINT CHARACTER_WEAPON_ID_FK
        FOREIGN KEY (weapon_id) REFERENCES weapons(id);
insert into characters(name,age,armor_class,base_attack,health_point) values ('John Doe',31,13,15,110);
insert into characters(name,age,armor_class,base_attack,health_point) values ('Jack Doe',31,13,15,100);

create table items(id bigint auto_increment, name varchar(30) not null, description text(500),  primary key(id));

create table character_item(character_id bigint not null,item_id bigint not null,  primary key(character_id,item_id));

ALTER TABLE character_item
    ADD CONSTRAINT CHARACTER_ITEM_CHARACTER
        FOREIGN KEY (character_id) REFERENCES characters(id);

ALTER TABLE character_item
    ADD CONSTRAINT CHARACTER_ITEM_ITEM
        FOREIGN KEY (item_id) REFERENCES items(id);



