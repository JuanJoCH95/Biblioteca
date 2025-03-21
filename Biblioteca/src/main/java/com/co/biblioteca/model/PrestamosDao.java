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
 * Clase que contiene todos los metodos para realizar las transacciones de prestamos en la BD del sistema
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class PrestamosDao {
    
    private static final String insertPrestamo = "INSERT INTO prestamos (id_persona, id_libro, fecha_inicio, fecha_fin, estado) VALUES (?, ?, STR_TO_DATE(?, '%d-%m-%Y'), STR_TO_DATE(?, '%d-%m-%Y'), ?)";
    private static final String findPrestamo = "SELECT * FROM prestamos WHERE id_persona = ? AND id_libro = ? AND estado = ?";
    private static final String updatePrestamo = "UPDATE prestamos SET fecha_devolucion = STR_TO_DATE(?, '%d-%m-%Y'), estado = ? WHERE id_prestamo = ?";
    private static final String findAll = "SELECT * FROM prestamos WHERE estado = ?";
    
    /**
     * Metodo encargado de insertar un nuevo prestamo en la BD
     * @param prestamo
     * @throws SQLException 
     */
    public void insertPrestamo(PrestamoDTO prestamo) throws SQLException {
	Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(insertPrestamo);
            preparedStm.setInt(index++, prestamo.getIdPersona());
            preparedStm.setInt(index++, prestamo.getIdLibro());
            preparedStm.setString(index++, prestamo.getFechaInicio());
            preparedStm.setString(index++, prestamo.getFechaFin());
            preparedStm.setString(index++, prestamo.getEstado());
            preparedStm.executeUpdate();
	} finally {
            Conexion.closeConnection(preparedStm, conn);
	}
    }
    
    /**
     * Metodo encargado de consultar un prestamo activo en la BD
     * @param prestamo
     * @return
     * @throws SQLException 
     */
    public PrestamoDTO findPrestamo(PrestamoDTO prestamo) throws SQLException {
        PrestamoDTO prestamoBD = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findPrestamo);
            preparedStm.setInt(index++, prestamo.getIdPersona());
            preparedStm.setInt(index++, prestamo.getIdLibro());
            preparedStm.setString(index++, "Activo");
            resultado = preparedStm.executeQuery();
            
            if(resultado.next()) {
                prestamoBD = new PrestamoDTO();
                prestamoBD.setIdPrestamo(resultado.getInt("id_prestamo"));
                prestamoBD.setIdPersona(resultado.getInt("id_persona"));
                prestamoBD.setIdLibro(resultado.getInt("id_libro"));
                prestamoBD.setFechaInicio(resultado.getString("fecha_inicio"));
                prestamoBD.setFechaFin(resultado.getString("fecha_fin"));
                prestamoBD.setEstado(resultado.getString("estado"));
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return prestamoBD;
    }
    
    /**
     * Metodo encargado de actualizar un prestamo en la BD
     * @param prestamo
     * @throws SQLException 
     */
    public void updatePrestamo(PrestamoDTO prestamo) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(updatePrestamo);
            preparedStm.setString(index++, prestamo.getFechaDevolucion());
            preparedStm.setString(index++, prestamo.getEstado());
            preparedStm.setInt(index++, prestamo.getIdPrestamo());
            preparedStm.executeUpdate();
        } finally {
            Conexion.closeConnection(preparedStm, conn);
        }
    }
    
    /**
     * Metodo encargado de consultar la lista de prestamos activos en BD
     * @return
     * @throws SQLException 
     */
    public List<PrestamoDTO> findAll() throws SQLException {
        List<PrestamoDTO> listPrestamos = new ArrayList<>();
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findAll);
            preparedStm.setString(index++, "Activo");
            resultado = preparedStm.executeQuery();
            
            while(resultado.next()) {
                PrestamoDTO prestamoBD = new PrestamoDTO();
                prestamoBD.setIdPrestamo(resultado.getInt("id_prestamo"));
                prestamoBD.setIdPersona(resultado.getInt("id_persona"));
                prestamoBD.setIdLibro(resultado.getInt("id_libro"));
                prestamoBD.setFechaInicio(resultado.getString("fecha_inicio"));
                prestamoBD.setFechaFin(resultado.getString("fecha_fin"));
                prestamoBD.setEstado(resultado.getString("estado"));
                listPrestamos.add(prestamoBD);
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return listPrestamos;
    }
}
