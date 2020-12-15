Login/password - user/pass

Сервис сбора статистики по платежам.
Технологии:
Postgresql, Java, Spring Boot, liquibase, N2O 7

1)Реализовать REST сервис для приема статистики по платежам (stat-payment-gateway)
 -в liquibase создать таблицу

// Таблица платежей
 payments
    id bigint NOT NULL,
    created_dt timestamp without time zone NOT NULL,
    sum numeric(12,2) NOT NULL,
    commission numeric(12,2),
    status_id character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT payments_id_pk PRIMARY KEY (id),
    CONSTRAINT payments_status_id_fk FOREIGN KEY (status_id)
        REFERENCES public.payment_statuses (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT

// Статус платежа добавить в нее значения (Успешно, Отмена, В обработке)
payment_statuses
    id character varying COLLATE pg_catalog."default" NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT payment_statuses_id_pk PRIMARY KEY (id)

 -Созадть REST для заполнения статистики по платежам, api дублирует таблицу payments

 -Сервис должен быть защешен basic authentication

2)Реализовать UI просмотра статистики по платежам
   Информацию по N2O 7 можно посмотреть тут https://wiki.i-novus.ru/questions/28475521/answers/28475527

 - на N2O 7 создать страницу с гридом отображения платежей из таблицы payments
   сортировка по умолчанию по created_dt
   фильтры по полям created_dt(интервал От До), status

 - добавить кнопку удаления записи
