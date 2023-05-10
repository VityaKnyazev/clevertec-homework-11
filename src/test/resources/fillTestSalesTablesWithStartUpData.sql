Insert Into gift_certificate (name, description, price, duration, create_date) VALUES('Кухни', 'На кухни всех типов', 200.000, '2023-09-25', current_timestamp);
Insert Into gift_certificate (name, description, price, duration, create_date) VALUES('Ноутбуки', 'На ноутбуки', 300.000, '2023-08-20', current_timestamp);
Insert Into gift_certificate (name, description, price, duration, create_date) VALUES('Телевизоры', 'На телевизоры', 100.000, '2023-07-08', current_timestamp);
Insert Into gift_certificate (name, description, price, duration, create_date) VALUES('Телефоны', 'На телефоны', 100.000, '2023-12-25', current_timestamp);
Insert Into gift_certificate (name, description, price, duration, create_date) VALUES('Сад и огород', 'На товары для участков и дач', 200.000, '2023-09-20', current_timestamp);
Insert Into gift_certificate (name, description, price, duration, create_date) VALUES('Ремонт', 'На товары для ремонта помещений', 100.000, '2023-08-24', current_timestamp);

Insert Into tag (name) VALUES('Любимой жене');
Insert Into tag (name) VALUES('Дорогому мужу');
Insert Into tag (name) VALUES('Доче');
Insert Into tag (name) VALUES('Крестнице');
Insert Into tag (name) VALUES('Чернорабочему');
Insert Into tag (name) VALUES('Теще');
Insert Into tag (name) VALUES('Программисту');

Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(1, 3);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(1, 6);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(2, 4);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(2, 5);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(3, 1);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(3, 3);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(4, 2);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(4, 4);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(4, 6);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(4, 7);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(5, 1);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(6, 3);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(6, 4);
Insert Into gift_certificate_tag (gift_certificate_id, tag_id) VALUES(6, 7);
