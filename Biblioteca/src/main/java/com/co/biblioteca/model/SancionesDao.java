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
    
    private static final String insertSancion = "INSERT INTO sanciones (id_persona, estado) VALUES (?, ?)";
    private static final String findSancion = "SELECT s.id_sancion, s.id_persona, s.estado FROM sanciones s INNER JOIN personas p ON s.id_persona = p.id_persona WHERE p.num_documento = ? AND s.estado = ?";
    
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
            preparedStm.setInt(index++, sancion.getIdPersona());
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
    public SancionDTO findSancion(String numDocumento) throws SQLException {
        SancionDTO sancionDto = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findSancion);
            preparedStm.setString(index++, numDocumento);
            preparedStm.setString(index++, "Activo");
            resultado = preparedStm.executeQuery();
            
            if(resultado.next()) {
                sancionDto = new SancionDTO();
            	sancionDto.setIdSancion(resultado.getInt("id_sancion"));
                sancionDto.setIdPersona(resultado.getInt("id_persona"));
                sancionDto.setEstado(resultado.getString("estado"));
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return sancionDto;
    }
}
