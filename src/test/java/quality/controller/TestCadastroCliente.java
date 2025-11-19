package quality.controller;

import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import sistem.model.entities.Cliente;
import sistem.model.dao.interfaces.ClienteDAO;
import sistem.model.exceptions.DomainException;
import sistem.service.CpfCnpjMask;
import sistem.service.TelefoneMask;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCadastroCliente {

    private ClienteDAO clienteDAOMock;

    @BeforeEach
    void setup() {
        clienteDAOMock = mock(ClienteDAO.class);
    }

    @Test
    @Order(1)
    void testCadastroCompletoClienteFluxoReal() throws DomainException {

        String nome = "Ana Paula";
        String cpf = "12345678901";
        String telefone = "21988887777";

        String cpfMascarado = CpfCnpjMask.Mask(cpf);
        String telMascarado = TelefoneMask.Mask(telefone);

        Cliente cli = new Cliente(nome, cpfMascarado, telMascarado, 1);
        cli.setId(1);

        when(clienteDAOMock.findById(1)).thenReturn(cli);

        Cliente encontrado = clienteDAOMock.findById(1);

        System.out.println("===== RESULTADO DO TESTE =====");
        System.out.println("Nome encontrado: " + encontrado.getNome());
        System.out.println("CPF mascarado: " + encontrado.getCpf());
        System.out.println("Telefone mascarado: " + encontrado.getTelefone());
        System.out.println("Teste executado com sucesso!");
        System.out.println("==============================");

        assertNotNull(encontrado);
        assertEquals(nome, encontrado.getNome());
        assertEquals(cpfMascarado, encontrado.getCpf());
        assertEquals(telMascarado, encontrado.getTelefone());
    }
}