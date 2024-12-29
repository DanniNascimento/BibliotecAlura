package br.com.alura.BibliotecAlura.repository;

import br.com.alura.BibliotecAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
