package br.com.alura.BibliotecAlura.service;

import br.com.alura.BibliotecAlura.model.BuscaPorLivros;
import br.com.alura.BibliotecAlura.model.Livro;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class GutendexService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final TranslationService translationService;
    private static final String GUTENDEX_URL = "https://gutendex.com/books";

    public GutendexService(TranslationService translationService) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.translationService = translationService;
    }

    public Optional<Livro> buscarLivro(String inputTitle) {
        if (inputTitle == null || inputTitle.isBlank()) {
            throw new IllegalArgumentException("O título do livro não pode ser nulo ou vazio.");
        }

        try {
            // Traduzir o título para inglês
            String tituloTraduzido = translationService.traduzirParaIngles(inputTitle);

            // Construir a URL com o título traduzido
            URI uri = UriComponentsBuilder.fromHttpUrl(GUTENDEX_URL)
                    .queryParam("search", tituloTraduzido.trim())
                    .build()
                    .toUri();

            // Chamada à API Gutendex
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                BuscaPorLivros buscaPorLivros = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
                List<Livro> livros = buscaPorLivros.getResults();

                // Filtrar pelo título exato (traduzido ou original)
                return livros.stream()
                        .filter(livro -> livro.getTitle().equalsIgnoreCase(tituloTraduzido)
                                || livro.getTitle().equalsIgnoreCase(inputTitle))
                        .findFirst();
            } else {
                System.out.println("Resposta da API não foi bem-sucedida. Código: " + response.getStatusCode());
                return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar livro: " + e.getMessage());
            return Optional.empty();
        }
    }
}