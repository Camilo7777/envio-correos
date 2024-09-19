package com.vid.envio_correos.repositories2;

import com.vid.envio_correos.models.Jefe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JefeRepository extends JpaRepository<Jefe, String> {
    

    //Jefe findByCodJefe(String codJefe);


    @Query(value = "SELECT * FROM q7_datos_jefe WHERE cod_jefe = :codJefe", nativeQuery = true)
    Jefe findJefeByCodJefe(@Param("codJefe") String codJefe);

}
