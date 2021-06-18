CREATE TABLE IF NOT EXISTS payment_category (
                                  id BIGSERIAL NOT NULL,
                                  name character varying COLLATE pg_catalog."default" NOT NULL,
                                  CONSTRAINT payment_category_id_pk PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS users (
                       id BIGSERIAL NOT NULL,
                       username varchar NOT NULL,
                       password varchar NOT NULL,
                       CONSTRAINT users_id_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS payment_statistic (
                                   id BIGSERIAL NOT NULL,
                                   created_dt timestamp without time zone NOT NULL,
                                   sum numeric(12,2) NOT NULL,
                                   category_id bigint NOT NULL,
                                   comment varchar,
                                   user_id bigint,
                                   CONSTRAINT payment_id_pk PRIMARY KEY (id),
                                   CONSTRAINT payments_category_id_fk FOREIGN KEY (category_id)
                                   REFERENCES public.payment_category (id) MATCH SIMPLE
                                   ON UPDATE RESTRICT
                                   ON DELETE RESTRICT,
                                   CONSTRAINT users_id_fk FOREIGN KEY (user_id)
                                     REFERENCES public.users(id) MATCH SIMPLE
                                     ON UPDATE RESTRICT
                                     ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS files (
                       id BIGSERIAL NOT NULL,
                       file_name character varying COLLATE pg_catalog."default" NOT NULL,
                       url character varying COLLATE pg_catalog."default" NOT NULL,
                       payment_id bigint,
                       CONSTRAINT payment_id_fk FOREIGN KEY (payment_id)
                           REFERENCES public.payment_statistic(id) MATCH SIMPLE
                           ON UPDATE RESTRICT
                           ON DELETE RESTRICT,
                       CONSTRAINT files_id_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS analitics (
                       id BIGSERIAL NOT NULL,
                       user_id bigint,
                       balance bigint,
                       planned_consumption bigint,
                       fact_consumption bigint,
                       expensive_purchase bigint,
                       consumption_category varchar
);

INSERT INTO payment_category (id, name) VALUES (1, 'Продукты'), (2, 'Транспорт'), (3, 'Развлечения'),
                                               (4, 'Романтика'), (5, 'Еда вне дома'), (6, 'Услуги');

INSERT INTO users (username, password) VALUES ('user', '$2y$12$GmXGz3uWFWBFDJpxj/wQQuTg45KKCMhF1YqOgGP0cDYpvxxCOER4S');

INSERT INTO payment_statistic (created_dt, sum, category_id, comment, user_id) VALUES
('1999-12-03T10:15:30', 123, 1, 'Хлебушек из пятерочки', 1),
('2005-12-03T10:15:30', 145, 2, 'На автобус', 1),
('2000-12-03T10:15:30', 1456, 3, 'Тусовка с друзьями', 1),
('2020-12-03T10:15:30', 14, 6, 'Коммуналка', 1);

INSERT INTO analitics(user_id, balance, planned_consumption, fact_consumption, expensive_purchase, consumption_category) VALUES
(1, 10000, 9000, 2000, 700, 'Развлечения');

INSERT INTO files (file_name, url, payment_id) VALUES
('Сбербанк', 'https://htstatic.imgsmail.ru/pic_image/da6085d65b979aee7a14087701fac5f1/840/564/1861289/', 1),
('из кб пива купил', 'https://www.retail.ru/upload/medialibrary/a7f/chek-na-sluzhbe-marketinga-14_1.jpg', 2),
('Пятерка', 'https://check.ofd.ru/assets/top-check.1d7181b.png', 3),
('хз', 'https://allcashbacks.com/web/uploads/new_uploads/2-chek.jpg', 3),
('Tiffany', 'https://habrastorage.org/files/49d/d30/d33/49dd30d330f34d278e65d25d9084ad55.jpeg', 4);




