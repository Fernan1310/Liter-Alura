package com.LiterAlura.LiterAlura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("id") long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutores> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") int numeroDescargas

) {
}
