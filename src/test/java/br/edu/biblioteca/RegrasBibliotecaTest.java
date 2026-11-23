
package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class RegrasBibliotecaTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // Partições de equivalência válidas

    @Test
    void alunoDeveTerPrazoDe14Dias() {
        assertEquals(14, regras.prazoDias(TipoUsuario.ALUNO));
    }

    @Test
    void professorDeveTerPrazoDe30Dias() {
        assertEquals(30, regras.prazoDias(TipoUsuario.PROFESSOR));
    }

    @Test
    void visitanteDeveTerPrazoDe7Dias() {
        assertEquals(7, regras.prazoDias(TipoUsuario.VISITANTE));
    }

    // Partição de equivalência inválida

    @Test
    void tipoNuloDeveLancarExcecao() {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.prazoDias(null)
        );
    }

    // ========================================
    // TESTES: dataDevolucao
    // ========================================

    // Partições de equivalência válidas

    @Test
    void alunoDeveDevolverApos14Dias() {
        LocalDate retirada = LocalDate.of(2026, 11, 2);
        LocalDate esperado = LocalDate.of(2026, 11, 16);

        assertEquals(esperado,
            regras.dataDevolucao(retirada, TipoUsuario.ALUNO));
    }

    @Test
    void professorDeveDevolverApos30Dias() {
        LocalDate retirada = LocalDate.of(2026, 11, 2);
        LocalDate esperado = LocalDate.of(2026, 12, 2);

        assertEquals(esperado,
            regras.dataDevolucao(retirada, TipoUsuario.PROFESSOR));
    }

    @Test
    void visitanteDeveDevolverApos7Dias() {
        LocalDate retirada = LocalDate.of(2026, 11, 2);
        LocalDate esperado = LocalDate.of(2026, 11, 9);

        assertEquals(esperado,
            regras.dataDevolucao(retirada, TipoUsuario.VISITANTE));
    }

    // Análise de valores-limite: sábado, domingo e segunda

    @Test
    void devolucaoNoSabadoDeveSerMantida() {
        LocalDate retirada = LocalDate.of(2026, 10, 30);
        LocalDate esperado = LocalDate.of(2026, 11, 13);

        assertEquals(esperado,
            regras.dataDevolucao(retirada, TipoUsuario.ALUNO));
    }

    @Test
    void devolucaoNoDomingoDeveIrParaSegunda() {
        LocalDate retirada = LocalDate.of(2026, 10, 31);
        LocalDate esperado = LocalDate.of(2026, 11, 16);

        assertEquals(esperado,
            regras.dataDevolucao(retirada, TipoUsuario.ALUNO));
    }

    @Test
    void devolucaoNaSegundaDeveSerMantida() {
        LocalDate retirada = LocalDate.of(2026, 11, 1);
        LocalDate esperado = LocalDate.of(2026, 11, 16);

        assertEquals(esperado,
            regras.dataDevolucao(retirada, TipoUsuario.ALUNO));
    }

    // Partições de equivalência inválidas

    @Test
    void retiradaNulaDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.dataDevolucao(null, TipoUsuario.ALUNO));
    }

    @Test
    void tipoNuloEmDataDevolucaoDeveLancarExcecao() {
        LocalDate retirada = LocalDate.of(2026, 11, 2);

        assertThrows(IllegalArgumentException.class,
            () -> regras.dataDevolucao(retirada, null));
    }

    // ========================================
    // TESTES: limiteEmprestimos
    // ========================================

    // Partições de equivalência válidas

    @Test
    void alunoDeveTerLimiteDe3Emprestimos() {
        assertEquals(3,
            regras.limiteEmprestimos(TipoUsuario.ALUNO));
    }

    @Test
    void professorDeveTerLimiteDe8Emprestimos() {
        assertEquals(8,
            regras.limiteEmprestimos(TipoUsuario.PROFESSOR));
    }

    @Test
    void visitanteDeveTerLimiteDe1Emprestimo() {
        assertEquals(1,
            regras.limiteEmprestimos(TipoUsuario.VISITANTE));
    }

    // Partição de equivalência inválida

    @Test
    void tipoNuloEmLimiteEmprestimosDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.limiteEmprestimos(null));
    }


}
