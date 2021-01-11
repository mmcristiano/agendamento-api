create TABLE IF NOT EXISTS paciente (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(255) NOT NULL,
    cpf             VARCHAR(11)  NOT NULL UNIQUE,
    idade           INTEGER      NOT NULL,
    telefone        VARCHAR(12)  NOT NULL
)  ENGINE=INNODB;


INSERT INTO `agenda`.`paciente` (`nome`, `cpf`, `idade`, `telefone`)
VALUES ('Cristiano Maia', '49575606094', '28 ', '021999999999');

