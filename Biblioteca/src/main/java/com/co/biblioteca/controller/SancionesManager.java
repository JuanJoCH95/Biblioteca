package com.co.biblioteca.controller;

import com.co.biblioteca.dto.PrestamoDTO;
import com.co.biblioteca.dto.SancionDTO;
import com.co.biblioteca.model.PrestamosDao;
import com.co.biblioteca.model.SancionesDao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase que contiene la logica de negocio necesaria para gestionar las sanciones
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class SancionesManager {
    
    PrestamosDao prestamoDao = new PrestamosDao();
    SancionesDao sancionesDao = new SancionesDao();
    
    public void sancionar() {
        try {
            List<PrestamoDTO> listPrestamos = new ArrayList<>();
            listPrestamos = prestamoDao.findAll();
        
            for(PrestamoDTO prestamo : listPrestamos) {
                if(calculo(prestamo.getFechaFin()) > 0) {
                    SancionDTO sancionDto = new SancionDTO();
                    sancionDto.setIdPersona(prestamo.getIdPersona());
                    sancionDto.setEstado("Activo");
                    sancionesDao.insertSancion(sancionDto);
                }
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int calculo(String fecha) throws Exception {
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaFin = formato.parse(fecha);
        
        return fechaActual.compareTo(fechaFin);
    }
}
