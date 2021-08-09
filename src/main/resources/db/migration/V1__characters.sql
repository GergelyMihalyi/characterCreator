create table characters(id bigint auto_increment, name varchar(60), age int, armor_class int, base_attack int,health_point int, weapon_id int, primary key(id),
                        FOREIGN KEY (weapon_id) REFERENCES weapons(id) );

insert into characters(name,age,armor_class,base_attack,health_point) values ('John Doe',31,13,15,110);
insert into characters(name,age,armor_class,base_attack,health_point) values ('Jack Doe',31,13,15,100);


create table weapons(id bigint auto_increment, name varchar(40), weapon_type ENUM('CUTTING','CRUSHING','STABBING'),damage int,weight int, primary key(id));

insert into weapons(name,weapon_type,weight) values ('Axe','cutting',30);
insert into weapons(name,weapon_type,weight) values ('Mace','crushing',50);