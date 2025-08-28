package co.com.crediyarequest.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name="solicitud")
public class ApplicationEntity {
    @Id
    @Column("id_solicitud")
    private Long idRequest;

    @Column("id_estado")
    private Long idState;

    @Column("id_tipo_prestamo")
    private Long longTypeId;

    @Column("monto")
    private double amount;

    @Column("plazo")
    private int term;

    @Column("documento_identidad")
    private String document;
}
