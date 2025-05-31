package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.model.Datos;
import com.aluracursos.desafio.model.DatosLibros;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE  = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu(){
        System.out.println("--------------------------------------------------------");
        System.out.println("- API BOOKS ");
        System.out.println("--------------------------------------------------------");
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);

        // Imprimir datos de la API
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        // Top 10 libros mas descargados
        System.out.println("[INFO] - Top 10 Libros más descargados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        // Buscar libros por nombre
        System.out.println("\nIngrese el nombre del libro a buscar: ");
        var tituloLibro = teclado.nextLine();
        var json1 = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusquedaLibro = conversor.obtenerDatos(json1, Datos.class);

        Optional<DatosLibros> librosBuscado =  datosBusquedaLibro.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (librosBuscado.isPresent()){
            System.out.println("[INFO] - libro encontrado");
            System.out.println(librosBuscado.get());
        } else {
            System.out.println("[WARNING] - Libro no encontrado");
        }

        // Trabajando con estadisticas
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d -> d.numeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("Cantidad media descargas: " + est.getAverage());
        System.out.println("Cantidad minima descargas: " + est.getMin());
        System.out.println("Cantidad maxima descargas: " + est.getMax());

        System.out.println("Cantidada registros evaluados para calcular: " + est.getCount());
    }
}
