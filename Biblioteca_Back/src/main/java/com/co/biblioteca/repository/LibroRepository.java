package com.co.biblioteca.repository;

import com.co.biblioteca.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libros, Integer> {

}
