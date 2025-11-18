package quality.service;

import org.junit.jupiter.api.Test;
import sistem.model.exceptions.DomainException;

import static org.junit.jupiter.api.Assertions.*;

class TelefoneMaskTest {
    @Test
    void deveFormatarCelularSemDDD() throws DomainException {
        String raw = "988887777"; // 9 dígitos → cai no primeiro IF

        String masked = sistem.service.TelefoneMask.Mask(raw);

        assertEquals("98888-7777", masked,
                "Falha ao formatar celular sem DDD (9 dígitos)");
    }

    @Test
    void deveFormatarFixoSemDDD() throws DomainException {
        String raw = "33334444"; // 8 dígitos → segundo ELSE IF

        String masked = sistem.service.TelefoneMask.Mask(raw);

        assertEquals("3333-4444", masked,
                "Falha ao formatar telefone fixo sem DDD (8 dígitos)");
    }

    @Test
    void deveFormatarCelularComDDD() throws DomainException {
        String raw = "21988887777"; // 11 dígitos → terceiro ELSE IF

        String masked = sistem.service.TelefoneMask.Mask(raw);

        assertEquals("(21) 98888-7777", masked,
                "Falha ao formatar celular com DDD (11 dígitos)");
    }

    @Test
    void deveLancarExcecaoQuandoTamanhoInvalido() {
        String raw = "12345"; // 5 dígitos → cai no ELSE → DomainException

        assertThrows(DomainException.class, () -> {
            sistem.service.TelefoneMask.Mask(raw);
        }, "Era esperado DomainException para telefone inválido.");
    }

    @Test
    void deveRemoverMascaraComUnmask() {
        String masked = "(21) 98888-7777";

        String unmasked = sistem.service.TelefoneMask.Unmask(masked);

        // Esperado: apenas números
        assertEquals("21988887777", unmasked,
                "Falha ao remover máscara corretamente com Unmask.");
    }

    @Test
    void deveManterConsistenciaMascaraUnmaskMask() throws DomainException {
        String original = "21988887777";

        String masked = sistem.service.TelefoneMask.Mask(original);
        String unmasked = sistem.service.TelefoneMask.Unmask(masked);
        String maskedAgain = sistem.service.TelefoneMask.Mask(unmasked);

        assertEquals(masked, maskedAgain,
                "Fluxo mask → unmask → mask deve ser estável.");
    }

    @Test
    void deveCobrirRegexesDiretamente() throws DomainException {
        // 8 dígitos
        assertEquals("1234-5678", sistem.service.TelefoneMask.Mask("12345678"));

        // 9 dígitos
        assertEquals("12345-6789", sistem.service.TelefoneMask.Mask("123456789"));

        // 11 dígitos
        assertEquals("(12) 34567-8901", sistem.service.TelefoneMask.Mask("12345678901"));
    }
}
