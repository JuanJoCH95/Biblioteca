package com.co.biblioteca.model;

import com.co.biblioteca.db.Conexion;
import com.co.biblioteca.dto.LibroDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibrosDao {
    
    private static final String insertLibro = "INSERT INTO libros (nombre_libro, autor, id_genero, stock, disponibles) VALUES (?, ?, ?, ?, ?)";
    
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
}
