INSERT INTO public.customer (id, name, role, surname, address, city, email) VALUES ('23739863-abd3-46a2-a4cb-e05f6caf9969', 'Mario', 'CUSTOMER', 'Rossi', 'Via Roma, 22', 'Torino', 'mariorossi@polito.it');
INSERT INTO public.customer (id, name, role, surname, address, city, email) VALUES ('b1058256-77d7-43b3-b7c7-b87094c6fce2', 'Angela', 'CUSTOMER', 'Bianchi', 'Via Garibaldi, 57', 'Palermo', 'angelabianchi@polito.it');
INSERT INTO public.customer (id, name, role, surname, address, city, email) VALUES ('41aeeffa-29d2-42da-a554-6c8b3cd1bd71', 'Luca', 'CUSTOMER', 'Neri', 'Via Mazzini, 15', 'Roma', 'lucaneri@polito.it');
INSERT INTO public.customer (id, name, role, surname, address, city, email) VALUES ('d16a8bb6-55ff-411f-94be-a8473a657394', 'Sofia', 'CUSTOMER', 'Verdi', 'Via Cavour, 91', 'Milano', 'sofiaverdi@polito.it');
INSERT INTO public.customer (id, name, role, surname, address, city, email) VALUES ('b2a636c3-c2d3-4ef5-868c-c9ea1a54c23a', 'Giorgio', 'CUSTOMER', 'Gialli', 'Corso Francia, 1', 'Cagliari', 'giorgiogialli@polito.it');

INSERT INTO public.product (ean, brand, category, name, price) VALUES (1, 'Apple', 'Smartphone', 'iPhone 11', 800);
INSERT INTO public.product (ean, brand, category, name, price) VALUES (2, 'Sony', 'Console', 'Playstation 5', 500);
INSERT INTO public.product (ean, brand, category, name, price) VALUES (3, 'HP', 'PC', '256 G7', 600);
INSERT INTO public.product (ean, brand, category, name, price) VALUES (4, 'Samsung', 'Fridge', 'RS66A8101SL', 700);
INSERT INTO public.product (ean, brand, category, name, price) VALUES (5, 'LG', 'Smart TV', '65QNED816QA', 900);

INSERT INTO public.expert (id, name, role, surname, email, fields) VALUES ('f41c8684-1dd7-47dc-9d6a-fece119da318', 'Paolo', 'EXPERT', 'Russo', 'paolorusso@polito.it', 'Smartphone');
INSERT INTO public.expert (id, name, role, surname, email, fields) VALUES ('2cdbd3f8-6ad5-4812-b167-a1b480390312', 'Marta', 'EXPERT', 'Ferrari', 'martaferrari@polito.it', 'PC');
INSERT INTO public.expert (id, name, role, surname, email, fields) VALUES ('21da0b02-ec85-4a34-811c-74a8d9a7050c', 'Daniele', 'EXPERT', 'Esposito', 'danieleesposito@polito.it', 'Console, Smartphone');
INSERT INTO public.expert (id, name, role, surname, email, fields) VALUES ('483bdefb-0881-4788-ba9b-5097adf0fdcb', 'Beatrice', 'EXPERT', 'Romano', 'beatriceromano@polito.it','Fridge, Smart TV');
INSERT INTO public.expert (id, name, role, surname, email, fields) VALUES ('66280483-c8f7-4912-859a-3322ca09d74c', 'Michele', 'EXPERT', 'Ricci', 'michelericci@polito.it','PC, Smartphone');

INSERT INTO public.manager (id, name, role, surname, email, department) VALUES ('e9ade4f7-7ca5-4042-81e3-1d2c1eab715a', 'Dario', 'MANAGER', 'Greco', 'dariogreco@polito.it', 'dep2');

INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (1, '2023-05-05 12:00:00.000000', 'Problem with smartphone', 'Problem', 3, '056d235a-8eaa-41fc-8d2a-adaedcf317c7', '5ecf9484-eb6d-4470-afaf-f7d935c77ccd', 1);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (2, '2023-05-15 16:00:00.000000', 'Configure PC', 'Configuration', 2, '452d3eb7-7952-48ac-9ab0-e5bdbecf333a', '526fdd4a-788a-4b3b-9444-0844620beb93', 3);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (3, '2023-05-26 17:00:00.000000', 'Broken fridge', 'Fault', null, '2bd77583-af4e-4680-a666-59c3b785c8b8', null, 4);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (4, '2023-05-26 18:00:00.000000', 'Problem with console', 'Problem', null, '056d235a-8eaa-41fc-8d2a-adaedcf317c7', null, 2);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (5, '2023-05-26 18:30:00.000000', 'Broken smartphone', 'Fault', 1, '6719b943-cf2e-4f8d-a2ed-8cd2be30737b', 'b326e786-7f41-40a1-a398-e77088635097', 1);

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

INSERT INTO public.chat (id, creation_date, ticket_id) VALUES (1, '2023-05-06 11:00:00.000000', 1);
INSERT INTO public.chat (id, creation_date, ticket_id) VALUES (2, '2023-05-15 17:00:00.000000', 2);
INSERT INTO public.chat (id, creation_date, ticket_id) VALUES (3, '2023-05-27 11:00:00.000000', 5);

INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (1, 'Hello, I have a problem with my smartphone', '2023-05-06 11:30:00.000000', 'CUSTOMER', 1);
INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (2, 'Hello, try to turn it of and on again', '2023-05-06 12:30:00.000000', 'EXPERT', 1);
INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (3, 'Thank you, it worked', '2023-05-06 15:30:00.000000', 'CUSTOMER', 1);
INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (4, 'Hello, I have a problem with my PC', '2023-05-15 17:30:00.000000', 'CUSTOMER', 2);
INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (5, 'Hello, I''ll come to your house to help you', '2023-05-16 09:00:00.000000', 'EXPERT', 2);
INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (6, 'Hello, I broke my smartphone', '2023-05-27 11:30:00.000000', 'CUSTOMER', 3);

INSERT INTO public.attachment (id, content, name, size, message_id) VALUES (1, 'path/to/a/photo/file', 'photo1', 10, 1);
INSERT INTO public.attachment (id, content, name, size, message_id) VALUES (2, 'path/to/another/photo', 'photo2', 8, 1);