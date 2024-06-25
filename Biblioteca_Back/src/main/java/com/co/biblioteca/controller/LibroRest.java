package com.co.biblioteca.controller;

import com.co.biblioteca.model.Libro;
import com.co.biblioteca.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController()
@RequestMapping("/apiRest/libros")
public class LibroRest {

    @Autowired
    private LibroService libroService;

    @GetMapping("/lista")
    public List<Libro> listarLibros() {
        return libroService.listarLibros();
    }
}
