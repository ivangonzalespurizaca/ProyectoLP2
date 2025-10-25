package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.ComprobanteDePago;

public interface ComprobanteDePagoRepository extends JpaRepository<ComprobanteDePago, Integer> {

    // Búsqueda flexible por DNI exacto, nombre o apellido parcialmente
    @Query("SELECT c FROM ComprobanteDePago c " +
           "WHERE c.dniPagador = :dato " +
           "   OR LOWER(c.nombrePagador) LIKE LOWER(CONCAT('%', :dato, '%')) " +
           "   OR LOWER(c.apellidosPagador) LIKE LOWER(CONCAT('%', :dato, '%'))"+
    	       "ORDER BY c.fechaEmision DESC")
    List<ComprobanteDePago> buscarPorDniONombre(@Param("dato") String dato); 
}
