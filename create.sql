CREATE TABLE IF NOT EXISTS USUARIO (
ID int auto_increment primary key,
NOME varchar(16),
EMAIL varchar(48),
SENHA varchar(10),
NIVEL_ACESSO BIT);

CREATE TABLE IF NOT EXISTS DENTISTA (
ID int AUTO_INCREMENT primary key,
NOME varchar(255),
SOBRENOME varchar (255),
MATRICULA varchar (255));

CREATE TABLE IF NOT EXISTS ENDERECO (
ID int AUTO_INCREMENT primary key,
NUMERO varchar(255),
RUA varchar (255),
CIDADE varchar (255),
ESTADO varchar (255));

CREATE TABLE IF NOT EXISTS PACIENTE (
ID int AUTO_INCREMENT primary key,
NOME varchar(255),
SOBRENOME varchar (255),
RG varchar (255),
DATA_CADASTRO TIMESTAMP WITHOUT TIME ZONE,
ENDERECO_ID int);
