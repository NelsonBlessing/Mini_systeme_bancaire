DROP TABLE IF EXISTS Client ;
CREATE TABLE Client (id_client_Client INT AUTO_INCREMENT NOT NULL,
nom_Client VARCHAR(50),
prenom_Client VARCHAR(50),
adresse_Client VARCHAR(50),
telephone_Client INT,
PRIMARY KEY (id_client_Client)) ENGINE=InnoDB;

DROP TABLE IF EXISTS Compte ;
CREATE TABLE Compte (id_compte_Compte INT AUTO_INCREMENT NOT NULL,
numero_Compte INT,
solde_Compte DOUBLE,
date_creation_Compte DATE,
id_client_Compte INT,
id_client_Client **NOT FOUND**,
PRIMARY KEY (id_compte_Compte)) ENGINE=InnoDB;

DROP TABLE IF EXISTS Operation ;
CREATE TABLE Operation (id_operation_Operation INT AUTO_INCREMENT NOT NULL,
type_Operation VARCHAR(50),
montant_Operation DOUBLE,
date_operation_Operation DATE,
id_compte_Operation INT,
id_compte_Compte **NOT FOUND**,
PRIMARY KEY (id_operation_Operation)) ENGINE=InnoDB;

ALTER TABLE Compte ADD CONSTRAINT FK_Compte_id_client_Client FOREIGN KEY (id_client_Client) REFERENCES Client (id_client_Client);

ALTER TABLE Operation ADD CONSTRAINT FK_Operation_id_compte_Compte FOREIGN KEY (id_compte_Compte) REFERENCES Compte (id_compte_Compte);
