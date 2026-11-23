
package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.*;

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
}
