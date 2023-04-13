create table if not exists  products(
    product_id varchar(15) primary key,
    name varchar(255),
    brand varchar(255),
    category varchar(255),
    price double precision
);

INSERT INTO public.products (product_id, name, brand, category, price) VALUES ('101', 'iPhone', 'Apple', 'smartphone', 900);
INSERT INTO public.products (product_id, name, brand, category, price) VALUES ('102', 'Playstation 5', 'Sony', 'console', 500);
INSERT INTO public.products (product_id, name, brand, category, price) VALUES ('103', '256 G7', 'HP', 'pc', 600);
INSERT INTO public.products (product_id, name, brand, category, price) VALUES ('104', 'RS66A8101SL', 'Samsung', 'fridge', 700);
INSERT INTO public.products (product_id, name, brand, category, price) VALUES ('105', '65QNED816QA', 'LG', 'smart tv', 800);


create table if not exists profiles (
  email varchar(255) primary key,
  password varchar(255),
  name varchar(255),
  surname varchar(255),
  city varchar(255),
  address varchar(255)
);

INSERT INTO public.profiles (email, password, name, surname, city, address) VALUES ('mario@polito.it', 'password', 'Mario', 'Rossi', 'Roma', 'Via Garibaldi, 47');
INSERT INTO public.profiles (email, password, name, surname, city, address) VALUES ('luca@polito.it', 'password', 'Luca', 'Bianchi', 'Torino', 'Via Roma, 71');
INSERT INTO public.profiles (email, password, name, surname, city, address) VALUES ('carla@polito.it', 'password', 'Carla', 'Neri', 'Milano', 'Via Cavour, 14');
INSERT INTO public.profiles (email, password, name, surname, city, address) VALUES ('giorgio@polito.it', 'password', 'Giorgio', 'Gialli', 'Bari', 'Via Dante, 5');
INSERT INTO public.profiles (email, password, name, surname, city, address) VALUES ('angela@polito.it', 'password', 'Angela', 'Verdi', 'Palermo', 'Via Mazzini, 55');
