create TABLE IF NOT EXISTS medico (
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario                 VARCHAR(100) NOT NULL,
    senha                   VARCHAR(64) NOT NULL,
    nome                    VARCHAR(255) NOT NULL,
    token                   TEXT,
    especialidade           VARCHAR(100) NOT NULL
)  ENGINE=INNODB;