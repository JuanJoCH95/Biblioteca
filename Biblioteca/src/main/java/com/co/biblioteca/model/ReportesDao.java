package com.co.biblioteca.model;

import com.co.biblioteca.db.Conexion;
import com.co.biblioteca.dto.PrestamoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene todos los metodos para realizar las transacciones de reportes en la BD del sistema
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class ReportesDao {
    
    private static final String findAll = "SELECT p.num_documento AS documento, concat(p.nombre_persona, \" \", p.apellido) AS nombre, l.nombre_libro, pr.fecha_inicio, pr.fecha_fin, pr.estado FROM prestamos pr INNER JOIN personas p ON pr.id_persona = p.id_persona INNER JOIN libros l ON pr.id_libro = l.id_libro";
    
    /**
     * Metodo encargado de consultar todos los prestamos en la BD
     * @return
     * @throws SQLException 
     */
    public List<PrestamoDTO> findAll() throws SQLException {
        List<PrestamoDTO> listPrestamos = new ArrayList<>();
        PrestamoDTO prestamoDto = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findAll);
            resultado = preparedStm.executeQuery();
            
            while(resultado.next()) {
                prestamoDto = new PrestamoDTO();
                prestamoDto.setDocumentoPersona(resultado.getString("documento"));
                prestamoDto.setNombrePersona(resultado.getString("nombre"));
                prestamoDto.setNombreLibro(resultado.getString("nombre_libro"));
                prestamoDto.setFechaInicio(resultado.getString("fecha_inicio"));
                prestamoDto.setFechaFin(resultado.getString("fecha_fin"));
                prestamoDto.setEstado(resultado.getString("estado"));
                listPrestamos.add(prestamoDto);
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return listPrestamos;
    }
}
