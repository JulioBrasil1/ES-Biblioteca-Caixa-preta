# Plano de Testes — Sistema Biblioteca

## Fase 1 — Caixa-preta

### Método: prazoDias(TipoUsuario tipo)

**Técnica:** Partição de equivalência.

O método recebe um valor do enum `TipoUsuario`. Foram identificadas três classes válidas e uma classe inválida.

| Classe | Entrada | Resultado esperado | Teste |
|---|---|---|---|
| CE1 — Válida | ALUNO | 14 | alunoDeveTerPrazoDe14Dias |
| CE2 — Válida | PROFESSOR | 30 | professorDeveTerPrazoDe30Dias |
| CE3 — Válida | VISITANTE | 7 | visitanteDeveTerPrazoDe7Dias |
| CE4 — Inválida | null | IllegalArgumentException | tipoNuloDeveLancarExcecao |

**Análise de valor-limite:** Não aplicável à entrada, pois `TipoUsuario` é uma enumeração sem intervalo numérico.

**Resultado da execução:** Os quatro testes da classe `RegrasBibliotecaTest` foram executados. Três passaram e um falhou, revelando um defeito no prazo de visitantes.