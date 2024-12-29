package br.com.alura.BibliotecAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuscaPorLivros {

    private int count;

    private String next;

    private String previous;

    @JsonProperty("results")
    private List<Livro> results;

    // Getters e setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("O valor de 'count' não pode ser negativo.");
        }
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Livro> getResults() {
        return results;
    }

    public void setResults(List<Livro> results) {
        if (results == null) {
            throw new IllegalArgumentException("A lista de resultados não pode ser nula.");
        }
        this.results = results;
    }

    @Override
    public String toString() {
        return "BuscaPorLivros{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
}