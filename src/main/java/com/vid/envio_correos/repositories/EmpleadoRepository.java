package com.vid.envio_correos.repositories;

import com.vid.envio_correos.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {

    @Query(value = "SELECT emp_apellido1, emp_apellido2, emp_nombre, emp_tipo_identif, " +
            "emp_cedula, emp_fecha_fin_contrato, emp_jefe_codigo FROM empleado",
            nativeQuery = true)
    List<Empleado> findAllEmpleadoFields();


    /*
    @Query(value = "SELECT emp_apellido1, emp_apellido2, emp_nombre, emp_tipo_identif, " +
            "emp_cedula, emp_fecha_fin_contrato, emp_jefe_codigo FROM empleado " +
            "WHERE emp_fecha_fin_contrato = :fechaEspecifica",
            nativeQuery = true)
    List<Empleado> findEmpleadoByFechaFinContrato2(@Param("fechaEspecifica") LocalDate fechaEspecifica);

     */


@Query(value = "SELECT * FROM empleado WHERE emp_fecha_fin_contrato = :fechaEspecifica",
        nativeQuery = true)
List<Empleado> findEmpleadoByFechaFinContrato(@Param("fechaEspecifica") LocalDate fechaEspecifica);
}