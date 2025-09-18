alter table m_inout
	rename column document_id to document_no;

alter table m_inout
	alter column document_no type varchar(255);
	
	select * from product;
	delete from product
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
	
	select * from m_inoutline;
	delete  from m_inoutline;
	select * from m_inout;
	delete from m_inout;

	select * from userinfo;
	
	SELECT * FROM userinfo WHERE userinfo_id = '22';
	
	INSERT INTO userinfo (userinfo_id, isactive, created, createdby, updated, updatedby, value, name)
VALUES ('22', 'Y', NOW(), '22', NOW(), '22', 'gobi02', 'gobisha');

select * from m_inout ORDER BY document_no ASC
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


alter table m_inoutline
	alter column inoutline_id type UUID DEFAULT uuid_generate_v4();
