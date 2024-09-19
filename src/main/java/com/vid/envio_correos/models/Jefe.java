package com.vid.envio_correos.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "q7_datos_jefe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jefe {

    @Id
    @Column(name = "cod_jefe")
    private String codJefe;

    @Column(name = "correo_jefe")
    private String correoJefe;

    @Column(name = "nombres_jefe")
    private String nombresJefe;

    @Column(name = "apellidos_jefe")
    private String apellidosJefe;
}
