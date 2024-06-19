DROP DATABASE IF EXISTS ecommerce;
CREATE DATABASE IF NOT EXISTS ecommerce;
USE ecommerce;

DROP TABLE IF EXISTS client;
CREATE TABLE IF NOT EXISTS client (
    idClient CHAR(5) NOT NULL PRIMARY KEY,
    name VARCHAR(15) NOT NULL,
    surname VARCHAR(15) NOT NULL,
    address VARCHAR(60) NOT NULL,
    mail VARCHAR(30) NOT NULL UNIQUE,
    password CHAR(8) NOT NULL,
    typePayment ENUM('NOT_DEFINED', 'CREDIT_CARD', 'DEBIT_CARD', 'PAYPAL', 'PAY_TRANSFER') NOT NULL,
    dtSignup DATE NOT NULL,
    dtSignoff DATE,
    dtLastLogin DATE,
    tmLastLogin TIME
);

DROP TABLE IF EXISTS userLogged;
CREATE TABLE IF NOT EXISTS userLogged (
    idUser CHAR(5) PRIMARY KEY,
    dtLogin DATE NOT NULL,
    tmLogin TIME NOT NULL,
    role ENUM('ADMIN', 'CLIENT') NOT NULL,
    FOREIGN KEY (idUser) REFERENCES Client(idClient)
);

DROP TABLE IF EXISTS article;
CREATE TABLE IF NOT EXISTS article (
    idArticle CHAR(5) NOT NULL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    descr VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    qtaAvailable INT NOT NULL
);

DROP TABLE IF EXISTS cart;
CREATE TABLE IF NOT EXISTS cart (
    idClient CHAR(5) NOT NULL,
    cartProgr INT NOT NULL,
    idArticle CHAR(5) NOT NULL,
    qtaOrdered INT NOT NULL,
    unitPrice DECIMAL(10, 2) NOT NULL,
    totalPrice DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (idClient, idArticle),
    FOREIGN KEY (idClient) REFERENCES Client(idClient),
    FOREIGN KEY (idArticle) REFERENCES Article(idArticle)
);

DROP TABLE IF EXISTS userOrder;
CREATE TABLE IF NOT EXISTS userOrder (
    idOrder INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    idClient CHAR(5) NOT NULL,
    address VARCHAR(60) NOT NULL,
    typePayment ENUM('CREDIT_CARD', 'DEBIT_CARD', 'PAYPAL', 'PAY_TRANSFER') NOT NULL,
    dtOrder DATE NOT NULL,
    totalOrderPrice DECIMAL(10, 2) NOT NULL,
    state ENUM('PROGRESS', 'CONFIRMED', 'REJECTED', 'DELIVERY', 'ARRIVED', 'CLOSED') NOT NULL,
    FOREIGN KEY (idClient) REFERENCES Client(idClient)
);

DROP TABLE IF EXISTS orderDetail;
CREATE TABLE IF NOT EXISTS orderDetail (
    idOrder INT NOT NULL,
    idArticle CHAR(5) NOT NULL,
    qtaOrdered INT NOT NULL,
    unitPrice DECIMAL(10, 2) NOT NULL,
    totalPrice DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (idOrder, idArticle),
    FOREIGN KEY (idOrder) REFERENCES UserOrder(idOrder),
    FOREIGN KEY (idArticle) REFERENCES Article(idArticle)
);
