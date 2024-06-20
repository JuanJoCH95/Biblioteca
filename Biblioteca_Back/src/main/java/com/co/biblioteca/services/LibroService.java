package com.co.biblioteca.services;

import com.co.biblioteca.model.Libros;
import com.co.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepo;

    public List<Libros> listarLibros() {
        return libroRepo.findAll();
    }
}
