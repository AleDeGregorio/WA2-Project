INSERT INTO public.customer (id, username, first_name, last_name, email, city, address) VALUES ('056d235a-8eaa-41fc-8d2a-adaedcf317c7', 'mariorossi', 'Mario', 'Rossi', 'mariorossi@polito.it', 'Torino', 'Via Roma, 22');
INSERT INTO public.customer (id, username, first_name, last_name, email, city, address) VALUES ('452d3eb7-7952-48ac-9ab0-e5bdbecf333a', 'angelabianchi', 'Angela', 'Bianchi', 'angelabianchi@polito.it', 'Palermo', 'Via Garibaldi, 57');
INSERT INTO public.customer (id, username, first_name, last_name, email, city, address) VALUES ('2bd77583-af4e-4680-a666-59c3b785c8b8', 'lucaneri', 'Luca', 'Neri', 'lucaneri@polito.it', 'Roma', 'Via Mazzini, 15');
INSERT INTO public.customer (id, username, first_name, last_name, email, city, address) VALUES ('6a3fe4d1-a3b9-4cf1-b818-aa6d149dddf0', 'sofiaverdi', 'Sofia', 'Verdi', 'sofiaverdi@polito.it', 'Milano', 'Via Cavour, 91');
INSERT INTO public.customer (id, username, first_name, last_name, email, city, address) VALUES ('6719b943-cf2e-4f8d-a2ed-8cd2be30737b', 'giorgiogialli', 'Giorgio', 'Gialli', 'giorgiogialli@polito.it', 'Cagliari', 'Corso Francia, 1');

INSERT INTO public.product (ean, brand, category, name, price) VALUES (1, 'Apple', 'Smartphone', 'iPhone 11', 800);
INSERT INTO public.product (ean, brand, category, name, price) VALUES (2, 'Sony', 'Console', 'Playstation 5', 500);
INSERT INTO public.product (ean, brand, category, name, price) VALUES (3, 'HP', 'PC', '256 G7', 600);
INSERT INTO public.product (ean, brand, category, name, price) VALUES (4, 'Samsung', 'Fridge', 'RS66A8101SL', 700);
INSERT INTO public.product (ean, brand, category, name, price) VALUES (5, 'LG', 'Smart TV', '65QNED816QA', 900);

INSERT INTO public.expert (id, fields, username, first_name, last_name, email) VALUES ('a21c80cd-9c6c-4eed-b992-cd926a72ad4b', 'Smartphone', 'expert1', 'Expert', 'Uno', 'exp@polito.it');
INSERT INTO public.expert (id, fields, username, first_name, last_name, email) VALUES ('526fdd4a-788a-4b3b-9444-0844620beb93', 'PC', 'martaferrari', 'Marta', 'Ferrari', 'martaferrari@polito.it');
INSERT INTO public.expert (id, fields, username, first_name, last_name, email) VALUES ('e743b973-d610-4206-92c3-4e1c1548e995', 'Console, Smartphone', 'danieleesposito', 'Daniele', 'Esposito', 'danieleesposito@polito.it');
INSERT INTO public.expert (id, fields, username, first_name, last_name, email) VALUES ('aa929dd8-bb2b-4823-93eb-728c98080f19', 'Fridge, Smart TV', 'beatriceromano', 'Beatrice', 'Romano', 'beatriceromano@polito.it');
INSERT INTO public.expert (id, fields, username, first_name, last_name, email) VALUES ('b326e786-7f41-40a1-a398-e77088635097', 'PC, Smartphone', 'michelericci', 'Michele', 'Ricci', 'michelericci@polito.it');

INSERT INTO public.manager (id, username, first_name, last_name, email, department) VALUES ('af153852-ce10-49ee-85ad-6100e7371875', 'dariogreco', 'Dario', 'Greco', 'dariogreco@polito.it', 'dep2');

INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (7, '2023-05-05 12:00:00.000000', 'Problem with smartphone', 'Problem', 3, '056d235a-8eaa-41fc-8d2a-adaedcf317c7', 'a21c80cd-9c6c-4eed-b992-cd926a72ad4b', 1);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (2, '2023-05-15 16:00:00.000000', 'Configure PC', 'Configuration', 2, '056d235a-8eaa-41fc-8d2a-adaedcf317c7', 'a21c80cd-9c6c-4eed-b992-cd926a72ad4b', 1);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (3, '2023-05-26 17:00:00.000000', 'Broken fridge', 'Fault', null, '056d235a-8eaa-41fc-8d2a-adaedcf317c7', 'a21c80cd-9c6c-4eed-b992-cd926a72ad4b', 1);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (4, '2023-05-26 18:00:00.000000', 'Problem with console', 'Problem', null, '056d235a-8eaa-41fc-8d2a-adaedcf317c7', 'a21c80cd-9c6c-4eed-b992-cd926a72ad4b', 1);
INSERT INTO public.ticket (id, date_of_creation, description, issue_type, priority_level, customer_id, expert_id, product_ean) VALUES (5, '2023-05-26 18:30:00.000000', 'Broken smartphone', 'Fault', 1, '6719b943-cf2e-4f8d-a2ed-8cd2be30737b', 'b326e786-7f41-40a1-a398-e77088635097', 1);

INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-05 12:00:00.000000', 'OPEN', 7);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-06 11:00:00.000000', 'IN_PROGRESS', 7);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-06 15:00:00.000000', 'REOPENED', 7);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-06 16:00:00.000000', 'CLOSED', 7);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-15 16:00:00.000000', 'OPEN', 2);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-15 17:00:00.000000', 'IN_PROGRESS', 2);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-16 12:00:00.000000', 'CLOSED', 2);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-16 15:00:00.000000', 'REOPENED', 2);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-16 18:00:00.000000', 'RESOLVED', 2);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-16 18:30:00.000000', 'CLOSED', 2);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-26 17:00:00.000000', 'OPEN', 3);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-26 18:00:00.000000', 'OPEN', 4);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-26 18:30:00.000000', 'OPEN', 5);
INSERT INTO public.status_ticket (last_modified_date, status, ticket_id) VALUES ('2023-05-27 11:00:00.000000', 'IN_PROGRESS', 5);

/*INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (7, 1, '2023-05-05 12:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (7, 1, '2023-05-06 11:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (7, 1, '2023-05-06 15:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (7, 1, '2023-05-06 16:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (2, 2, '2023-05-15 16:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (2, 2, '2023-05-15 17:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (2, 2, '2023-05-16 12:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (2, 2, '2023-05-16 15:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (2, 2, '2023-05-16 18:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (2, 2, '2023-05-16 18:30:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (3, 3, '2023-05-26 17:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (4, 4, '2023-05-26 18:00:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (5, 5, '2023-05-26 18:30:00.000000');
INSERT INTO public.ticket_status (ticket_id, status_ticket_id, status_last_modified_date) VALUES (5, 5, '2023-05-27 11:00:00.000000');
*/
INSERT INTO public.chat (id, creation_date, ticket_id) VALUES (1, '2023-05-06 11:00:00.000000', 7);
INSERT INTO public.chat (id, creation_date, ticket_id) VALUES (2, '2023-05-15 17:00:00.000000', 2);
INSERT INTO public.chat (id, creation_date, ticket_id) VALUES (3, '2023-05-27 11:00:00.000000', 5);

INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (1, 'Hello, I have a problem with my smartphone', '2023-05-06 11:30:00.000000', 'CUSTOMER', 1);
INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (2, 'Hello, try to turn it of and on again', '2023-05-06 12:30:00.000000', 'EXPERT', 1);
INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (3, 'Thank you, it worked', '2023-05-06 15:30:00.000000', 'CUSTOMER', 1);
INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (4, 'Hello, I have a problem with my PC', '2023-05-15 17:30:00.000000', 'CUSTOMER', 2);
INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (5, 'Hello, I''ll come to your house to help you', '2023-05-16 09:00:00.000000', 'EXPERT', 2);
INSERT INTO public.message (id, content, sending_date, sent_by, chat_id) VALUES (6, 'Hello, I broke my smartphone', '2023-05-27 11:30:00.000000', 'CUSTOMER', 3);

/*INSERT INTO public.attachment (id, content, name, size, message_id) VALUES (1, 'path/to/a/photo/file', 'photo1', 10, 1);
INSERT INTO public.attachment (id, content, name, size, message_id) VALUES (2, 'path/to/another/photo', 'photo2', 8, 1);*/


