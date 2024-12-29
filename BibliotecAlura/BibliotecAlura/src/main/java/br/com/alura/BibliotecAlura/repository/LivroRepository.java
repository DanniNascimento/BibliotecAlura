package br.com.alura.BibliotecAlura.repository;


import br.com.alura.BibliotecAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
