package br.com.alura.BibliotecAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do autor não pode ser vazio.")
    private String name;

    @Pattern(regexp = "\\d{4}", message = "Ano de nascimento deve ser um valor numérico de 4 dígitos.")
    @JsonProperty("birth_year")
    private String birthYear;

    @Pattern(regexp = "\\d{4}", message = "Ano de falecimento deve ser um valor numérico de 4 dígitos.")
    @JsonProperty("death_year")
    private String deathYear;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.LAZY)
    private List<Livro> livros;

    public Autor() {}

    public Autor(Long id, String name, String birthYear, String deathYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Autor(Long id, String name, String birthYear, String deathYear, List<Livro> livros) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.livros = livros;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(String deathYear) {
        this.deathYear = deathYear;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t---------- AUTOR -----------\n");
        sb.append("Nome: ").append(name).append("\n");
        sb.append("Ano de Nascimento: ").append(birthYear).append("\n");
        sb.append("Ano de Falecimento: ").append(deathYear).append("\n");
        sb.append("Livros:\n");
        if (livros != null && !livros.isEmpty()) {
            livros.forEach(livro -> sb.append("\t").append(livro.getTitle()).append("\n"));
        } else {
            sb.append("\tDesculpa não localizamos um Autor associado.\n");
        }
        sb.append("\t\t-------------------");
        return sb.toString();
    }
}