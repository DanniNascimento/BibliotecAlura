package br.com.alura.BibliotecAlura.service;

import br.com.alura.BibliotecAlura.model.Livro;
import br.com.alura.BibliotecAlura.repository.AutorRepository;
import br.com.alura.BibliotecAlura.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final GutendexService gutendexService;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, GutendexService gutendexService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.gutendexService = gutendexService;
    }

    public List<Livro> getLivros() {
        return livroRepository.findAll();
    }

    public Optional<Livro> getLivroById(long id) {
        return livroRepository.findById(id);
    }

    @Transactional
    public Livro salvarLivro(Livro livro) {
        if (livro == null) {
            throw new IllegalArgumentException("O livro não pode ser vazio.");
        }
        return livroRepository.save(livro);
    }

    @Transactional
    public void excluirLivro(long id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro com ID " + id + " não encontrado.");
        }
        livroRepository.deleteById(id);
    }

    public List<Livro> getLivrosByLanguage(String language) {
        if (language == null || language.isBlank()) {
            throw new IllegalArgumentException("Idioma não pode ser nulo ou vazio.");
        }
        return getLivros().stream()
                .filter(livro -> livro.getLanguages() != null && livro.getLanguages().contains(language))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean tryFindAndRegisterLivro(String inputTitle) {
        if (inputTitle == null || inputTitle.isBlank()) {
            throw new IllegalArgumentException("O título do livro não pode ser nulo ou vazio.");
        }

        try {
            System.out.println("Iniciando a busca pelo livro: " + inputTitle);
            Optional<Livro> foundBook = gutendexService.buscarLivro(inputTitle);

            if (foundBook.isPresent()) {
                Livro livro = foundBook.get();

                // Salvar autores associados
                livro.getAutores().forEach(autor -> {
                    if (!autorRepository.existsById(autor.getId())) {
                        autorRepository.save(autor);
                    }
                });

                salvarLivro(livro);
                System.out.println("Livro registrado com sucesso: " + livro.getTitle());
                return true;
            } else {
                System.out.println("Nenhum livro encontrado para o título: " + inputTitle);
            }
        } catch (Exception e) {
            System.err.println("Erro ao registrar o livro: " + e.getMessage());
        }
        return false;
    }
}