package br.com.alura.BibliotecAlura.service;

import br.com.alura.BibliotecAlura.model.Autor;
import br.com.alura.BibliotecAlura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> getAutores() {
        return autorRepository.findAll();
    }

    public Optional<Autor> getAutorById(long id) {
        return autorRepository.findById(id);
    }

    public List<Autor> getAutoresVivos(int ano) {
        List<Autor> listaDeAutores = getAutores();

        // Filtra autores vivos em um determinado ano
        return listaDeAutores.stream()
                .filter(autor -> isAutorVivoNoAno(autor, ano))
                .collect(Collectors.toList());
    }

    @Transactional
    public Autor salvarAutor(Autor autor) {
        validarDadosAutor(autor);
        return autorRepository.save(autor);
    }

    @Transactional
    public void excluirAutor(long id) {
        if (!autorRepository.existsById(id)) {
            throw new IllegalArgumentException("Autor com ID " + id + " não encontrado.");
        }
        autorRepository.deleteById(id);
    }

    // Método auxiliar para verificar se um autor estava vivo em um ano
    private boolean isAutorVivoNoAno(Autor autor, int ano) {
        try {
            int birthYear = Integer.parseInt(autor.getBirthYear());
            int deathYear = autor.getDeathYear() != null && !autor.getDeathYear().isEmpty()
                    ? Integer.parseInt(autor.getDeathYear())
                    : Integer.MAX_VALUE; // Considera autores vivos sem ano de morte registrado
            return birthYear <= ano && deathYear >= ano;
        } catch (NumberFormatException e) {
            return false; // Ignora autores com dados inválidos
        }
    }

    // Valida os dados do autor antes de salvar
    private void validarDadosAutor(Autor autor) {
        if (autor.getName() == null || autor.getName().isBlank()) {
            throw new IllegalArgumentException("O nome do autor não pode ser vazio.");
        }
        try {
            Integer.parseInt(autor.getBirthYear());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ano de nascimento inválido: " + autor.getBirthYear());
        }
        if (autor.getDeathYear() != null && !autor.getDeathYear().isEmpty()) {
            try {
                Integer.parseInt(autor.getDeathYear());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Ano de falecimento inválido: " + autor.getDeathYear());
            }
        }
    }
}