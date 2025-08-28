package co.com.crediyarequest.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name="estados")
public class StateEntity {
    @Id
    @Column("id_estado")
    private Long idState;

    @Column("nombre")
    private String name;

    @Column("descripcion")
    private String description;
}


