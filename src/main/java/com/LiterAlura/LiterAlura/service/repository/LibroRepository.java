package com.LiterAlura.LiterAlura.service.repository;

import com.LiterAlura.LiterAlura.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository <Libro, Long> {

    Optional<Libro> findByTitulo(String titulo);

    @Query("select l from Libro l where l.idioma = :idioma")
    List<Libro> buscarPorIdioma(String idioma);
}
