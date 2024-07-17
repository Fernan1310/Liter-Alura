package com.LiterAlura.LiterAlura.service.repository;

import com.LiterAlura.LiterAlura.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombre(String nombre);

    @Query("select a FROM Autor a where a.nacimiento <= :anio and a.fallecimineto >= :anio")
    List<Autor> listarPorAniosVivo(int anio);

}
