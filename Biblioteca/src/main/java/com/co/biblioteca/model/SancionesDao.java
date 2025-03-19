package com.co.biblioteca.model;

import com.co.biblioteca.db.Conexion;
import com.co.biblioteca.dto.SancionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que contiene todos los metodos para realizar las transacciones de sanciones en la BD del sistema
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class SancionesDao {
    
    private static final String insertSancion = "INSERT INTO sanciones (id_prestamo, estado) VALUES (?, ?)";
    private static final String findSancionByUsuario = "SELECT s.id_sancion, s.id_prestamo, s.estado FROM sanciones s INNER JOIN prestamos p ON p.id_prestamo = s.id_prestamo INNER JOIN personas pr ON pr.id_persona = p.id_persona WHERE pr.num_documento = ? AND s.estado = ?";
    private static final String findSancion = "SELECT * FROM sanciones WHERE id_prestamo = ? AND estado = ?";
    private static final String updateSancion = "UPDATE sanciones SET estado = ? WHERE id_sancion = ?";
    
    /**
     * Metodo encargado de insertar una nueva sancion en la BD
     * @param sancion
     * @throws SQLException 
     */
    public void insertSancion(SancionDTO sancion) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(insertSancion);
            preparedStm.setInt(index++, sancion.getIdPrestamo());
            preparedStm.setString(index++, sancion.getEstado());
            preparedStm.executeUpdate();
        } finally {
            Conexion.closeConnection(preparedStm, conn);
        }
    }
    
    /**
     * Metodo encargado de validar si un usuario esta sancionado
     * @param numDocumento
     * @return
     * @throws SQLException 
     */
    public SancionDTO findSancionByUsuario(String numDocumento) throws SQLException {
        SancionDTO sancionDto = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findSancionByUsuario);
            preparedStm.setString(index++, numDocumento);
            preparedStm.setString(index++, "Activo");
            resultado = preparedStm.executeQuery();
            
            if(resultado.next()) {
                sancionDto = new SancionDTO();
            	sancionDto.setIdSancion(resultado.getInt("id_sancion"));
                sancionDto.setIdPrestamo(resultado.getInt("id_prestamo"));
                sancionDto.setEstado(resultado.getString("estado"));
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return sancionDto;
    }
    
    /**
     * Metodo encargado de consultar una sancion activa asociada a un prestamo
     * @param idPrestamo
     * @return
     * @throws SQLException 
     */
    public SancionDTO findSancionActiva(int idPrestamo) throws SQLException {
        SancionDTO sancionDto = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findSancion);
            preparedStm.setInt(index++, idPrestamo);
            preparedStm.setString(index++, "Activo");
            resultado = preparedStm.executeQuery();
            
            if(resultado.next()) {
                sancionDto = new SancionDTO();
            	sancionDto.setIdSancion(resultado.getInt("id_sancion"));
                sancionDto.setIdPrestamo(resultado.getInt("id_prestamo"));
                sancionDto.setEstado(resultado.getString("estado"));
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return sancionDto;
    }
    
    /**
     * Metodo encargado de actualizar el estado de una sancion en BD
     * @param sancion
     * @throws SQLException 
     */
    public void updateSancion(SancionDTO sancion) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(updateSancion);
            preparedStm.setString(index++, sancion.getEstado());
            preparedStm.setInt(index++, sancion.getIdSancion());
            preparedStm.executeUpdate();
        } finally {
            Conexion.closeConnection(preparedStm, conn);
        }
    }
}
