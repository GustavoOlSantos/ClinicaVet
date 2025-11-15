package quality.view;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import sistem.app.App;
import sistem.model.db.DB;

import java.sql.Connection;

import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.LabeledMatchers.*;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.testfx.assertions.api.Assertions.assertThat;


public class CadastroViewTest extends ApplicationTest {

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
        clickOn("#cadastrar");

        sleep(500);
        verifyThat(".Header", isVisible());
        verifyThat("#leftMenu", isVisible());
    }

    @Test
    void deveExibirElementosDaPaginaDeCadastro() {
        verifyThat("#nomeField", isVisible());
        verifyThat("#cpfField", isVisible());
        verifyThat("#emailField", isVisible());
        verifyThat("#telefoneField", isVisible());
        verifyThat("#formaPgField", isVisible());
        verifyThat("#parcelasField", isVisible());
        verifyThat("#orcamentoField", isVisible());
        verifyThat("#observacoesClienteField", isVisible());

        verifyThat("#tableView", isVisible());
        verifyThat("#cadastrarPet", isVisible());
        verifyThat("#btnSalvar", isVisible());
    }

    @Test
    void cadastrarCliente(){
        // ===== 1. Digitar dados do cliente =====
        clickOn("#nomeField").write("João da Silva");
        clickOn("#cpfField").write("12345678901");
        clickOn("#emailField").write("joao@gmail.com");
        clickOn("#telefoneField").write("2199999-8888");

        // ===== 2. Forma de pagamento =====
        clickOn("#formaPgField");   // Abre o ComboBox
        type(javafx.scene.input.KeyCode.DOWN);
        type(javafx.scene.input.KeyCode.DOWN); // Seleciona o segundo (Crédito)
        type(javafx.scene.input.KeyCode.ENTER);

        // ===== 3. Parcelas =====
        clickOn("#parcelasField").eraseText(10).write("3");

        // ===== 6. Adicionar um Pet =====
         clickOn("#cadastrarPet");
         sleep(500);
         cadastrarUmPet("Rex", "Animal agressivo.");
         sleep(1000);

         // ==== 7. Verifica se tabela agora possui alguma linha cadastrada
        TableView<?> tabela = lookup("#tableView").queryTableView();
        assertThat(tabela.getItems().size()).isGreaterThan(0);


        // ===== 8. Clicar no botão salvar =====
        clickOn("#btnSalvar");

        // ===== 9. Feedback
        verifyThat(".dialog-pane", NodeMatchers.isVisible());
        verifyThat(".dialog-pane .header-panel .label",
                LabeledMatchers.hasText("Cliente Cadastrado com sucesso"));
        verifyThat("OK", NodeMatchers.isVisible());
        clickOn("OK");

        // =====Verifica Retorno a HomePage
        verifyThat(".Welcome", hasText("Bem vindo!"));
    }

    private void cadastrarUmPet(String nome, String Obs){
        clickOn("#nomeField").write(nome);

        // Sexo
        clickOn("#sexoField");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // Espécie
        clickOn("#especieField");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // Status do Paciente
        clickOn("#statusField");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // Emergência
        clickOn("#emergenciaField");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // Observações
        clickOn("#observacoesField").write(Obs);

        // Serviços
        clickOn("#servicosBox");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        sleep(1500);
        // Verifica se o preço carregou
        String txt = lookup("#precoServField").queryAs(TextField.class).getText();
        assertThat(txt).isNotEmpty();

        // Clique em adicionar serviço
        clickOn("#btnAdd");

        // 3) VALIDA: A linha existe na tabela
        sleep(500);
//        TableView<?> tabela = lookup("#tableView").queryTableView();
//        assertThat(tabela.getItems().size()).isGreaterThan(0);

        //=> Verifica se o Orçamento Total foi calculado
        assertThat(lookup("#orcamentoField").queryAs(TextField.class))
                .hasText("50.0");

        // Clica em salvar
        clickOn("#btnSalvar");
    }
}
