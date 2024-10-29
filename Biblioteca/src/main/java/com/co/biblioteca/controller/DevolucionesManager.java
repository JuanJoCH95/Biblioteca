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
 * Clase que contiene la logica de negocio necesaria para gestionar el modulo de devoluciones
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class DevolucionesManager {
    
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
                if(usuarioDao.findUsuario(usuario) == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró un prestamo asociado los datos ingresados", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                
                UsuarioDTO usuarioDto = usuarioDao.findUsuario(usuario);
                PrestamoDTO prestamo = new PrestamoDTO();
                prestamo.setIdPersona(usuarioDto.getIdUsuario());
                prestamo.setIdLibro(Integer.parseInt(libro));
                
                if(prestamoDao.findPrestamo(prestamo) == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró un prestamo asociado los datos ingresados", "AVISO", JOptionPane.INFORMATION_MESSAGE);
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
     * Metodo encargado de gestionar la devolucion de libros
     * @param usuario
     * @param libro 
     */
    public void devolverLibro(String usuario, String libro) {
        if(validarDatos(usuario, libro)) {
            try {
                PrestamoDTO prestamo = new PrestamoDTO();
                UsuarioDTO usuarioDto = usuarioDao.findUsuario(usuario);
                LibroDTO libroDto = libroDao.findLibro(Integer.parseInt(libro));
                
                prestamo.setIdPersona(usuarioDto.getIdUsuario());
                prestamo.setIdLibro(Integer.parseInt(libro));
                prestamo = prestamoDao.findPrestamo(prestamo);
                
                prestamo.setFechaFin(getFechaActual());
                prestamo.setEstado("Devuelto");
                prestamoDao.updatePrestamo(prestamo);
                
                libroDto.setDisponibles(libroDto.getDisponibles() + 1);
                libroDao.updateLibro(libroDto);
                
                JOptionPane.showMessageDialog(null, "La devolución se ha realizado exitosamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
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
