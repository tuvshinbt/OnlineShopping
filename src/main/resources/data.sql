# CREATE USER 'oss'@'localhost' IDENTIFIED BY 'oss';
# GRANT ALL PRIVILEGES ON OSS.* TO 'oss'@'localhost';
# GRANT ALL PRIVILEGES ON mock_bank_db.* TO 'oss'@'localhost';
# FLUSH PRIVILEGES;

INSERT INTO user VALUES (1 , 'ebatsukh@mum.edu', '$2a$10$snL9/9oR.u6CtqN/g5hJm.GtmHr4wx1ptuOD8.GL8wR4yvHg8Y/7O', 'ENABLED',
                                  'erdenebayar');
INSERT INTO user VALUES (3 , 'ylai@mum.edu', '$2a$10$ibMbCkRcLFo4snX8071GSuJ9bV15Jvl9wFNafEkiSBTFuFt8m9BtO', 'ENABLED', 'yeerick');
INSERT INTO user VALUES (4 , 'akron@mum.edu', '$2a$10$5CROa6Yafyrk/NdPLtUIwemYIjWlh9fSMtGEIuKfmWN98dXMyvUYi', 'ENABLED','akron');
INSERT INTO user VALUES (5 , 'bartiet@mum.edu', '$2a$10$5CROa6Yafyrk/NdPLtUIwemYIjWlh9fSMtGEIuKfmWN98dXMyvUYi', 'ENABLED','bartiet');
INSERT INTO user VALUES (6 , 'gamet@mum.edu', '$2a$10$5CROa6Yafyrk/NdPLtUIwemYIjWlh9fSMtGEIuKfmWN98dXMyvUYi', 'ENABLED','gamet');
INSERT INTO user VALUES (7 , 'panasonic@mum.edu', '$2a$10$5CROa6Yafyrk/NdPLtUIwemYIjWlh9fSMtGEIuKfmWN98dXMyvUYi', 'ENABLED','panasonic');
INSERT INTO user VALUES (14, 'lai_yeerick@hotmail.com','$2a$10$ibMbCkRcLFo4snX8071GSuJ9bV15Jvl9wFNafEkiSBTFuFt8m9BtO', 'ENABLED', 'rick');
INSERT INTO admin VALUES ('Erdenebayar', 'Batsukh', 1);


INSERT INTO customer VALUES ('Yee Rick', 'Lai', 3);
INSERT INTO user VALUES (8, 'ebatsukh@mum.edu','$2a$10$ibMbCkRcLFo4snX8071GSuJ9bV15Jvl9wFNafEkiSBTFuFt8m9BtO', 'ENABLED', 'bay');
INSERT INTO customer VALUES ('Erdenebayar', 'Batsukh', 8);
INSERT INTO user VALUES (9, 'tuvshin0bt@gmail.com','$2a$10$ibMbCkRcLFo4snX8071GSuJ9bV15Jvl9wFNafEkiSBTFuFt8m9BtO', 'ENABLED', 'batt');
INSERT INTO customer VALUES ('Battuvshin', 'Badarch', 9);
INSERT INTO user VALUES (10, 'tbatmunkh@mum.edu','$2a$10$ibMbCkRcLFo4snX8071GSuJ9bV15Jvl9wFNafEkiSBTFuFt8m9BtO', 'ENABLED', 'tamir');
INSERT INTO customer VALUES ('Tamir', 'Batmunkh', 10);
INSERT INTO user VALUES (11, 'pagmaa.erdenebat@gmail.com','$2a$10$ibMbCkRcLFo4snX8071GSuJ9bV15Jvl9wFNafEkiSBTFuFt8m9BtO', 'ENABLED', 'pagma');
INSERT INTO customer VALUES ('Pagmaa', 'Erdenebat', 11);
INSERT INTO user VALUES (12, 'thha@mum.edu','$2a$10$ibMbCkRcLFo4snX8071GSuJ9bV15Jvl9wFNafEkiSBTFuFt8m9BtO', 'ENABLED', 'hong');
INSERT INTO customer VALUES ('Thuy Hong', 'Ha', 12);
INSERT INTO user VALUES (13, 'chpiseth9@gmail.com','$2a$10$ibMbCkRcLFo4snX8071GSuJ9bV15Jvl9wFNafEkiSBTFuFt8m9BtO', 'ENABLED', 'seth');
INSERT INTO customer VALUES ('Chanpiseth', 'Chea', 13);


