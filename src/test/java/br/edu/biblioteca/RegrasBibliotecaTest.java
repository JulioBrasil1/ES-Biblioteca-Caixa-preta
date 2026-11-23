
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

    // ========================================
    // TESTES: multaCentavos
    // ========================================

    // Sem atraso e início da cobrança

    @Test
    void multaAlunoSemAtrasoDeveSerZero() {
        assertEquals(0, regras.multaCentavos(TipoUsuario.ALUNO, 0));
    }

    @Test
    void multaAlunoComUmDiaDeAtraso() {
        assertEquals(100, regras.multaCentavos(TipoUsuario.ALUNO, 1));
    }

    // Fronteira entre primeira e segunda faixa de cobrança

    @Test
    void multaAlunoComNoveDiasDeAtraso() {
        assertEquals(900, regras.multaCentavos(TipoUsuario.ALUNO, 9));
    }

    @Test
    void multaAlunoComDezDiasDeAtraso() {
        assertEquals(1000, regras.multaCentavos(TipoUsuario.ALUNO, 10));
    }

    @Test
    void multaAlunoComOnzeDiasDeAtraso() {
        assertEquals(1200, regras.multaCentavos(TipoUsuario.ALUNO, 11));
    }

    @Test
    void multaAlunoComDozeDiasDeAtraso() {
        assertEquals(1400, regras.multaCentavos(TipoUsuario.ALUNO, 12));
    }

    // Fronteira do teto de 5000 centavos

    @Test
    void multaAlunoComVinteENoveDiasDeAtraso() {
        assertEquals(4800, regras.multaCentavos(TipoUsuario.ALUNO, 29));
    }

    @Test
    void multaAlunoComTrintaDiasDeAtraso() {
        assertEquals(5000, regras.multaCentavos(TipoUsuario.ALUNO, 30));
    }

    @Test
    void multaAlunoComTrintaEUmDiasDeAtraso() {
        assertEquals(5000, regras.multaCentavos(TipoUsuario.ALUNO, 31));
    }

    // Carência de três dias para professores

    @Test
    void multaProfessorComDoisDiasDeAtraso() {
        assertEquals(0, regras.multaCentavos(TipoUsuario.PROFESSOR, 2));
    }

    @Test
    void multaProfessorComTresDiasDeAtraso() {
        assertEquals(0, regras.multaCentavos(TipoUsuario.PROFESSOR, 3));
    }

    @Test
    void multaProfessorComQuatroDiasDeAtraso() {
        assertEquals(100, regras.multaCentavos(TipoUsuario.PROFESSOR, 4));
    }

    @Test
    void multaProfessorComTrezeDiasDeAtraso() {
        assertEquals(1000, regras.multaCentavos(TipoUsuario.PROFESSOR, 13));
    }

    @Test
    void multaProfessorComQuatorzeDiasDeAtraso() {
        assertEquals(1200, regras.multaCentavos(TipoUsuario.PROFESSOR, 14));
    }

    @Test
    void multaProfessorComTrintaETresDiasDeAtraso() {
        assertEquals(5000, regras.multaCentavos(TipoUsuario.PROFESSOR, 33));
    }

    // Visitantes não possuem carência

    @Test
    void multaVisitanteComUmDiaDeAtraso() {
        assertEquals(100, regras.multaCentavos(TipoUsuario.VISITANTE, 1));
    }

    @Test
    void multaVisitanteComOnzeDiasDeAtraso() {
        assertEquals(1200, regras.multaCentavos(TipoUsuario.VISITANTE, 11));
    }

    // Entradas inválidas

    @Test
    void multaComDiasNegativosDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.multaCentavos(TipoUsuario.ALUNO, -1));
    }

    @Test
    void multaComTipoNuloDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.multaCentavos(null, 1));
    }


}
