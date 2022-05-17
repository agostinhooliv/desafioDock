CREATE DATABASE IF NOT EXISTS desafiodock;

USE desafiodock;

CREATE TABLE `pessoa` (
  `id_pessoa` bigint NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_pessoa`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO pessoa (id_pessoa, cpf, data_nascimento, nome)
VALUES(1, '00957552416', '1990-10-10', 'USUARIO TESTE');

CREATE TABLE `conta` (
  `id_conta` bigint NOT NULL AUTO_INCREMENT,
  `flag_ativo` bit(1) NOT NULL,
  `limite_saque_diario` double DEFAULT NULL,
  `saldo` double DEFAULT NULL,
  `tipo_conta` int DEFAULT NULL,
  `id_pessoa` bigint NOT NULL,
  PRIMARY KEY (`id_conta`),
  KEY `FKd3dfwkx7498nouox7qtf6vxfi` (`id_pessoa`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transacoes` (
  `id_transacao` bigint NOT NULL AUTO_INCREMENT,
  `dt_transacao` datetime DEFAULT NULL,
  `valor` double DEFAULT NULL,
  `conta_id_conta` bigint NOT NULL,
  PRIMARY KEY (`id_transacao`),
  KEY `FKh2t3qc66lpg8lqk3ca1d7yr22` (`conta_id_conta`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
