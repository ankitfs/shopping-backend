--table structure till 24/4/24

--product_category

CREATE TABLE product_category (
	id serial,
	name varchar(100),
	description varchar(1000),
	active boolean,
	created_at timestamp WITHOUT time ZONE,
	modified_at timestamp WITHOUT time ZONE,
	parent_id int,
	CONSTRAINT pk_product_category PRIMARY KEY (id),
	CONSTRAINT fk_product_category_parent_id foreign key (parent_id) references product_category(id)
);

--product_inventory

CREATE TABLE product_inventory (
	id serial,
	quantity int,
	created_at timestamp WITHOUT time ZONE,
	modified_at timestamp WITHOUT time ZONE,
	CONSTRAINT pk_product_inventory PRIMARY KEY (id)
);

--product

CREATE TABLE product (
	id serial,
	name varchar(500),
	description varchar(1000),
	SKU varchar(500),
	category_id int,
	inventory_id int,
	price decimal,
	discount_id int,
	active boolean,
	created_at timestamp WITHOUT time ZONE,
	modified_at timestamp WITHOUT time ZONE,
	CONSTRAINT pk_product PRIMARY KEY (id),
	CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES product_category(id),
	CONSTRAINT fk_product_discount FOREIGN KEY (discount_id) REFERENCES product_discount(id),
	CONSTRAINT fk_product_inventory FOREIGN KEY (inventory_id) REFERENCES product_inventory(id),
	CONSTRAINT unique_product_sku UNIQUE (sku)
);

--25/4/24


--Product_Category: Adding level column to show tree structure of the category
ALTER TABLE product_category ADD LEVEL int;