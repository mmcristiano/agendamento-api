create TABLE IF NOT EXISTS medico (
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario                 VARCHAR(100) NOT NULL UNIQUE,
    senha                   VARCHAR(64) NOT NULL,
    nome                    VARCHAR(255) NOT NULL,
    token                   TEXT,
    especialidade           VARCHAR(100) NOT NULL

)  ENGINE=INNODB;


INSERT INTO `agenda`.`medico` ( `usuario`, `senha`, `nome`, `token`, `especialidade`) VALUES ('antonio.chacra', '01c853bfb5c9735de12c6bcf33a1f9c8c0c2f5aa1b18a69d267c6f1704429277', 'Antonio Roberto Chacra', NULL, 'Endocrinologia');
INSERT INTO `agenda`.`medico` ( `usuario`, `senha`, `nome`, `token`, `especialidade`) VALUES ('eduardo.mutarelli', '463f06a112c73d418973469e2788d90a2934d0a91ca6db2daf322323429f40a4', 'Eduardo Mutarelli', NULL, 'Neurologia');
INSERT INTO `agenda`.`medico` ( `usuario`, `senha`, `nome`, `token`, `especialidade`) VALUES ('gilberto.camanho', 'e969415da2c8af3831b6a859a3250d201477c8414fc9de6601384efd991adde0', 'Gilberto Luis Camanho', NULL, 'Ortopedia');
