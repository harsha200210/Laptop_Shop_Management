create database laptop_shop;
use laptop_shop;

create table customer(
                         customer_id int auto_increment primary key ,
                         name varchar(20),
                         NIC varchar(20),
                         address text,
                         email varchar(50),
                         tel int(10),
                         status varchar(20)
);

create table user(
                     user_id int auto_increment primary key ,
                     user_type varchar(20),
                     user_name varchar(20),
                     password text,
                     profile_picture varchar(100)
);

create table employee(
                         employee_id int auto_increment primary key ,
                         name varchar(20),
                         NIC varchar(20),
                         address text,
                         email varchar(50),
                         tel int(10),
                         user_id int,
                         status varchar(20),
                         foreign key (user_id) references user (user_id) on update cascade on delete cascade
);

create table salary(
                       employee_id int not null ,
                       salary decimal(10,2),
                       tax decimal(10,2),
                       ETF decimal(10,2),
                       EPF decimal(10,2),
                       foreign key (employee_id) references employee (employee_id) on update cascade on delete cascade
);

create table orders(
                       order_id varchar(50) primary key ,
                       date date,
                       total_amount decimal(10,2),
                       customer_id int,
                       user_id int,
                       foreign key (customer_id) references customer (customer_id) on update cascade on delete cascade,
                       foreign key (user_id) references user (user_id) on update cascade on delete cascade
);

create table item(
                     item_id int auto_increment primary key ,
                     model text,
                     on_hand_qty int(5),
                     price decimal(10,2),
                     status varchar(20)
);

create table item_detail(
                            item_id int,
                            order_id varchar(50),
                            qty int(5),
                            foreign key (item_id) references item (item_id) on update cascade on delete cascade,
                            foreign key (order_id) references orders (order_id) on update cascade on delete cascade
);

create table supplier(
                         supplier_id int auto_increment primary key ,
                         name varchar(20),
                         NIC varchar(20),
                         address text,
                         email varchar(50),
                         tel int(10),
                         status varchar(20)
);

create table item_supplier_detail(
                                     item_id int,
                                     supplier_id int,
                                     date date,
                                     taken_qty int(10),
                                     taken_price decimal(10,2),
                                     foreign key (item_id) references item (item_id) on update cascade on delete cascade,
                                     foreign key (supplier_id) references supplier (supplier_id) on update cascade on delete cascade
);

create table payment(
                        payment_id int auto_increment primary key ,
                        payment_type text,
                        date date,
                        order_id varchar(50),
                        foreign key (order_id) references orders (order_id) on update cascade on delete cascade
);

create table driver(
                       driver_id int auto_increment primary key ,
                       name varchar(20),
                       NIC varchar(20),
                       address text,
                       email varchar(50),
                       tel int(10),
                       status varchar(20)
);

create table delivery(
                         delivery_id int auto_increment primary key ,
                         delivery_charge decimal(10,2),
                         order_id varchar(50),
                         driver_id int,
                         foreign key (order_id) references orders (order_id) on update cascade on delete cascade,
                         foreign key (driver_id) references driver (driver_id) on update cascade on delete cascade
);

create table configurations(
                       configuration_id int auto_increment primary key ,
                       inside_colombo decimal(10,2),
                       out_of_colombo decimal(10,2),
                       tax_rate int(5),
                       first_order_id varchar(50)
);

