package br.com.alura.BibliotecAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título do livro não pode ser vazio.")
    private String title;

    @JsonProperty("download_count")
    @Min(value = 0, message = "A contagem de downloads não pode ser negativa.")
    private int downloads;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "livro_language", joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "language")
    private List<String> languages = new ArrayList<>(); // Evita null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "livro_autores",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>(); // Evita null

    public Livro() {}

    public Livro(String title, List<Autor> autores, int downloads, List<String> languages) {
        this.title = title;
        this.autores = (autores != null) ? autores : new ArrayList<>();
        this.downloads = downloads;
        this.languages = (languages != null) ? languages : new ArrayList<>();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = (languages != null) ? languages : new ArrayList<>();
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = (autores != null) ? autores : new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t---------- LIVRO -----------\n");
        sb.append("Título: ").append(title).append("\n");
        sb.append("Downloads: ").append(downloads).append("\n");
        sb.append("Idiomas: ").append((languages != null && !languages.isEmpty()) ? languages : "Nenhum idioma registrado").append("\n");
        sb.append("Autores:\n");
        if (autores != null && !autores.isEmpty()) {
            autores.forEach(autor -> {
                sb.append("\t").append(autor.getName())
                        .append(" (").append(autor.getBirthYear())
                        .append(" - ").append(autor.getDeathYear()).append(")\n");
            });
        } else {
            sb.append("\tDesculpa não localizamos um livro associado.\n");
        }
        sb.append("\t\t-------------------");
        return sb.toString();
    }
}