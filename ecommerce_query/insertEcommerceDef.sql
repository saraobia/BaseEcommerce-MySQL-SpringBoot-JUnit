USE ecommerce;

INSERT INTO client (idClient, Name, Surname, Address, Mail, Password, TypePayment, dtSignup, dtSignoff, dtLastLogin, tmLastLogin)
VALUES
('C0001', 'Alice', 'Rossi', 'Via Roma 10, Milano', 'alice.rossi@mail.com', 'pass1234', 'CREDIT_CARD', '2023-01-15', NULL, '2023-05-10', '08:45:00'),
('C0002', 'Marco', 'Verdi', 'Corso Venezia 22, Roma', 'marco.verdi@mail.com', 'password', 'PAYPAL', '2023-02-20', NULL, '2023-05-12', '09:30:00'),
('C0003', 'Luca', 'Bianchi', 'Piazza Navona 5, Napoli', 'luca.bianchi@mail.com', 'mypwd456', 'DEBIT_CARD', '2023-03-10', NULL, '2023-05-14', '10:15:00'),
('C0004', 'Giulia', 'Neri', 'Via Garibaldi 18, Torino', 'giulia.neri@mail.com', 'giulia78', 'PAY_TRANSFER', '2023-04-25', NULL, '2023-05-16', '11:00:00'),
('C0005', 'Francesca', 'Bruno', 'Viale dei Cipressi 2, Firenze', 'francesca.bruno@mail.com', 'secure12', 'CREDIT_CARD', '2023-05-05', NULL, '2023-05-18', '12:30:00');

INSERT INTO userLogged (idUser, dtLogin, tmLogin, role)
VALUES
('C0001', '2023-05-10', '08:45:00', 'CLIENT'),
('C0002', '2023-05-12', '09:30:00', 'CLIENT'),
('C0003', '2023-05-14', '10:15:00', 'CLIENT'),
('C0004', '2023-05-16', '11:00:00', 'CLIENT'),
('C0005', '2023-05-18', '12:30:00', 'CLIENT');

INSERT INTO article (idArticle, name, descr, price, qtaAvailable)
VALUES
('A0001', 'Laptop', 'High-performance laptop', 1200, 50),
('A0002', 'Smartphone', 'Latest model smartphone', 800, 100),
('A0003', 'Headphones', 'Noise-cancelling headphones', 150, 200),
('A0004', 'Monitor', '4K Ultra HD monitor', 400, 75),
('A0005', 'Keyboard', 'Mechanical keyboard', 100, 150);

INSERT INTO cart (idClient, idArticle, cartProgr, qtaOrdered, unitPrice, totalPrice)
VALUES
('C0001', 'A0001', 0, 1, 1200, 1200),
('C0002', 'A0002', 0, 2, 800, 1600),
('C0003', 'A0003', 0, 3, 150, 450),
('C0004', 'A0004', 0, 1, 400, 400),
('C0005', 'A0005', 0, 2, 100, 200);

INSERT INTO userOrder (idClient, Address, typePayment, dtOrder, totalOrderPrice, state)
VALUES
('C0001', 'Via Roma 10, Milano', 'CREDIT_CARD', '2023-05-10', 1200, 'CONFIRMED'),
('C0002', 'Corso Venezia 22, Roma', 'PAYPAL', '2023-05-12', 1600, 'PROGRESS'),
('C0003', 'Piazza Navona 5, Napoli', 'DEBIT_CARD', '2023-05-14', 450, 'DELIVERY'),
('C0004', 'Via Garibaldi 18, Torino', 'PAY_TRANSFER', '2023-05-16', 400, 'ARRIVED'),
('C0005', 'Viale dei Cipressi 2, Firenze', 'CREDIT_CARD', '2023-05-18', 200, 'CLOSED');

INSERT INTO orderDetail (idOrder, idArticle, qtaOrdered, unitPrice, totalPrice)
VALUES
(1, 'A0001', 1, 1200, 1200),
(2, 'A0002', 2, 800, 1600),
(3, 'A0003', 3, 150, 450),
(4, 'A0004', 1, 400, 400),
(5, 'A0005', 2, 100, 200);