INSERT INTO user VALUES (2 , 'ebatsukh@mum.edu', '$2a$10$snL9/9oR.u6CtqN/g5hJm.GtmHr4wx1ptuOD8.GL8wR4yvHg8Y/7O', 'ENABLED',
                         'vendor');
INSERT INTO vendor VALUES ('MUM', 'vendor\\2\\2.jpg', 2);
INSERT INTO vendor VALUES ('ADIDAS', 'vendor\\4\\4.png', 4);
INSERT INTO vendor VALUES ('Louis Vuitton', 'vendor\\5\\5.png', 5);
INSERT INTO vendor VALUES ('Rolex', 'vendor\\6\\6.png', 6);
INSERT INTO vendor VALUES ('Oakley', 'vendor\\7\\7.jpg', 7);
INSERT INTO vendor VALUES ('Tesla', 'vendor\\14\\14.png', 14);

INSERT INTO category VALUES (1, 'category\\1\\1.jpg', "Women",'ENABLED',null);
INSERT INTO category VALUES (2, 'category\\2\\2.jpg', "Men",'ENABLED',null);
INSERT INTO category VALUES (3, 'category\\3\\3.jpg', "Clothing",'ENABLED',1);
INSERT INTO category VALUES (4, 'category\\4\\4.jpg', "Bag",'ENABLED',1);
INSERT INTO category VALUES (7, 'category\\7\\7.jpg', "Accessories",'ENABLED',2);
INSERT INTO category VALUES (6, 'category\\6\\6.jpg', "Shoes",'ENABLED',2);
INSERT INTO category VALUES (5, 'category\\5\\5.jpg', "Watches",'ENABLED',7);
INSERT INTO category VALUES (8, 'category\\8\\8.jpg', "Car", 'ENABLED', 2);
INSERT INTO category VALUES (9, 'category\\9\\9.jpg', "Sunglasses", 'ENABLED', 7);

