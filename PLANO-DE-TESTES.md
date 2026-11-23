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

## Método: dataDevolucao(LocalDate retirada, TipoUsuario tipo)

**Técnicas:** Partição de equivalência e análise de valores-limite.

### Classes de equivalência

| Classe | Descrição | Teste |
|---|---|---|
| CE1 — Válida | Retirada com usuário ALUNO | alunoDeveDevolverApos14Dias |
| CE2 — Válida | Retirada com usuário PROFESSOR | professorDeveDevolverApos30Dias |
| CE3 — Válida | Retirada com usuário VISITANTE | visitanteDeveDevolverApos7Dias |
| CE4 — Inválida | Data de retirada nula | retiradaNulaDeveLancarExcecao |
| CE5 — Inválida | Tipo de usuário nulo | tipoNuloEmDataDevolucaoDeveLancarExcecao |

### Análise de valores-limite

| Fronteira | Teste | Resultado esperado |
|---|---|---|
| Sábado, imediatamente antes do domingo | devolucaoNoSabadoDeveSerMantida | Data mantida |
| Domingo | devolucaoNoDomingoDeveIrParaSegunda | Adiada um dia |
| Segunda-feira, imediatamente após o domingo | devolucaoNaSegundaDeveSerMantida | Data mantida |

**Critério de aprovação:** A data calculada deve corresponder ao prazo definido para o tipo de usuário, com adiamento somente quando a devolução cair em domingo.