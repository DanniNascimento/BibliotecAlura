package br.com.alura.BibliotecAlura;


import br.com.alura.BibliotecAlura.Menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Run implements CommandLineRunner {

    private final Menu menu;


    @Autowired
    public Run(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void run(String... args) throws Exception {
        menu.showMenu();

    }
}
