package quality.view;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import sistem.app.App;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class home extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage); // Inicia sua aplicação JavaFX real
    }

    @Test
    void deveAcessarPaginaHome() {
        verifyThat(".Welcome", hasText("Bem vindo!"));
    }
}
