package com.co.biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_libro;

    private String nombre_libro;
    private String autor;
    private String categoria;
    private String disponible;

    public Libros() {
        super();
    }

    public Libros(Integer id_libro, String nombre_libro, String autor, String categoria, String disponible) {
        super();
        this.id_libro = id_libro;
        this.nombre_libro = nombre_libro;
        this.autor = autor;
        this.categoria = categoria;
        this.disponible = disponible;
    }

    public Integer getId_libro() {
        return id_libro;
    }

    public void setId_libro(Integer id_libro) {
        this.id_libro = id_libro;
    }

    public String getNombre_libro() {
        return nombre_libro;
    }

    public void setNombre_libro(String nombre_libro) {
        this.nombre_libro = nombre_libro;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }
}
