package com.co.biblioteca.services;

import com.co.biblioteca.model.Libro;
import com.co.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepo;

    /**
     * Metodo encargado de consultar todos los libros en la BD
     * @return
     */
    public List<Libro> listarLibros() {
        return libroRepo.findAll();
    }
}
