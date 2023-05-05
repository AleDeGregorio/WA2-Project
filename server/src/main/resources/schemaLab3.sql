INSERT INTO public.customer (id, name, role, surname, address, city, email) VALUES (1, 'Mario', 'CUSTOMER', 'Rossi', 'Via Roma, 22', 'Torino', 'mario@polito.it');
INSERT INTO public.customer (id, name, role, surname, address, city, email) VALUES (2, 'Angela', 'CUSTOMER', 'Bianchi', 'Via Garibaldi, 57', 'Palermo', 'angela@polito.it');
INSERT INTO public.customer (id, name, role, surname, address, city, email) VALUES (3, 'Luca', 'CUSTOMER', 'Neri', 'Via Mazzini, 15', 'Roma', 'luca@polito.it');
INSERT INTO public.customer (id, name, role, surname, address, city, email) VALUES (4, 'Sofia', 'CUSTOMER', 'Verdi', 'Via Cavour, 91', 'Milano', 'sofia@polito.it');
INSERT INTO public.customer (id, name, role, surname, address, city, email) VALUES (5, 'Giorgio', 'CUSTOMER', 'Gialli', 'Corso Francia, 1', 'Cagliari', 'giorgio@polito.it');

INSERT INTO public.device (ean, brand, category, name, price) VALUES (1, 'Apple', 'Smartphone', 'iPhone 11', 800);
INSERT INTO public.device (ean, brand, category, name, price) VALUES (2, 'Sony', 'Console', 'Playstation 5', 500);
INSERT INTO public.device (ean, brand, category, name, price) VALUES (3, 'HP', 'PC', '256 G7', 600);
INSERT INTO public.device (ean, brand, category, name, price) VALUES (4, 'Samsung', 'Fridge', 'RS66A8101SL', 700);
INSERT INTO public.device (ean, brand, category, name, price) VALUES (5, 'LG', 'Smart TV', '65QNED816QA', 900);

INSERT INTO public.expert (id, name, role, surname, fields) VALUES (6, 'Paolo', 'EXPERT', 'Russo', 'Smartphone');
INSERT INTO public.expert (id, name, role, surname, fields) VALUES (7, 'Marta', 'EXPERT', 'Ferrari', 'PC');
INSERT INTO public.expert (id, name, role, surname, fields) VALUES (8, 'Daniele', 'EXPERT', 'Esposito', 'Console, Smartphone');
INSERT INTO public.expert (id, name, role, surname, fields) VALUES (9, 'Beatrice', 'EXPERT', 'Romano', 'Fridge, Smart TV');
INSERT INTO public.expert (id, name, role, surname, fields) VALUES (10, 'Michele', 'EXPERT', 'Ricci', 'PC, Smartphone');

INSERT INTO public.manager (id, name, role, surname, email) VALUES (11, 'Dario', 'MANAGER', 'Greco', 'dario@manager.it');

INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (1, '2023-05-05 12:00:00.000000', 'Problem with smartphone', 'Problem', 3, 1, 6, 1);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (2, '2023-05-15 16:00:00.000000', 'Configure PC', 'Configuration', 2, 2, 7, 3);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (3, '2023-05-26 17:00:00.000000', 'Broken fridge', 'Fault', null, 3, null, 4);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (4, '2023-05-26 18:00:00.000000', 'Problem with console', 'Problem', null, 1, null, 2);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (5, '2023-05-26 18:30:00.000000', 'Broken smartphone', 'Fault', 1, 4, 10, 1);

INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-05 12:00:00.000000', 'OPEN', 1);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-06 11:00:00.000000', 'IN_PROGRESS', 1);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-06 15:00:00.000000', 'RESOLVED', 1);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-06 16:00:00.000000', 'CLOSED', 1);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-15 16:00:00.000000', 'OPEN', 2);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-15 17:00:00.000000', 'IN_PROGRESS', 2);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-16 12:00:00.000000', 'CLOSED', 2);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-16 15:00:00.000000', 'REOPENED', 2);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-16 18:00:00.000000', 'RESOLVED', 2);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-16 18:30:00.000000', 'CLOSED', 2);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-26 17:00:00.000000', 'OPEN', 3);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-26 18:00:00.000000', 'OPEN', 4);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-26 18:30:00.000000', 'OPEN', 5);
INSERT INTO public.status_ticket (last_modified_date, status, id_id) VALUES ('2023-05-27 11:00:00.000000', 'IN_PROGRESS', 5);

INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (1, 1, '2023-05-05 12:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (1, 1, '2023-05-06 11:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (1, 1, '2023-05-06 15:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (1, 1, '2023-05-06 16:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (2, 2, '2023-05-15 16:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (2, 2, '2023-05-15 17:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (2, 2, '2023-05-16 12:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (2, 2, '2023-05-16 15:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (2, 2, '2023-05-16 18:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (2, 2, '2023-05-16 18:30:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (3, 3, '2023-05-26 17:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (4, 4, '2023-05-26 18:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (5, 5, '2023-05-26 18:30:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_id_id, status_last_modified_date) VALUES (5, 5, '2023-05-27 11:00:00.000000');
