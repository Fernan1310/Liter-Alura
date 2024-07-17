package com.LiterAlura.LiterAlura.model.entity;

import com.LiterAlura.LiterAlura.model.dto.DatosLibro;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Autor autor;
    private String idioma;
    private int numeroDescargas;

    public Libro() {

    }

    public Libro(DatosLibro libro){
        this.id = libro.id();
        this.titulo = libro.titulo();
        this.autor = new Autor(libro.autores().get(0));
        this.idioma = String.valueOf(libro.idiomas().get(0));
        this.numeroDescargas = libro.numeroDescargas();
    }


    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return "**** Libro ****\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNombre() + "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de descargas: " + numeroDescargas + "\n" +
                "*******************\n";
    }
}
