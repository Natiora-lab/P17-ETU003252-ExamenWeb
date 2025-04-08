CREATE DATABASE gestion;
USE gestion;

CREATE TABLE Credit
(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    montant  FLOAT,
    reste float
);
CREATE TABLE Depense
(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    id_credit INTEGER ,
    montant  float
    
);

