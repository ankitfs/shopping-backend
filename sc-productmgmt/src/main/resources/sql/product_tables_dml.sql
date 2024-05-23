--24/4/24

insert into product_category  ("name",description, active, created_at, parent_id) values ('women','women', true, now(), null);
insert into product_category  ("name",description, active, created_at, parent_id) values ('kurti','kurtis', true, now(), (select id from product_category where name='women'));

insert into product ("name", description, sku, category_id, price, active, created_at) values ('choodi dar kurtis', 'choodi dar kurtis', 'PO1-101', 2, 110, true, now() )

insert into product_inventory ( quantity , created_at) values(10, now());

--update product_inventory reference into product table
update product set inventory_id = 1, discount_id =1  where id=1;

--25/4/24
update product_category set level = 0 where id=1;
update product_category set level = 1  where id=2;

------------------------------------------------------------------------------------------------

--admin_type table
insert into admin_type (admin_type, permissions, created_at) values (1,'BOTH', now());
