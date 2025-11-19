package quality.erro;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;
import sistem.app.App;

import static org.testfx.api.FxAssert.*;
import static org.testfx.assertions.api.Assertions.assertThat;

public class erroCadpet extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
    }

    @Test
    public void testPrecisaCadastrarPetAntes() {
        clickOn("#cadastrar");
        clickOn("#cadastrarPet");
        clickOn("#nomeField").write("Gato");
        clickOn("#sexoField");
        type(javafx.scene.input.KeyCode.DOWN);
        type(javafx.scene.input.KeyCode.DOWN);
        type(javafx.scene.input.KeyCode.ENTER);
        clickOn("#especieField");
        type(javafx.scene.input.KeyCode.DOWN);
        type(javafx.scene.input.KeyCode.ENTER);
        clickOn("#statusField");
        type(javafx.scene.input.KeyCode.DOWN);
        type(javafx.scene.input.KeyCode.DOWN);
        type(javafx.scene.input.KeyCode.ENTER);
        clickOn("#emergenciaField");
        type(javafx.scene.input.KeyCode.DOWN);
        type(javafx.scene.input.KeyCode.ENTER);
        clickOn("#orcamentoPetField").write("100");
        clickOn("#btnSalvarPet");
        clickOn("#btnSalvar");

        // Aguarda até que o ALERTA apareça (máx 2 segundos)
        WaitForAsyncUtils.waitForFxEvents();

        // =====Verifica Retorno a HomePage
        verifyThat(".dialog-pane .header-panel .label",
                LabeledMatchers.hasText("Cliente Cadastrado com sucesso"));
    }
}