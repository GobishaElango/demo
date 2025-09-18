CREATE table userinfo(
	userinfo_id VARCHAR(32),
	isactive VARCHAR(1) default 'Y',
	created TIMESTAMP NOT NULL default current_timestamp,
	createdby VARCHAR(255),
	updated TIMESTAMP NOT NULL default current_timestamp,
	updatedby Varchar(255),
	value VARCHAR(255) NOT NULL UNIQUE,
	name VARCHAR(355) NOT NULL,
	CONSTRAINT userinfo_pkey primary key(userinfo_id),
	CONSTRAINT userinfo_isactive_chk check(isactive in('Y','N'))
);
select * from userinfo
insert into userinfo(userinfo_id,isactive,createdby,updatedby,value,name)values('1','Y','22','22','gobi01','gobisha');

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

\dx

CREATE TABLE product (
    product_id VARCHAR(32), -- UUID type with default generation
    isactive VARCHAR(1) DEFAULT 'Y',
    created TIMESTAMP NOT NULL DEFAULT current_timestamp,
    createdby VARCHAR(255),
    updated TIMESTAMP NOT NULL DEFAULT current_timestamp,
    updatedby VARCHAR(255),
    value VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    uom VARCHAR(255) NOT NULL,
    stock VARCHAR(255) NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (product_id),
    CONSTRAINT product_createdby_fkey FOREIGN KEY (createdby) 
        REFERENCES userinfo(userinfo_id),
    CONSTRAINT product_updatedby_fkey FOREIGN KEY (updatedby) 
        REFERENCES userinfo(userinfo_id),
    CONSTRAINT product_isactive_chk CHECK (isactive IN ('Y', 'N'))
);

drop table product
select * from product
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 1, 'PROD001', 'Apple', 'kg', '1000');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 2, 'PROD002', 'Aachi Chilli Powder - 100g', 'pcs', '3452');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 3, 'PROD003', 'Gold Winner Refined Sunflower Oil - 1L', 'pcs', '1300');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 4, 'PROD004', 'Sugar', 'kg', '4366');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 5, 'PROD005', 'MTR Gulabjamun', 'kg', '1346');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 6, 'PROD006', 'Aashirvaad Whole Wheat Flour - 1Kg', 'pcs', '6752');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 7, 'PROD007', 'Kit-Kat', 'pcs', '1504');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 8, 'PROD008', 'Tomato', 'kg', '6766');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 9, 'PROD009', 'Onion', 'kg', '1357');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 10, 'PROD010', 'Bread - Whole Wheat', 'pcs', '1200');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 11, 'PROD011', 'Milk - Full Cream 1L', 'pcs', '3500');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 12, 'PROD012', 'Cheese - Mozzarella 200g', 'pcs', '800');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 13, 'PROD013', 'Butter - 500g', 'pcs', '950');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 14, 'PROD014', 'Eggs - Dozen', 'pcs', '2200');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 15, 'PROD015', 'Chicken Breast - 1kg', 'kg', '650');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 16, 'PROD016', 'Lettuce - Fresh', 'kg', '500');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 17, 'PROD017', 'Tomato Ketchup - 500ml', 'pcs', '1200');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 18, 'PROD018', 'Orange Juice - 1L', 'pcs', '1300');
INSERT INTO product (isactive, product_id, value, name, uom, stock)
VALUES ('Y', 19, 'PROD019', 'Pasta - Spaghetti 500g', 'pcs', '1100');



Drop table customer 
CREATE table customer(
	customer_id VARCHAR(32),
	isactive VARCHAR(1) default 'Y',
	created TIMESTAMP NOT NULL default current_timestamp,
	createdby VARCHAR(255),
	updated TIMESTAMP NOT NULL default current_timestamp,
	updatedby VARCHAR(255),
	value VARCHAR(255) NOT NULL UNIQUE,
	name VARCHAR(255) NOT NULL,
	CONSTRAINT customer_pkey primary key(customer_id),
	CONSTRAINT customer_createdby_fkey foreign key(createdby)
	references userinfo(userinfo_id),
	CONSTRAINT customer_updatedby_fkey foreign key(updatedby)
	references userinfo(userinfo_id),
	CONSTRAINT customer_isactive_chk check(isactive in('Y','N'))
);
select* from customer

insert into customer(customer_id,value,name)values('CUST001','CUST001','Anu');
insert into customer(customer_id,value,name)values('CUST002','CUST002','Veni');
insert into customer(customer_id,value,name)values('CUST003','CUST003','Jai');
insert into customer(customer_id,value,name)values('CUST004','CUST004','Anu');
insert into customer(customer_id,value,name)values('CUST005','CUST005','Gobi');
insert into customer(customer_id,value,name)values('CUST006','CUST006','Elango');
insert into customer(customer_id,value,name)values('CUST007','CUST007','Jai');
insert into customer(customer_id,value,name)values('CUST008','CUST008','veni');
insert into customer(customer_id,value,name)values('CUST009','CUST009','Gobi');
insert into customer(customer_id,value,name)values('CUST010','CUST010','Gobisha');


ALTER TABLE customer
    ALTER COLUMN customer_id TYPE VARCHAR(255);
	
	
create table m_inout(
	inout_id UUID DEFAULT uuid_generate_v4(),
	isactive VARCHAR(1) default 'Y',
	created TIMESTAMP NOT NULL default current_timestamp,
	createdby VARCHAR(255),
	updated TIMESTAMP NOT NULL default current_timestamp,
	updatedby VARCHAR(255),
	document_no VARCHAR(255) NOT NULL UNIQUE,
	document_date DATE NOT NULL,
	customer_id VARCHAR(255) NOT NULL,
	CONSTRAINT inout_pkey primary key(inout_id),
	CONSTRAINT inout_customerid_fkey foreign key(customer_id)
	references customer(customer_id),
	CONSTRAINT inout_createdby_fkey foreign key(createdby)
	references userinfo(userinfo_id),
	CONSTRAINT inout_updatedby_fkey foreign key(updatedby)
	references userinfo(userinfo_id),
	CONSTRAINT inout_isactive_chk check(isactive in('Y','N'))
);

drop table m_inout;
drop table m_inoutline;

create table m_inoutline(
	inoutline_id UUID DEFAULT uuid_generate_v4(),
	isactive VARCHAR(1) default 'Y',
	created TIMESTAMP NOT NULL default current_timestamp,
	createdby VARCHAR(255),
	updated TIMESTAMP NOT NULL default current_timestamp,
	updatedby VARCHAR(255),
	inout_id UUID,
	quantity NUMERIC(255) NOT NULL default 0,
	product_id VARCHAR(255) NOT NULL,
	CONSTRAINT inoutline_pkey primary key(inoutline_id),
	CONSTRAINT inoutline_inoutid_fkey foreign key(inout_id)
	references m_inout(inout_id),
	CONSTRAINT inoutline_productid_fkey foreign key(product_id)
	references product(product_id),
	CONSTRAINT inoutline_createdby_fkey foreign key(createdby)
	references userinfo(userinfo_id),
	CONSTRAINT inoutline_updatedby_fkey foreign key(updatedby)
	references userinfo(userinfo_id)
);


select * from userinfo
select * from product
select * from customer
select * from m_inout
select * from m_inoutline


