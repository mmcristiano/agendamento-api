create TABLE IF NOT EXISTS paciente (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(255) NOT NULL,
    cpf             VARCHAR(11)  NOT NULL UNIQUE,
    idade           INTEGER      NOT NULL,
    telefone        VARCHAR(12)  NOT NULL
)  ENGINE=INNODB;