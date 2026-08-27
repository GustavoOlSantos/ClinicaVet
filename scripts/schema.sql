CREATE TABLE `animal` (
  `idAnimal` int NOT NULL AUTO_INCREMENT,
  `idCliente` int NOT NULL,
  `nome` varchar(45) NOT NULL,
  `sexo` varchar(45) NOT NULL,
  `tipo` int NOT NULL,
  `status` int DEFAULT NULL,
  `emergencia` int DEFAULT NULL,
  `internado` int DEFAULT NULL,
  `orcamento` double NOT NULL,
  `observacoes` varchar(1000) DEFAULT NULL,
  `diagnostico` varchar(80) DEFAULT NULL,
  `medicamentos` varchar(1000) DEFAULT NULL,
  `servicos` varchar(200) DEFAULT NULL,
  `data_alta` datetime DEFAULT NULL,
  `data_obito` datetime DEFAULT NULL,
  PRIMARY KEY (`idAnimal`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `animaltipo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `categorias_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cpfCnpj` varchar(45) DEFAULT NULL,
  `telefone` varchar(45) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `qtdAnimal` int DEFAULT NULL,
  `orcamentoTotal` double NOT NULL,
  `formaPagamento` int NOT NULL,
  `parcelas` int NOT NULL,
  `statusPagamento` int NOT NULL,
  `situacao` int NOT NULL,
  `observacao` varchar(1000) DEFAULT NULL,
  `dataCadastro` datetime NOT NULL,
  `dataEncerramento` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `servicos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `valor` double NOT NULL,
  `tipo` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;