--4000300020001000 100 05/2020 OSS 52557
--4000300020002000 200 05/2020 TAX 10000
--4000300020003001 301 05/2020 V1 52557
--4000300020003002 302 05/2020 V2 52557
--4000300020003003 303 05/2020 V3 52557
--4000300020003004 304 05/2020 V4 52557
--4000300020003005 305 05/2020 V5 52557
INSERT INTO card_detail VALUES(1, 'QraOsmY1Vm/D/pQN6BuLhw==', 'hiE+wqyvnTqJkKbp/OsjdA==', 'wCs2kVuyP/Wl1r1z25CSLvgzFwyz9ICwGILHUe1Ku8c=', 'VISA', 'PDxRRthWgxfXXzrhUr4D1w==', '1000', 'ENABLED', 'PM0IK0uDDL5hne6fEhSiQg==', NULL, 1);
INSERT INTO card_detail VALUES(2, 'QraOsmY1Vm/D/pQN6BuLhw==', 'SCyf4mtAPdUbXAUeuJXTZg==', 'ZsI1f6td8VkuQmP2o7zrsvgzFwyz9ICwGILHUe1Ku8c=', 'VISA', 'E+xQ7+d1sHpp+x5e9vK7UA==', '2000', 'ENABLED', 'VV8Po+pw3CYRaB151M9MdQ==', NULL, NULL);
INSERT INTO card_detail VALUES(3, 'QraOsmY1Vm/D/pQN6BuLhw==', 'U20GoH1QCTVt89PhV9iiBQ==', 'CyIxvjmUDvX8d3MAODdv+PgzFwyz9ICwGILHUe1Ku8c=', 'VISA', 'iU6EhOP10yd+6m1+7hxHgg==', '3001', 'ENABLED', 'PM0IK0uDDL5hne6fEhSiQg==', NULL, 2);
INSERT INTO card_detail VALUES(4, 'QraOsmY1Vm/D/pQN6BuLhw==', 'ZJENHTyNg0q7K2Nj4j5E0g==', 'xljjdxpBdvEfFMGJtIhCx/gzFwyz9ICwGILHUe1Ku8c=', 'VISA', 'qut4mUwppXC4xP3Sbj782w==', '3002', 'ENABLED', 'PM0IK0uDDL5hne6fEhSiQg==', NULL, 4);
INSERT INTO card_detail VALUES(5, 'QraOsmY1Vm/D/pQN6BuLhw==', 'BaLQhxhQS+4sOa3QYkMUBQ==', 'sLmmlQugNFEJyAIbjiRyoPgzFwyz9ICwGILHUe1Ku8c=', 'VISA', 'Zr4BWk3jr48H6wzDWqQczA==', '3003', 'ENABLED', 'PM0IK0uDDL5hne6fEhSiQg==', NULL, 5);
INSERT INTO card_detail VALUES(6, 'QraOsmY1Vm/D/pQN6BuLhw==', '4wkdjUWqjez+bE9w0Jm7bA==', 'ZQrhYoCB5yrJW0uUEwajn/gzFwyz9ICwGILHUe1Ku8c=', 'VISA', 'DNC/nbfdZRgR64YMwhuPlw==', '3004', 'ENABLED', 'PM0IK0uDDL5hne6fEhSiQg==', NULL, 6);
INSERT INTO card_detail VALUES(7, 'QraOsmY1Vm/D/pQN6BuLhw==', 'McuMaay2jbl+KVxe/IXqFg==', 'J78CUbF9WYpMKC9ZCedOOPgzFwyz9ICwGILHUe1Ku8c=', 'VISA', '37vF6TCpGI55juND9qv8XA==', '3005', 'ENABLED', 'PM0IK0uDDL5hne6fEhSiQg==', NULL, 7);
INSERT INTO card_detail VALUES(8, '6zoQ5Q3P0FkN7cMvQQdYtA==', '5wM9IyibGkIBralK1Lb6vA==', 'gMJ7svtpR5YGu5UiYXg0G/gzFwyz9ICwGILHUe1Ku8c=', 'VISA', 'AIZ9VtyuXwhp/Wj+Cz4yug==', '3699', 'ENABLED', 'PM0IK0uDDL5hne6fEhSiQg==', NULL, 3);

INSERT INTO card_detail VALUES(9, 'QraOsmY1Vm/D/pQN6BuLhw==', 'hiE+wqyvnTqJkKbp/OsjdA==', 'wCs2kVuyP/Wl1r1z25CSLvgzFwyz9ICwGILHUe1Ku8c=', 'VISA', 'PDxRRthWgxfXXzrhUr4D1w==', '1000', 'ENABLED', 'PM0IK0uDDL5hne6fEhSiQg==', NULL, 3);
INSERT INTO address VALUES (1, 'Fairfield', '2058871599', 'Iowa', 'ENABLED', '1000 N 4th St', '52557', 3);
INSERT INTO address VALUES (2, 'Fairfield', '2058871599', 'Iowa', 'ENABLED', '52 E. Golden Lane', '52556', 3);

