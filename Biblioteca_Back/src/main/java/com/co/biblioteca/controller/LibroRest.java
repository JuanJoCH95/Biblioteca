package com.co.biblioteca.controller;

import com.co.biblioteca.model.Libro;
import com.co.biblioteca.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/lista/{id}")
    public ResponseEntity<Libro> obtenerLibro(@PathVariable Integer id) {
        Libro libro = libroService.buscarLibro(id);
        return ResponseEntity.ok(libro);
    }

    @PostMapping("/nuevo")
    public Libro guardarLibro(@RequestBody Libro newLibro) {
        return libroService.guardarLibro(newLibro);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Integer id, @RequestBody Libro editLibro) {
        Libro libro = libroService.actualizarLibro(id, editLibro);
        return ResponseEntity.ok(libro);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarLibro(@PathVariable Integer id) {
        boolean respuesta = libroService.eliminarLibro(id);
        return ResponseEntity.ok(respuesta);
    }
}
