package co.com.crediyarequest.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name="tipo_prestamo")
public class LoanTypeEntity {

    @Id
    @Column("id_tipo_prestamo")
    private Long IdlongType;

    @Column("nombre")
    private String name;

    @Column("monto_minimo")
    private double minimumAmount;

    @Column("monto_maximo")
    private double maximumAmount;

    @Column("tasa_interes")
    private double interestRate;

    @Column("validacion_automatica")
    private Boolean automaticValidation;

}

