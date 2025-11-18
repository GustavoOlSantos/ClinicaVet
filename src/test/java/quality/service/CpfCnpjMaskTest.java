package quality.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import sistem.service.CpfCnpjMask;

class CpfCnpjMaskTest {

    @Test
    void testMaskEmpty() {
        assertEquals("", CpfCnpjMask.Mask(""));
    }

    @Test
    void testMaskCpf() {
        String cpf = "12345678901";
        String esperado = "123.456.789-01"; // formato baseado nos regex e replace da classe

        assertEquals(esperado, CpfCnpjMask.Mask(cpf));
    }

    @Test
    void testMaskCnpj() {
        String cnpj = "12345678000199";
        String esperado = "12.345.678/0001-99";

        assertEquals(esperado, CpfCnpjMask.Mask(cnpj));
    }

    @Test
    void testMaskTamanhoInvalido() {
        // O comportamento depende do código que está truncado.
        // Se necessário, ajuste conforme o comportamento real.
        String invalido = "123";
        assertEquals("123", CpfCnpjMask.Mask(invalido));
    }

    @Test
    void testUnmaskCpf() {
        String formatado = "123.456.789-01";
        String esperado = "12345678901";

        assertEquals(esperado, CpfCnpjMask.Unmask(formatado));
    }

    @Test
    void testUnmaskCnpj() {
        String formatado = "12.345.678/0001-99";
        String esperado = "12345678000199";

        assertEquals(esperado, CpfCnpjMask.Unmask(formatado));
    }

    @Test
    void testUnmaskSemCaracteresEspeciais() {
        assertEquals("12345678901", CpfCnpjMask.Unmask("12345678901"));
    }

    @Test
    void testIsCpfTrue() {
        assertTrue(CpfCnpjMask.isCpf("12345678901"));
    }

    @Test
    void testIsCpfFalse() {
        assertFalse(CpfCnpjMask.isCpf("12345678"));
        assertFalse(CpfCnpjMask.isCpf("123456789012"));
    }

    @Test
    void testIsCnpjTrue() {
        assertTrue(CpfCnpjMask.isCnpj("12345678000199"));
    }

    @Test
    void testIsCnpjFalse() {
        assertFalse(CpfCnpjMask.isCnpj("12345"));
        assertFalse(CpfCnpjMask.isCnpj("1234567800019900"));
    }
}