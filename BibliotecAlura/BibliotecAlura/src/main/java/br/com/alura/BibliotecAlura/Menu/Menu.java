package br.com.alura.BibliotecAlura.Menu;

import br.com.alura.BibliotecAlura.model.Autor;
import br.com.alura.BibliotecAlura.service.AutorService;
import br.com.alura.BibliotecAlura.service.LivroService;
import br.com.alura.BibliotecAlura.model.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class Menu {

    private static final String MENU_TITLE = " ***** B I B L I O T E C A L U R A ***** ";
    private static final String SEARCH_BOOK_TITLE = "***** HORA DA PESQUISA ***** ";
    private static final String EXIT_MESSAGE = "HORA DE DAR TCHAU...";

    private static final List<String> VALID_LANGUAGES = List.of("en", "es", "fr", "pt");

    private final AutorService autorService;
    private final LivroService livroService;

    @Autowired
    public Menu(LivroService livroService, AutorService autorService) {
        this.livroService = livroService;
        this.autorService = autorService;
    }

    public void showMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu();
                int option = readOption(scanner);

                switch (option) {
                    case 0:
                        System.out.println(EXIT_MESSAGE);
                        System.exit(0);
                        break;
                    case 1:
                        searchBook(scanner);
                        break;
                    case 2:
                        listLivros();
                        break;
                    case 3:
                        listAutores();
                        break;
                    case 4:
                        listAliveAutores(scanner);
                        break;
                    case 5:
                        listLivrosByLanguage(scanner);
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                }
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    private void printMenu() {
        System.out.println("-------------------");
        System.out.println(MENU_TITLE);
        System.out.println("1 - Pesquisar livro");
        System.out.println("2 - Listar livros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Listar autores vivos em algum ano");
        System.out.println("5 - Listar livros por idioma");
        System.out.println("0 - Sair");


    }

    private int readOption(Scanner scanner) {
        System.out.print("Digite o número da opção desejada: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, insira um número.");
            scanner.next(); // Limpa a entrada inválida
        }
        int option = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        return option;
    }

    private void searchBook(Scanner scanner) {
        System.out.println("-------------------");
        System.out.println(SEARCH_BOOK_TITLE);
        System.out.print("Digite o título do livro ou \"sair\" para voltar: ");
        String inputTitle = scanner.nextLine().trim();

        if ("sair".equalsIgnoreCase(inputTitle)) {
            return;
        }

        try {
            boolean found = livroService.tryFindAndRegisterLivro(inputTitle);
            if (found) {
                System.out.println("Livro encontrado e registrado com sucesso!");
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar livro: " + e.getMessage());
        }
    }

    private void listLivros() {
        List<Livro> livros = livroService.getLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado. Comece a pesquisar!");
        } else {
            livros.forEach(livro -> System.out.println(livro.getTitle()));
        }
    }

    private void listAutores() {
        List<Autor> autores = autorService.getAutores();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado. Comece a pesquisar!");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listAliveAutores(Scanner scanner) {
        System.out.print("Digite um ano para listar os autores vivos nesse período: ");
        int year = readYear(scanner);
        autorService.getAutoresVivos(year).forEach(System.out::println);
    }

    private int readYear(Scanner scanner) {
        while (true) {
            System.out.print("Ano: ");
            if (scanner.hasNextInt()) {
                int year = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer
                return year;
            } else {
                System.out.println("Por favor, insira um ano válido.");
                scanner.nextLine(); // Limpa a entrada inválida
            }
        }
    }

    private void listLivrosByLanguage(Scanner scanner) {
        while (true) {
            System.out.println("\tDigite um dos idiomas abaixo ou \"sair\" para voltar:");
            VALID_LANGUAGES.forEach(language -> System.out.println("\t" + language));

            String languageInput = scanner.nextLine().trim();
            if ("sair".equalsIgnoreCase(languageInput)) {
                return;
            }
            if (!VALID_LANGUAGES.contains(languageInput)) {
                System.out.println("Por favor, digite um idioma válido.");
                continue;
            }

            List<Livro> livros = livroService.getLivrosByLanguage(languageInput);
            if (livros.isEmpty()) {
                System.out.println("Nenhum livro encontrado nesse idioma.");
            } else {
                livros.forEach(livro -> System.out.println(livro.getTitle()));
            }
            break;
        }
    }
}