package com.co.biblioteca.controller;

import com.co.biblioteca.dto.PrestamoDTO;
import com.co.biblioteca.model.ReportesDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase que contiene la logica de negocio necesaria para gestionar el modulo de reportes
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class ReportesManager {
    
    ReportesDao reporteDao = new ReportesDao();
    
    /**
     * Metodo encargado de listar el reporte de todos los prestamos en el sistema
     * @return 
     */
    public List<PrestamoDTO> listar() {
        List<PrestamoDTO> listaPrestamos = new ArrayList<>();
        
        try {
            listaPrestamos = reporteDao.findAll();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return listaPrestamos;
    }
}