INSERT INTO product VALUES(1, 'White Checker bag, elegant yet gorgeous', 'product\\1\\1.jpg', 'Louis Vuitton White Checker bag',  900, 3, 'ENABLED', 4, 5);
INSERT INTO product VALUES(2, 'Branded Mechanical Watch by ROLEX', 'product\\2\\2.jpg', 'ROLEX VISION',  5000, 4, 'ENABLED', 5, 6 );
INSERT INTO product VALUES(3, 'Limited Edition Adidas Yeezy by Kanye West', 'product\\3\\3.jpg' , 'Adidas Yeezy 1.0 ',  1200, 5, 'ENABLED', 6, 4);
INSERT INTO product VALUES(4, 'Louis Vuitton Most Luxurious Bag ever made', 'product\\4\\4.jpg', 'Louis Vuitton Black Leather',  1169, 8, 'ENABLED', 4, 5);
INSERT INTO product VALUES(5, 'Black Rose', 'product\\5\\5.jpg', 'BR',  222, 10, 'ENABLED', 3, 2);
INSERT INTO product VALUES(6, 'Prepare to amazed by this beautiful brown bag', 'product\\6\\6.jpg', 'Louis Vuitton Brown Leather bag',  800, 3, 'ENABLED', 4, 5);
INSERT INTO product VALUES(7, 'Brown Checker bag, elegant yet gorgeous', 'product\\7\\7.png', 'Louis Vuitton brown Checker bag',  1349, 3, 'ENABLED', 4, 5);
INSERT INTO product VALUES(8, 'Limited Edition Adidas NMD by Yee Rick', 'product\\8\\8.jpg' , 'Adidas NMD 1.0 ',  780, 5, 'ENABLED', 6, 4);
INSERT INTO product VALUES(9, 'State of the art Gold plated shoes', 'product\\9\\9.jpg' , 'Adidas Golden Threshold Shoes',  3598, 5, 'ENABLED', 6, 4);
INSERT INTO product VALUES(10, 'Epic jump shoes, felt like hovering when you jump', 'product\\10\\10.jpg' , 'Adidas Jumping Reaper',  1898, 5, 'ENABLED', 6, 4);
INSERT INTO product VALUES(11, 'You maybe walking, you are actually flying. Shoes for future', 'product\\11\\11.jpg' , 'Adidas Flying Verizon',  2778, 5, 'ENABLED', 6, 4);
INSERT INTO product VALUES(12, 'Gold Plated Branded Mechanical Watch by ROLEX', 'product\\12\\12.jpg', 'ROLEX Peace',  7334, 3, 'ENABLED', 5, 6);
INSERT INTO product VALUES(13, 'High Tech looking sunglasses for all handsome males', 'product\\13\\13.jpg', 'Oakley Flamer',  450, 9, 'ENABLED', 9, 7);
INSERT INTO product VALUES(14, 'Historical sunglasses for tough Man', 'product\\14\\14.jpg', 'Oakley Historic', 533, 8, 'ENABLED', 9, 7);
INSERT INTO product VALUES(15, 'Sunglasses for every working adults', 'product\\15\\15.jpg', 'Oakley Palmer',  330, 12, 'ENABLED', 9, 7);
INSERT INTO product VALUES(16, 'Futuristic embodiment of sunglasses', 'product\\16\\16.jpg', 'Oakley Hunger',  435, 9, 'ENABLED', 9, 7);
INSERT INTO product VALUES(17, 'Cheapest Tesla Electric car in the market.', 'product\\17\\17.jpg', 'Tesla Model 3', 15000, 5, 'ENABLED', 8, 14);
INSERT INTO product VALUES(18, 'Electric Tesla SUV with the greatest price reduction ever', 'product\\18\\18.jpg', 'Tesla Model X',  17000, 4, 'ENABLED', 8, 14);
INSERT INTO product VALUES(19, 'Meditation to do less accomplish more. Seek the highest first', 'product\\19\\19.jpg', 'Transcendental Meditation',  17000, 4, 'ENABLED', NULL , 2);

INSERT INTO order_detail VALUES(1, 150, 3, 1, 1);
INSERT INTO `order` VALUES (1, DATE '2018-4-4', DATE '2018-4-4', 'ENABLED', 1, 1, 10, NULL);
INSERT INTO order_detail VALUES(2, 160, 3, 2, 2);
INSERT INTO `order` VALUES (2, DATE '2018-4-10', DATE '2018-4-10', 'ENABLED', 1, 2, 10, NULL);
INSERT INTO order_detail VALUES(3, 170, 3, 3, 3);
INSERT INTO `order` VALUES (3, DATE '2018-4-15', DATE '2018-4-15', 'ENABLED', 1, 3, 10, NULL);
INSERT INTO order_detail VALUES(4, 180, 3, 4, 4);
INSERT INTO `order` VALUES (4, DATE '2018-5-4', DATE '2018-5-4', 'ENABLED', 1, 4, 10, NULL);
INSERT INTO order_detail VALUES(5, 190, 3, 5, 5);
INSERT INTO `order` VALUES (5, DATE '2018-5-4', DATE '2018-5-4', 'ENABLED', 1, 4, 11, NULL);
INSERT INTO order_detail VALUES(6, 201, 3, 6, 4);
INSERT INTO `order` VALUES (6, DATE '2018-5-4', DATE '2018-5-4', 'ENABLED', 1, 4, 11, NULL);
