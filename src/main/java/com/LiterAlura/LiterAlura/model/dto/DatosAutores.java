package com.LiterAlura.LiterAlura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;

public record DatosAutores(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") int nacimiento,
        @JsonAlias("death_year") int fallecimiento
        ) {
}
