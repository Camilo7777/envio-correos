package com.vid.envio_correos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @Column(name = "emp_codigo")
    private String id;

    @Column(name = "emp_apellido1")
    private String apellido1;

    @Column(name = "emp_apellido2")
    private String apellido2;

    @Column(name = "emp_nombre")
    private String nombre;

    @Column(name = "emp_tipo_identif")
    private String tipoIdentif;

    @Column(name = "emp_cedula")
    private Long cedula;

    @Column(name = "emp_fecha_fin_contrato")
    private LocalDate fechaFinContrato;

    @Column(name = "emp_jefe_codigo")
    private String jefeCodigo;

    @Column(name = "emp_sociedad")
    private String empSociedad;
}
