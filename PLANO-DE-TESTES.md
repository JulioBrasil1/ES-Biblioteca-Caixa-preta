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

**Resultado da execução:** Foram executados oito testes,
dos quais seis passaram e dois falharam.

**Defeitos identificados:**
- Ajuste incorreto da devolução quando a data calculada cai em domingo.
- Data de devolução incorreta para visitantes, possivelmente
  relacionada ao defeito encontrado em prazoDias.

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

## Método: limiteEmprestimos(TipoUsuario tipo)

**Técnica:** Partição de equivalência.

| Classe | Entrada | Resultado esperado | Teste |
|---|---|---|---|
| CE1 — Válida | ALUNO | 3 | alunoDeveTerLimiteDe3Emprestimos |
| CE2 — Válida | PROFESSOR | 8 | professorDeveTerLimiteDe8Emprestimos |
| CE3 — Válida | VISITANTE | 1 | visitanteDeveTerLimiteDe1Emprestimo |
| CE4 — Inválida | null | IllegalArgumentException | tipoNuloEmLimiteEmprestimosDeveLancarExcecao |

**Análise de valor-limite:** Não aplicável à entrada, pois o método recebe um enum sem intervalo numérico.

**Critério de aprovação:** O método deve retornar o limite correspondente ao tipo de usuário e lançar `IllegalArgumentException` para tipo nulo.

**Resultado da execução:** Foram executados quatro testes,
todos aprovados. Nenhum defeito foi identificado nos casos testados.