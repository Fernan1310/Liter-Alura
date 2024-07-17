package com.LiterAlura.LiterAlura.model.entity;

import com.LiterAlura.LiterAlura.model.dto.DatosAutores;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String nombre;
    private int nacimiento;
    private int fallecimineto;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {

    }

    public Autor(DatosAutores datosAutores){
        this.nombre = datosAutores.nombre();
        this.nacimiento = datosAutores.nacimiento();
        this.fallecimineto = datosAutores.fallecimiento();
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(int nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getFallecimineto() {
        return fallecimineto;
    }

    public void setFallecimineto(int fallecimineto) {
        this.fallecimineto = fallecimineto;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }


    @Override
    public String toString() {

        String nombres = libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining("; "));

        return  "****** Autor ******" +
                "\nNombre: " + nombre +
                "\nAño de nacimiento: " + nacimiento +
                "\n Año de fallecimineto: " + fallecimineto +
                "\nLibros: [ " + nombres + " ]"+
                "\n*******************\n";
    }
}
