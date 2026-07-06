CREATE TABLE IF NOT EXISTS pedidos (
    id_pedido       BIGINT         AUTO_INCREMENT PRIMARY KEY,
    id_reserva      BIGINT         NOT NULL,
    id_plato        BIGINT         NOT NULL,
    cantidad        INT            NOT NULL,
    precio_unitario DECIMAL(10,2)  NOT NULL,
    estado          VARCHAR(30)    NOT NULL DEFAULT 'PENDIENTE'
);
