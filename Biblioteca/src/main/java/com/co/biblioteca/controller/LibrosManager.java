package com.co.biblioteca.controller;

import javax.swing.*;
import com.co.biblioteca.dto.LibroDTO;
import com.co.biblioteca.model.LibrosDao;

public class LibrosManager {
    
    LibrosDao libroDao = new LibrosDao();
    
    /**
     * Metodo encargado de validar todos los campos
     * @param libro
     * @return 
     */
    public Boolean validarDatos(LibroDTO libro) {
        boolean isValid = true;
        
        if(libro.getNombreLibro().isEmpty() || libro.getAutor().isEmpty() || libro.getIdGenero() == 0 ||
                libro.getStock() == 0 || libro.getDisponibles() == 0) {
            isValid = false;
        }
        return isValid;
    }
    
    /**
     * Metodo encargado de registrar libros
     * @param libro 
     */
    public void registrarLibro(LibroDTO libro) {
        try {
            libroDao.insertLibro(libro);
            JOptionPane.showMessageDialog(null, "Libro registrado exitosamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
