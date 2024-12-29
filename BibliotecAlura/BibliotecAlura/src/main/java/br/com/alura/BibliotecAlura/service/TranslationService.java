package br.com.alura.BibliotecAlura.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class TranslationService {

    private final RestTemplate restTemplate;

    // Substitua por sua chave de API
    private static final String API_KEY = "AIzaSyBUVCeOKQHIyXbny_d6-wFP1jK2IBRubhA";

    public TranslationService() {
        this.restTemplate = new RestTemplate();
    }

    public String traduzirParaIngles(String textoOriginal) {
        if (textoOriginal == null || textoOriginal.trim().isEmpty()) {
            System.err.println("O texto para tradução está vazio ou nulo.");
            return textoOriginal; // Retorna o texto original como fallback
        }

        try {
            // URL da API do Google Cloud Translation
            String url = "https://translation.googleapis.com/language/translate/v2";
            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("q", textoOriginal)  // Texto original
                    .queryParam("target", "en")     // Língua de destino
                    .queryParam("key", API_KEY)     // Chave da API
                    .build()
                    .toUri();

            // Definir o tipo esperado para a resposta
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody.containsKey("data")) {
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                    if (data.containsKey("translations")) {
                        Map<String, Object> translation = ((List<Map<String, Object>>) data.get("translations")).get(0);
                        return translation.get("translatedText").toString();
                    }
                } else {
                    System.err.println("A resposta não contém o campo 'data'.");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao traduzir: " + e.getMessage());
        }

        // Retorna o texto original como fallback
        return textoOriginal;
    }
}