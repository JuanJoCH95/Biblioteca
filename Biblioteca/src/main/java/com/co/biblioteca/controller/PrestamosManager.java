package com.co.biblioteca.controller;

import com.co.biblioteca.dto.LibroDTO;
import com.co.biblioteca.dto.PrestamoDTO;
import com.co.biblioteca.dto.UsuarioDTO;
import com.co.biblioteca.model.LibrosDao;
import com.co.biblioteca.model.PrestamosDao;
import com.co.biblioteca.model.UsuariosDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Clase que contiene la logica de negocio necesaria para gestionar el modulo de prestamos
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class PrestamosManager {
    
    LibrosDao libroDao = new LibrosDao();
    UsuariosDao usuarioDao = new UsuariosDao();
    PrestamosDao prestamoDao = new PrestamosDao();
    
    /**
     * Metodo encargado de validar los campos del formulario
     * @param usuario
     * @param libro
     * @return 
     */
    private Boolean validarDatos(String usuario, String libro) {
        try {
            if(usuario.isEmpty() || libro.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                return false;
            } else {
                int idLibro = Integer.parseInt(libro);
                
                if(usuarioDao.findUsuario(usuario) == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró un usuario en el sistema asociado al documento " + usuario, "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                
                if(libroDao.findLibro(idLibro) == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró un libro en el sistema asociado al ID " + idLibro, "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                
                if(libroDao.findDisponibles(idLibro) == 0) {
                    JOptionPane.showMessageDialog(null, "El libro seleccionado no se encuentra disponible en el momento" + usuario, "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Metodo encargado de gestionar el prestamo de libros
     * @param usuario
     * @param libro 
     */
    public void realizarPrestamo(String usuario, String libro) {
        if(validarDatos(usuario, libro)) {
            try {
                PrestamoDTO prestamo = new PrestamoDTO();
                UsuarioDTO usuarioDto = usuarioDao.findUsuario(usuario);
                LibroDTO libroDto = libroDao.findLibro(Integer.parseInt(libro));
                
                prestamo.setIdPersona(usuarioDto.getIdUsuario());
                prestamo.setIdLibro(Integer.parseInt(libro));
                prestamo.setFechaInicio(getFechaActual());
                prestamo.setEstado("A");
                prestamoDao.insertPrestamo(prestamo);
                
                libroDto.setDisponibles(libroDto.getDisponibles() - 1);
                libroDao.updateLibro(libroDto);
                
                JOptionPane.showMessageDialog(null, "El prestamo se ha realizado exitosamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                System.out.print(ex.getMessage());
                JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Metodo encargado de obtener la fecha actual y darle formato
     * @return 
     */
    private String getFechaActual() {
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        return formato.format(fechaActual);
    }
}
