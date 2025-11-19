package quality.erro;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import sistem.app.App;

public class erroCpf extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
    }

    @Test
    public void testPrecisaCadastrarPetAntes() {
        clickOn("#cadastrar");
        clickOn("#nomeField").write("testecpf");
        clickOn("#cpfField").write("AAAbbbCCC11");
        clickOn("#emailField").write("testecpf@gmail.com");
        clickOn("#telefoneField").write("999999999");
        clickOn("#formaPgField");   // Abre o ComboBox
        type(javafx.scene.input.KeyCode.DOWN);
        type(javafx.scene.input.KeyCode.DOWN); // Seleciona o segundo (Crédito)


        clickOn("#btnSalvar");

        // Aguarda até que o ALERTA apareça (máx 2 segundos)
        WaitForAsyncUtils.waitForFxEvents();

        // =====Verifica Retorno a HomePage
        verifyThat(".Welcome", hasText("Bem vindo!"));

    }
}