package com.co.biblioteca.controller;

import javax.swing.*;
import com.co.biblioteca.dto.LibroDTO;
import com.co.biblioteca.model.LibrosDao;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene la logica de negocio necesaria para gestionar el modulo de libros
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
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
            if(!libroDao.validateLibro(libro.getNombreLibro())) {
                libroDao.insertLibro(libro);
                JOptionPane.showMessageDialog(null, "Libro registrado exitosamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "El libro ya se encuentra registrado en el sistema", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Metodo encargado de listar los libros registrados en el sistema
     * @return 
     */
    public List<LibroDTO> listarLibros(String name) {
        List<LibroDTO> listaLibros = new ArrayList<>();
        
        try {
            listaLibros = name.isEmpty() ? libroDao.findAll() : libroDao.findSpecific(name);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return listaLibros;
    }
    
    /**
     * Metodo encargado de eliminar libros
     * @param idLibro 
     */
    public void eliminarLibro(int idLibro) {
        try {
            libroDao.deleteLibro(idLibro);
            JOptionPane.showMessageDialog(null, "El libro ha sido eliminado exitosamente del sistema", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Metodo encargado de editar un libro
     * @param libro 
     */
    public void editarLibro(LibroDTO libro) {
        try {
            libroDao.updateLibro(libro);
            JOptionPane.showMessageDialog(null, "La información del libro ha sido actualizada con exito", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Metodo encargado de consultar un libro especifico
     * @param idLibro
     * @return 
     */
    public LibroDTO consultarLibro(int idLibro) {
        LibroDTO libro = null;
        
        try {
            libro = libroDao.findLibro(idLibro);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return libro;
    }
}
