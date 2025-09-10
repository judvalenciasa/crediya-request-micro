-- Insertar estados
INSERT INTO estados (id_estado, nombre, descripcion) VALUES
(1, 'PENDIENTE', 'Solicitud pendiente de revisión'),
(2, 'APROBADA', 'Solicitud aprobada'),
(3, 'RECHAZADA', 'Solicitud rechazada'),
(4, 'EN_REVISION', 'Solicitud en proceso de revisión')
ON CONFLICT (id_estado) DO NOTHING;

-- Insertar tipos de préstamo
INSERT INTO tipo_prestamos (id_tipo_prestamo, nombre, monto_minimo, monto_maximo, tasa_interes, validacion_automatica) VALUES
(1, 'PERSONAL', 10000, 1000000, 0.15, true),
(2, 'VEHICULAR', 1000001, 5000000, 0.12, false),
(3, 'HIPOTECARIO', 10000000, 14999999, 0.08, false)
ON CONFLICT (id_tipo_prestamo) DO NOTHING;