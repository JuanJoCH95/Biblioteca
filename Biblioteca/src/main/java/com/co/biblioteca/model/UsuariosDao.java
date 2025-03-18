package com.co.biblioteca.model;

import com.co.biblioteca.db.Conexion;
import com.co.biblioteca.dto.UsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene todos los metodos para realizar las transacciones de usuarios en la BD del sistema
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class UsuariosDao {
    
    private static final String insertUsuario = "INSERT INTO personas (id_documento, num_documento, nombre_persona, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String validateUsuario = "SELECT td.nomenclatura, p.num_documento, p.nombre_persona, p.apellido, p.telefono, p.email, p.direccion FROM personas p INNER JOIN tipo_documento td ON p.id_documento = td.id_documento WHERE p.num_documento = ?";
    private static final String findAll = "SELECT td.nomenclatura, p.num_documento, p.nombre_persona, p.apellido, p.telefono, p.email, p.direccion FROM personas p INNER JOIN tipo_documento td ON p.id_documento = td.id_documento";
    private static final String deleteUsuario = "DELETE FROM personas WHERE num_documento = ?";
    private static final String findUsuario = "SELECT id_persona, id_documento, num_documento, nombre_persona, apellido, telefono, email, direccion FROM personas WHERE num_documento = ?";
    private static final String updateUsuario = "UPDATE personas SET id_documento = ?, num_documento = ?, nombre_persona = ?, apellido = ?, telefono = ?, email = ?, direccion = ? WHERE id_persona = ?";
    
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
     * Metodo encargado de consultar todos los usuarios de la BD
     * @return
     * @throws SQLException 
     */
    public List<UsuarioDTO> findAll() throws SQLException {
        List<UsuarioDTO> listUsuarios = new ArrayList<>();
        UsuarioDTO usuarioDto = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findAll);
            resultado = preparedStm.executeQuery();
            
            while(resultado.next()) {
                usuarioDto = new UsuarioDTO();
                usuarioDto.setTipoDocumento(resultado.getString("nomenclatura"));
                usuarioDto.setNumDocumento(resultado.getString("num_documento"));
                usuarioDto.setNombre(resultado.getString("nombre_persona"));
                usuarioDto.setApellido(resultado.getString("apellido"));
                usuarioDto.setTelefono(resultado.getString("telefono"));
                usuarioDto.setEmail(resultado.getString("email"));
                usuarioDto.setDireccion(resultado.getString("direccion"));
                listUsuarios.add(usuarioDto);
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return listUsuarios;
    }
    
    /**
     * Metodo encargado de eliminar un usuario de la BD
     * @param numDocumento
     * @throws SQLException 
     */
    public void deleteUsuario(String numDocumento) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(deleteUsuario);
            preparedStm.setString(index++, numDocumento);
            preparedStm.executeUpdate();
        } finally {
            Conexion.closeConnection(preparedStm, conn);
        }
    }
    
    /**
     * Metodo encargado de actualizar un usuario en la BD
     * @param usuario
     * @throws SQLException 
     */
    public void updateUsuario(UsuarioDTO usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(updateUsuario);
            preparedStm.setInt(index++, usuario.getIdTipoDocumento());
            preparedStm.setString(index++, usuario.getNumDocumento());
            preparedStm.setString(index++, usuario.getNombre());
            preparedStm.setString(index++, usuario.getApellido());
            preparedStm.setString(index++, usuario.getTelefono());
            preparedStm.setString(index++, usuario.getEmail());
            preparedStm.setString(index++, usuario.getDireccion());
            preparedStm.setInt(index++, usuario.getIdUsuario());
            preparedStm.executeUpdate();
        } finally {
            Conexion.closeConnection(preparedStm, conn);
        }
    }
    
    /**
     * Metodo encargado de consultar un usuario en la BD
     * @return
     * @throws SQLException 
     */
    public UsuarioDTO findUsuario(String numDocumento) throws SQLException {
        UsuarioDTO usuarioDto = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findUsuario);
            preparedStm.setString(index++, numDocumento);
            resultado = preparedStm.executeQuery();
            
            if(resultado.next()) {
                usuarioDto = new UsuarioDTO();
                usuarioDto.setIdUsuario(resultado.getInt("id_persona"));
                usuarioDto.setIdTipoDocumento(resultado.getInt("id_documento"));
                usuarioDto.setNumDocumento(resultado.getString("num_documento"));
                usuarioDto.setNombre(resultado.getString("nombre_persona"));
                usuarioDto.setApellido(resultado.getString("apellido"));
                usuarioDto.setTelefono(resultado.getString("telefono"));
                usuarioDto.setEmail(resultado.getString("email"));
                usuarioDto.setDireccion(resultado.getString("direccion"));
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return usuarioDto;
    }
    
    /**
     * Metodo encargado de consultar una lista especifica de usuarios en la BD
     * @param numDocumento
     * @return
     * @throws SQLException 
     */
    public List<UsuarioDTO> findSpecific(String numDocumento) throws SQLException {
        List<UsuarioDTO> listUsuarios = new ArrayList<>();
        UsuarioDTO usuarioDto = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(validateUsuario);
            preparedStm.setString(index++, numDocumento);
            resultado = preparedStm.executeQuery();
            
            while(resultado.next()) {
                usuarioDto = new UsuarioDTO();
                usuarioDto.setTipoDocumento(resultado.getString("nomenclatura"));
                usuarioDto.setNumDocumento(resultado.getString("num_documento"));
                usuarioDto.setNombre(resultado.getString("nombre_persona"));
                usuarioDto.setApellido(resultado.getString("apellido"));
                usuarioDto.setTelefono(resultado.getString("telefono"));
                usuarioDto.setEmail(resultado.getString("email"));
                usuarioDto.setDireccion(resultado.getString("direccion"));
                listUsuarios.add(usuarioDto);
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return listUsuarios;
    }
    
    /**
     * Metodo encargado de validar si un usuario ya existe en la BD
     * @param numDocumento
     * @return
     * @throws SQLException 
     */
    public boolean validateUsuario(String numDocumento) throws SQLException {
        boolean usuarioExiste = false;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(validateUsuario);
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
