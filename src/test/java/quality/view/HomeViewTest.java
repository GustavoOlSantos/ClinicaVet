package quality.view;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import sistem.app.App;

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
    }

    @Test
    void buscaPorAnimal() {
        verifyThat("#nomePetField", isVisible());
        clickOn("#nomePetField").write("Apolo");
        clickOn(".submit-button");

        //=> Verifica se o animal foi encontrado
        verifyThat(".modal", isVisible());
        verifyThat(".infoModal", allOf(
                isVisible(),
                hasText("Dados do Animal")
        ));

        verifyThat("#nome", hasText("Apolo"));
        verifyThat("#nomeTutor", hasText("Milleny Araujo"));

        sleep(1000);
    }

    @Test
    void buscaPorTutor() {
        verifyThat("#nomeField", isVisible());
        clickOn("#nomeField").write("Milleny Araujo");
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
    }
}
