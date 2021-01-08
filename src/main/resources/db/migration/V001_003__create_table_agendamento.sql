create TABLE IF NOT EXISTS agendamento (
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_hora_agendamento   DATETIME     NOT NULL,
    id_paciente             BIGINT       NOT NULL,
    id_medico               BIGINT       NOT NULL,

    FOREIGN KEY (id_paciente) REFERENCES paciente(id),
    FOREIGN KEY (id_medico) REFERENCES medico(id)
)  ENGINE=INNODB;