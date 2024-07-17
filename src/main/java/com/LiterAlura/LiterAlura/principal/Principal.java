package com.LiterAlura.LiterAlura.principal;

import com.LiterAlura.LiterAlura.model.dto.DatosBusqueda;
import com.LiterAlura.LiterAlura.model.dto.DatosLibro;
import com.LiterAlura.LiterAlura.model.entity.Autor;
import com.LiterAlura.LiterAlura.model.entity.Libro;
import com.LiterAlura.LiterAlura.service.repository.AutorRepository;
import com.LiterAlura.LiterAlura.service.ConsumoAPI;
import com.LiterAlura.LiterAlura.service.conversor.ConvierteDatos;
import com.LiterAlura.LiterAlura.service.repository.LibroRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository){
        this.repositorioLibro = libroRepository;
        this.repositorioAutor = autorRepository;
    }

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private String idiomas = """
                            es - Español
                            en - Ingles
                            pt - Portuguese
                            de - Aleman
                            """;

    List<Libro> libroList = new ArrayList<>();
    List<Autor> autorList = new ArrayList<>();

    public void mostrarMenu(){
        var option = -1;

        while(option != 0){
            var menu = """
                    Menu
                    1 - Buscar libro
                    2 - Lista libros en la base de datos
                    3 - Listar autores en la base de datos
                    4 - Listar por autores vivos en determinado año
                    5 - Listar por idiomas
                    
                    0 - Salir
                    """;

            System.out.println(menu);
            option = sc.nextInt();
            sc.nextLine();

            switch (option){
                case 1:
                    buscarLibro();
                    break;

                case 2:
                    listarLibros();
                    break;

                case 3:
                    listarAutores();
                    break;

                case 4:
                    listarPorAnios();
                    break;

                case 5:
                    listarPorIdiomas();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación... ");
                    break;

                default:
                    System.out.println("Escriba una opción valida");
                    break;
            }
        }
    }

    private void buscarLibro(){
        System.out.println("Ingrese el nombre del libro: ");
        var nombreLibro = sc.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        DatosBusqueda datosBusqueda = convierteDatos.obtenerDatos(json, DatosBusqueda.class);
        List<DatosLibro> datosLibroList = datosBusqueda.resultados();

        if(!datosLibroList.isEmpty()){
            DatosLibro libroEncontrado = datosLibroList.get(0);
            Libro libro = new Libro(libroEncontrado);

            //Comprobar si existe el autor
            String nombreAutor = libro.getAutor().getNombre();
            Autor autor = repositorioAutor.findByNombre(nombreAutor);
            if(autor == null){
                autor = new Autor(libroEncontrado.autores().get(0));
                repositorioAutor.save(autor);
            }
            libro.setAutor(autor);

            //Comprobar que el libro no se repita
            String nombreLibroComprobar = libro.getTitulo();
            Optional<Libro> libroComprobar = repositorioLibro.findByTitulo(nombreLibroComprobar);

            if (libroComprobar.isEmpty()){
                repositorioLibro.save(libro);
            }else{
                System.out.println("Imposible agregar libros repetidos, ya se encuentra en la base de datos");
            }

            System.out.println(libro);
        }else{
            System.out.println("No se encontró ningún libro");
        }
    }

    private void listarLibros(){
        libroList = repositorioLibro.findAll();
        libroList.forEach(System.out::println);
    }

    private void listarAutores(){
        autorList = repositorioAutor.findAll();
        autorList.forEach(System.out::println);
    }

    private void listarPorAnios(){
        System.out.println("Ingrese el año en el que desea buscar: ");
        var anioBuscar = sc.nextInt();
        sc.nextLine();
        List<Autor> autoresAnioVivo = repositorioAutor.listarPorAniosVivo(anioBuscar);

        if (!autoresAnioVivo.isEmpty()){
            autoresAnioVivo.forEach(System.out::println);
        }else{
            System.out.println("\nNo se encontró ningún autor vivo en ese año\n");
        }

    }

    private void listarPorIdiomas(){
        List<Libro> librosEncontrados;
        System.out.println(idiomas);
        System.out.println("Ingrese el idioma que desee: ");
        var idiomaBuscar = sc.nextLine();
        if (idiomaBuscar.equalsIgnoreCase("es")){
            librosEncontrados = repositorioLibro.buscarPorIdioma("es");
        } else if (idiomaBuscar.equalsIgnoreCase("en")) {
            librosEncontrados = repositorioLibro.buscarPorIdioma("en");
        }else if (idiomaBuscar.equalsIgnoreCase("pt")) {
            librosEncontrados = repositorioLibro.buscarPorIdioma("pt");
        }else if (idiomaBuscar.equalsIgnoreCase("de")) {
            librosEncontrados = repositorioLibro.buscarPorIdioma("de");
        }else{
            System.out.println("Ingrese una opción valida");
            return;
        }

        if (!librosEncontrados.isEmpty()){
            librosEncontrados.forEach(System.out::println);
        }else{
            System.out.println("No se encontraron libros en el idioma seleccionado");
        }

    }




}
