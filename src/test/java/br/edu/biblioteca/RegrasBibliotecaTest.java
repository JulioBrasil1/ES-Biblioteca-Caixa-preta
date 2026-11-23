
package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    // ========================================
    // TESTES: podeEmprestar
    // ========================================

    // Limites de emprestimos - ALUNO

    @Test
    void alunoSemEmprestimosPodeEmprestar() {
        assertTrue(regras.podeEmprestar(
            TipoUsuario.ALUNO, 0, 0, false));
    }

    @Test
    void alunoComDoisEmprestimosPodeEmprestar() {
        assertTrue(regras.podeEmprestar(
            TipoUsuario.ALUNO, 2, 0, false));
    }

    @Test
    void alunoComTresEmprestimosNaoPodeEmprestar() {
        assertFalse(regras.podeEmprestar(
            TipoUsuario.ALUNO, 3, 0, false));
    }

    @Test
    void alunoComQuatroEmprestimosNaoPodeEmprestar() {
        assertFalse(regras.podeEmprestar(
            TipoUsuario.ALUNO, 4, 0, false));
    }

    // Limites de emprestimos - PROFESSOR

    @Test
    void professorComSeteEmprestimosPodeEmprestar() {
        assertTrue(regras.podeEmprestar(
            TipoUsuario.PROFESSOR, 7, 0, false));
    }

    @Test
    void professorComOitoEmprestimosNaoPodeEmprestar() {
        assertFalse(regras.podeEmprestar(
            TipoUsuario.PROFESSOR, 8, 0, false));
    }

    @Test
    void professorComNoveEmprestimosNaoPodeEmprestar() {
        assertFalse(regras.podeEmprestar(
            TipoUsuario.PROFESSOR, 9, 0, false));
    }

    // Limites de emprestimos - VISITANTE

    @Test
    void visitanteSemEmprestimosPodeEmprestar() {
        assertTrue(regras.podeEmprestar(
            TipoUsuario.VISITANTE, 0, 0, false));
    }

    @Test
    void visitanteComUmEmprestimoNaoPodeEmprestar() {
        assertFalse(regras.podeEmprestar(
            TipoUsuario.VISITANTE, 1, 0, false));
    }

    @Test
    void visitanteComDoisEmprestimosNaoPodeEmprestar() {
        assertFalse(regras.podeEmprestar(
            TipoUsuario.VISITANTE, 2, 0, false));
    }

    // Fronteira da multa pendente

    @Test
    void multaZeroPermiteEmprestimo() {
        assertTrue(regras.podeEmprestar(
            TipoUsuario.ALUNO, 0, 0, false));
    }

    @Test
    void multaDe999CentavosPermiteEmprestimo() {
        assertTrue(regras.podeEmprestar(
            TipoUsuario.ALUNO, 0, 999, false));
    }

    @Test
    void multaDe1000CentavosPermiteEmprestimo() {
        assertTrue(regras.podeEmprestar(
            TipoUsuario.ALUNO, 0, 1000, false));
    }

    @Test
    void multaDe1001CentavosBloqueiaEmprestimo() {
        assertFalse(regras.podeEmprestar(
            TipoUsuario.ALUNO, 0, 1001, false));
    }

    // Condicao de atraso

    @Test
    void usuarioComAtrasoNaoPodeEmprestar() {
        assertFalse(regras.podeEmprestar(
            TipoUsuario.ALUNO, 0, 0, true));
    }

    @Test
    void usuarioSemAtrasoPodeEmprestar() {
        assertTrue(regras.podeEmprestar(
            TipoUsuario.ALUNO, 0, 0, false));
    }

    // Combinacoes de condicoes

    @Test
    void usuarioComLimiteAtingidoEMultaAltaNaoPodeEmprestar() {
        assertFalse(regras.podeEmprestar(
            TipoUsuario.ALUNO, 3, 1001, false));
    }

    @Test
    void usuarioComMultaAltaEAtrasoNaoPodeEmprestar() {
        assertFalse(regras.podeEmprestar(
            TipoUsuario.ALUNO, 0, 1001, true));
    }

    // Entradas invalidas

    @Test
    void emprestimosNegativosDevemLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.podeEmprestar(
                TipoUsuario.ALUNO, -1, 0, false));
    }

    @Test
    void multaNegativaDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.podeEmprestar(
                TipoUsuario.ALUNO, 0, -1, false));
    }

    @Test
    void tipoNuloEmPodeEmprestarDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.podeEmprestar(
                null, 0, 0, false));
    }

    @Test
    void emprestimosNegativosComMultaAltaDevemLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.podeEmprestar(
                TipoUsuario.ALUNO, -1, 1001, false));
    }

    @Test
    void multaNegativaComAtrasoDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.podeEmprestar(
                TipoUsuario.ALUNO, 0, -1, true));
    }

    // ========================================
    // TESTES: podeRenovar
    // ========================================

    // ALUNO: limite de duas renovacoes

    @Test
    void renovarAlunoSemRenovacoesDevePermitir() {
        assertTrue(regras.podeRenovar(
            TipoUsuario.ALUNO, 0, false, false));
    }

    @Test
    void renovarAlunoComUmaRenovacaoDevePermitir() {
        assertTrue(regras.podeRenovar(
            TipoUsuario.ALUNO, 1, false, false));
    }

    @Test
    void renovarAlunoComDuasRenovacoesDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.ALUNO, 2, false, false));
    }

    @Test
    void renovarAlunoComTresRenovacoesDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.ALUNO, 3, false, false));
    }

    // PROFESSOR: limite de tres renovacoes

    @Test
    void renovarProfessorSemRenovacoesDevePermitir() {
        assertTrue(regras.podeRenovar(
            TipoUsuario.PROFESSOR, 0, false, false));
    }

    @Test
    void renovarProfessorComUmaRenovacaoDevePermitir() {
        assertTrue(regras.podeRenovar(
            TipoUsuario.PROFESSOR, 1, false, false));
    }

    @Test
    void renovarProfessorComDuasRenovacoesDevePermitir() {
        assertTrue(regras.podeRenovar(
            TipoUsuario.PROFESSOR, 2, false, false));
    }

    @Test
    void renovarProfessorComTresRenovacoesDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.PROFESSOR, 3, false, false));
    }

    // VISITANTE: renovacao nunca permitida

    @Test
    void renovarVisitanteSemRenovacoesDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.VISITANTE, 0, false, false));
    }

    @Test
    void renovarVisitanteComUmaRenovacaoDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.VISITANTE, 1, false, false));
    }

    @Test
    void renovarVisitanteComReservaDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.VISITANTE, 0, true, false));
    }

    @Test
    void renovarVisitanteEmAtrasoDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.VISITANTE, 0, false, true));
    }

    // Reserva e atraso como impedimentos

    @Test
    void renovarAlunoComReservaDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.ALUNO, 0, true, false));
    }

    @Test
    void renovarAlunoEmAtrasoDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.ALUNO, 0, false, true));
    }

    @Test
    void renovarProfessorComReservaDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.PROFESSOR, 0, true, false));
    }

    @Test
    void renovarProfessorEmAtrasoDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.PROFESSOR, 0, false, true));
    }

    @Test
    void renovarAlunoSemReservaESemAtrasoDevePermitir() {
        assertTrue(regras.podeRenovar(
            TipoUsuario.ALUNO, 1, false, false));
    }

    // Combinacoes de impedimentos

    @Test
    void renovarAlunoComReservaEAtrasoDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.ALUNO, 0, true, true));
    }

    @Test
    void renovarProfessorNoLimiteComReservaDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.PROFESSOR, 3, true, false));
    }

    @Test
    void renovarAlunoNoLimiteEmAtrasoDeveBloquear() {
        assertFalse(regras.podeRenovar(
            TipoUsuario.ALUNO, 2, false, true));
    }

    // Entradas invalidas

    @Test
    void renovarComQuantidadeNegativaDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.podeRenovar(
                TipoUsuario.ALUNO, -1, false, false));
    }

    @Test
    void renovarComTipoNuloDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.podeRenovar(
                null, 0, false, false));
    }

    @Test
    void renovarVisitanteComQuantidadeNegativaDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
            () -> regras.podeRenovar(
                TipoUsuario.VISITANTE, -1, false, false));
    }


}
