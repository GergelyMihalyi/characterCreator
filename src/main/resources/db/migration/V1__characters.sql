create table weapons(id bigint auto_increment, name varchar(30), damage int, weight int, weapon_type ENUM('CUTTING','CRUSHING','STABBING'),  primary key(id));

insert into weapons(name,damage,weight,weapon_type) values ('Axe',10,10,'CUTTING');
insert into weapons(name,damage,weight,weapon_type) values ('Mace',6,20,'CRUSHING');

create table characters(id bigint auto_increment, name varchar(60), age int, armor_class int, base_attack int,health_point int, weapon_id bigint,  primary key(id));
ALTER TABLE characters
    ADD CONSTRAINT CHARACTER_WEAPON_ID_FK
        FOREIGN KEY (weapon_id) REFERENCES weapons(id);
insert into characters(name,age,armor_class,base_attack,health_point) values ('John Doe',31,13,15,110);
insert into characters(name,age,armor_class,base_attack,health_point) values ('Jack Doe',31,13,15,100);



