/*

INSERT INTO role(id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role(id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user( id, title, first_name, last_name, address, city, birth_date, username, email, password, date_created ) VALUES (1, 'Mr', 'testFirstName' , 'testLastName', 'Musterstrasse 42', '8099 Musterhausen', {ts '1986-05-19 00:00:00.00'}, 'musterMAA', 'testEmail@testUser.com', 'testPassword1234', {ts '2013-01-19 16:00:00.00'});

INSERT INTO product( id, name, description, tags, specifications, price, number_in_stock, images, external_links, date_updated ) VALUES (1, 'Apple iPhone X' , '(5.80", 256GB, 12MP, Space Gray)' , 'smartphone,mobilephone,apple,iphone', '[{"resolution": "2436 x 1125 Pixels"}]', 1389, 5,'https://static.digitecgalaxus.ch/Files/9/8/3/4/4/8/7/iPhone-X-Space-Gray-Final.jpg?fit=inside%7C252:318&output-format=progressive-jpeg','www.apple.com', {ts '2017-11-22 15:00:00.00'});

INSERT INTO review(id, text, rating, date_created, user_id, product_id) VALUES (1,'testRating',5,{ts '2017-01-01 00:00:00.00'}, 1,1);

insert into user_role(user_id, roles_id) values(1, 1);

*/