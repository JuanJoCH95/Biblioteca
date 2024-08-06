package com.co.biblioteca.model;

import com.co.biblioteca.db.Conexion;
import com.co.biblioteca.dto.UsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que contiene todos los metodos para realizar las transacciones de usuarios en la BD del sistema
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class UsuariosDao {
    
    private static final String insertUsuario = "INSERT INTO personas (id_documento, num_documento, nombre_persona, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String validateUsuario = "SELECT id_documento, num_documento FROM personas WHERE id_documento = ? AND num_documento = ?";
    
    /**
     * Metodo encargado de insertar un nuevo usuario en la BD
     * @param usuario
     * @throws SQLException 
     */
    public void insertUsuario(UsuarioDTO usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(insertUsuario);
            preparedStm.setInt(index++, usuario.getIdTipoDocumento());
            preparedStm.setString(index++, usuario.getNumDocumento());
            preparedStm.setString(index++, usuario.getNombre());
            preparedStm.setString(index++, usuario.getApellido());
            preparedStm.setString(index++, usuario.getTelefono());
            preparedStm.setString(index++, usuario.getEmail());
            preparedStm.setString(index++, usuario.getDireccion());
            preparedStm.executeUpdate();
        } finally {
            Conexion.closeConnection(preparedStm, conn);
        }
    }
    
    /**
     * Metodo encargado de validar si un usuario ya existe en la BD
     * @param idDocumento
     * @param numDocumento
     * @return
     * @throws SQLException 
     */
    public boolean validateUsuario(int idDocumento, String numDocumento) throws SQLException {
        boolean usuarioExiste = false;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(validateUsuario);
            preparedStm.setInt(index++, idDocumento);
            preparedStm.setString(index++, numDocumento);
            resultado = preparedStm.executeQuery();
            
            if(resultado.next()) {
            	usuarioExiste = true;
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return usuarioExiste;
    }
}
