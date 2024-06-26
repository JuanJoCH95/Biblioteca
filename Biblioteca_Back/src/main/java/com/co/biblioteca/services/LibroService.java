package com.co.biblioteca.services;

import com.co.biblioteca.exceptions.NotFoundException;
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
     * Metodo encargado de consultar todos los libros de la BD
     */
    public List<Libro> listarLibros() {
        return libroRepo.findAll();
    }

    /**
     * Metodo encargado de consultar un libro en especifico por su ID
     */
    public Libro buscarLibro(Integer id) {
        return libroRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("El libro que desea consultar no existe"));
    }

    /**
     * Metodo encargado de insertar un nuevo libro en la BD
     */
    public Libro guardarLibro(Libro newLibro) {
        return libroRepo.save(newLibro);
    }

    /**
     * Metodo encargado de actualizar la info de un libro en la BD
     */
    public Libro actualizarLibro(Integer id, Libro libroEdit) {
        Libro libroExist = libroRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("El libro que desea actualizar no existe"));

        libroExist.setNombre_libro(libroEdit.getNombre_libro());
        libroExist.setAutor(libroEdit.getAutor());
        libroExist.setCategoria(libroEdit.getCategoria());
        libroExist.setDisponible(libroEdit.getDisponible());

        return libroRepo.save(libroExist);
    }

    /**
     * Metodo encargado de eliminar un libro de la BD
     */
    public boolean eliminarLibro(Integer id) {
        libroRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("El libro que desea eliminar no existe"));

        libroRepo.deleteById(id);
        return true;
    }
}
