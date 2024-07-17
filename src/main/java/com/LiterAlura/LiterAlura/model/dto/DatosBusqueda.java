package com.LiterAlura.LiterAlura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBusqueda(
        @JsonAlias("count") int contador,
        @JsonAlias("results") List<DatosLibro> resultados
) {
}
