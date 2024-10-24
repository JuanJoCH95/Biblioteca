package com.co.biblioteca.model;

import com.co.biblioteca.db.Conexion;
import com.co.biblioteca.dto.LibroDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene todos los metodos para realizar las transacciones de libros en la BD del sistema
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class LibrosDao {
    
    private static final String insertLibro = "INSERT INTO libros (nombre_libro, autor, id_genero, stock, disponibles) VALUES (?, ?, ?, ?, ?)";
    private static final String validateLibro = "SELECT lb.id_libro, lb.nombre_libro, lb.autor, tg.nombre_genero, lb.stock, lb.disponibles FROM libros lb INNER JOIN tipo_genero tg ON lb.id_genero = tg.id_genero WHERE lb.nombre_libro like (?)";
    private static final String findAll = "SELECT lb.id_libro, lb.nombre_libro, lb.autor, tg.nombre_genero, lb.stock, lb.disponibles FROM libros lb INNER JOIN tipo_genero tg ON lb.id_genero = tg.id_genero";
    private static final String deleteLibro = "DELETE FROM libros WHERE id_libro = ?";
    private static final String findLibro = "SELECT id_libro, nombre_libro, autor, id_genero, stock, disponibles FROM libros WHERE id_libro = ?";
    private static final String updateLibro = "UPDATE libros SET nombre_libro = ?, autor = ?, id_genero = ?, stock = ?, disponibles = ? WHERE id_libro = ?";
    private static final String findDisponibles = "SELECT disponibles FROM libros WHERE id_libro = ?";
    
    /**
     * Metodo encargado de insertar un nuevo libro en la BD
     * @param libro
     * @throws SQLException 
     */
    public void insertLibro(LibroDTO libro) throws SQLException {
	Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(insertLibro);
            preparedStm.setString(index++, libro.getNombreLibro());
            preparedStm.setString(index++, libro.getAutor());
            preparedStm.setInt(index++, libro.getIdGenero());
            preparedStm.setInt(index++, libro.getStock());
            preparedStm.setInt(index++, libro.getDisponibles());
            preparedStm.executeUpdate();
	} finally {
            Conexion.closeConnection(preparedStm, conn);
	}
    }
    
    /**
     * Metodo encargado de consultar todos los libros de la BD
     * @return
     * @throws SQLException 
     */
    public List<LibroDTO> findAll() throws SQLException {
        List<LibroDTO> listLibros = new ArrayList<>();
        LibroDTO libroDto = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findAll);
            resultado = preparedStm.executeQuery();
            
            while(resultado.next()) {
                libroDto = new LibroDTO();
                libroDto.setIdLibro(resultado.getInt("id_libro"));
                libroDto.setNombreLibro(resultado.getString("nombre_libro"));
                libroDto.setAutor(resultado.getString("autor"));
                libroDto.setGenero(resultado.getString("nombre_genero"));
                libroDto.setStock(resultado.getInt("stock"));
                libroDto.setDisponibles(resultado.getInt("disponibles"));
                listLibros.add(libroDto);
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return listLibros;
    }
    
    /**
     * Metodo encargado de eliminar un libro de la BD
     * @param idLibro
     * @throws SQLException 
     */
    public void deleteLibro(int idLibro) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(deleteLibro);
            preparedStm.setInt(index++, idLibro);
            preparedStm.executeUpdate();
        } finally {
            Conexion.closeConnection(preparedStm, conn);
        }
    }
    
    /**
     * Metodo encargado de actualizar un libro en la BD
     * @param libro
     * @throws SQLException 
     */
    public void updateLibro(LibroDTO libro) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(updateLibro);
            preparedStm.setString(index++, libro.getNombreLibro());
            preparedStm.setString(index++, libro.getAutor());
            preparedStm.setInt(index++, libro.getIdGenero());
            preparedStm.setInt(index++, libro.getStock());
            preparedStm.setInt(index++, libro.getDisponibles());
            preparedStm.setInt(index++, libro.getIdLibro());
            preparedStm.executeUpdate();
	} finally {
            Conexion.closeConnection(preparedStm, conn);
	}
    }
    
    /**
     * Metodo encargado de consultar un libro en la BD
     * @param idLibro
     * @return
     * @throws SQLException 
     */
    public LibroDTO findLibro(int idLibro) throws SQLException {
        LibroDTO libro = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findLibro);
            preparedStm.setInt(index++, idLibro);
            resultado = preparedStm.executeQuery();
            
            if(resultado.next()) {
                libro = new LibroDTO();
                libro.setIdLibro(resultado.getInt("id_libro"));
                libro.setNombreLibro(resultado.getString("nombre_libro"));
                libro.setAutor(resultado.getString("autor"));
                libro.setIdGenero(resultado.getInt("id_genero"));
                libro.setStock(resultado.getInt("stock"));
                libro.setDisponibles(resultado.getInt("disponibles"));
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return libro;
    }
    
    /**
     * Metodo encargado de consultar una lista especifica de libros en la BD
     * @param nombre
     * @return
     * @throws SQLException 
     */
    public List<LibroDTO> findSpecific(String nombre) throws SQLException {
        List<LibroDTO> listLibros = new ArrayList<>();
        LibroDTO libroDto = null;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(validateLibro);
            preparedStm.setString(index++, "%" + nombre + "%");
            resultado = preparedStm.executeQuery();
            
            while(resultado.next()) {
                libroDto = new LibroDTO();
                libroDto.setIdLibro(resultado.getInt("id_libro"));
                libroDto.setNombreLibro(resultado.getString("nombre_libro"));
                libroDto.setAutor(resultado.getString("autor"));
                libroDto.setGenero(resultado.getString("nombre_genero"));
                libroDto.setStock(resultado.getInt("stock"));
                libroDto.setDisponibles(resultado.getInt("disponibles"));
                listLibros.add(libroDto);
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return listLibros;
    }
    
    /**
     * Metodo encargado de consultar la cantidad de disponibles por libro
     * @param idLibro
     * @return
     * @throws SQLException 
     */
    public int findDisponibles(int idLibro) throws SQLException {
        int disponibles = 0;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(findDisponibles);
            preparedStm.setInt(index++, idLibro);
            resultado = preparedStm.executeQuery();
            
            if(resultado.next()) {
                disponibles = resultado.getInt("disponibles");
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return disponibles;
    }
    
    /**
     * Metodo encargado de validar si un libro ya existe en la BD
     * @param nombre
     * @return
     * @throws SQLException 
     */
    public boolean validateLibro(String nombre) throws SQLException {
        boolean libroExiste = false;
        Connection conn = null;
        ResultSet resultado = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(validateLibro);
            preparedStm.setString(index++, "%" + nombre + "%");
            resultado = preparedStm.executeQuery();
            
            if(resultado.next()) {
            	libroExiste = true;
            }
        } finally {
            Conexion.closeConnection(resultado, preparedStm, conn);
        }
        return libroExiste;
    }
}
