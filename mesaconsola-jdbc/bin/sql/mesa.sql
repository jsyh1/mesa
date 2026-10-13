-- Ejecuta este script solo si la tabla mesa todavía no existe.
-- Si tu proyecto principal ya tiene esta tabla, conserva su definición original.
CREATE TABLE IF NOT EXISTS mesa (
    id BIGINT NOT NULL AUTO_INCREMENT,
    numero INT NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'LIBRE',
    pedido_id BIGINT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_mesa_numero (numero)
);
