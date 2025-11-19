package quality.view;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import sistem.app.App;
import sistem.model.db.DB;

import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.LabeledMatchers.*;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.hamcrest.Matchers.*;

public class HomeViewTest extends ApplicationTest {

    /*@BeforeAll
    static void configurarHeadless() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("java.awt.headless", "true");
        System.setProperty("prism.order", "sw"); // Usa renderização por software
        System.setProperty("prism.text", "t2k"); // Evita crash de fonte (font.dll)
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
    }*/

    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
    }

    @BeforeAll
    public static void setup() {
        DB.enableTestMode();
    }

    @BeforeEach
    void waitForLoad() {
        sleep(500);
    }

    @Test
    void deveExibirElementosDaHomePage() {
        verifyThat(".Header", isVisible());
        verifyThat("#leftMenu", isVisible());
        verifyThat(".Welcome", hasText("Bem vindo!"));
        verifyThat("#tableView", isVisible());
        verifyThat("#ultimosPacientes", isVisible());
        verifyThat("#acoesRapidas", isVisible());
        verifyThat("#cxDiario", isVisible());
        verifyThat(".submit-button", hasText("Pesquisar"));

        System.out.println("\n\n================== Resultado do Teste ==================");
        System.out.println("Todos os elementos da homePage estão visíveis! ");
    }

    @Test
    void buscaPorAnimal() {
        verifyThat("#nomePetField", isVisible());
        clickOn("#nomePetField").write("Bob");
        clickOn(".submit-button");

        //=> Verifica se o animal foi encontrado
        verifyThat(".modal", isVisible());
        verifyThat(".infoModal", allOf(
                isVisible(),
                hasText("Dados do Animal")
        ));

        verifyThat("#nome", hasText("Bob"));
        verifyThat("#nomeTutor", hasText("João da Silva"));

        sleep(1000);

        System.out.println("\n\n================== Resultado do Teste ==================");
        System.out.println("Animal buscado com sucesso!: ");
        System.out.println("Animal buscado: Bob");
        System.out.println("Tutor do animal: João da Silva");
    }

    @Test
    void buscaPorTutor() {
        verifyThat("#nomeField", isVisible());
        clickOn("#nomeField").write("João da Silva");
        clickOn(".submit-button");

        //=> Verifica se a lista foi encontrado
        verifyThat(".Welcome", allOf(
                isVisible(),
                hasText("Resultado da Busca de Clientes")
        ));

        verifyThat("#tableView", isVisible());
        verifyThat("#NomePet", isVisible());
        verifyThat("#NomeTutor", isVisible());

        sleep(1000);

        System.out.println("\n\n================== Resultado do Teste ==================");
        System.out.println("Tutor e seus animais encontrados com sucesso!: ");
        System.out.println("Tutor encontrado: João da Silva");
    }
}
