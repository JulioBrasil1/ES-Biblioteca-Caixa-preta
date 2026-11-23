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

**Resultado da execução:** Foram executados oito testes,
dos quais seis passaram e dois falharam.

**Defeitos identificados:**
- Ajuste incorreto da devolução quando a data calculada cai em domingo.
- Data de devolução incorreta para visitantes, possivelmente
  relacionada ao defeito encontrado em prazoDias.

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

## Método: multaCentavos(TipoUsuario tipo, int diasAtraso)

**Técnicas:** Partição de equivalência e análise de valores-limite.

### Classes de equivalência

| Classe | Descrição | Resultado esperado |
|---|---|---|
| CE1 — Válida | ALUNO ou VISITANTE sem atraso | 0 centavos |
| CE2 — Válida | ALUNO ou VISITANTE com 1 a 10 dias de atraso | 100 centavos por dia |
| CE3 — Válida | ALUNO ou VISITANTE com 11 a 29 dias | 1000 + 200 por dia excedente |
| CE4 — Válida | ALUNO ou VISITANTE com 30 ou mais dias | 5000 centavos |
| CE5 — Válida | PROFESSOR com 0 a 3 dias de atraso | 0 centavos |
| CE6 — Válida | PROFESSOR com 4 a 13 dias de atraso | 100 centavos por dia após a carência |
| CE7 — Válida | PROFESSOR com 14 a 32 dias de atraso | 1000 + 200 por dia cobrado excedente |
| CE8 — Válida | PROFESSOR com 33 ou mais dias de atraso | 5000 centavos |
| CE9 — Inválida | Dias de atraso negativos | IllegalArgumentException |
| CE10 — Inválida | Tipo de usuário nulo | IllegalArgumentException |

### Análise de valores-limite

- **Início da cobrança:** 0 e 1 dia de atraso.
- **Mudança de faixa:** 9, 10, 11 e 12 dias cobrados.
- **Teto da multa:** 29, 30 e 31 dias cobrados.
- **Carência dos professores:** 2, 3 e 4 dias de atraso.
- **Mudança de faixa dos professores:** 13 e 14 dias de atraso.
- **Teto dos professores:** 33 dias de atraso.
- **Entrada inválida:** -1 dia de atraso.

**Critério de aprovação:** O valor deve corresponder às faixas previstas na especificação, respeitando a carência de professores, o teto de 5000 centavos por empréstimo e as exceções para entradas inválidas.

**Testes implementados:** 19 casos, identificados de CT01 a CT19.

**Resultado da execução:** Foram executados 19 testes, dos quais 10 passaram e 9 falharam, sem erros de execução.

**Defeitos identificados:**
- Cobrança incorreta na mudança de faixa de 10 para 11 dias cobrados.
- Aplicação incorreta da carência de três dias para professores.
- Multas superiores ao teto máximo de 5000 centavos.
- Ausência de exceção para dias negativos.

As nove falhas foram agrupadas em quatro comportamentos incorretos, documentados nos Defeitos 04 a 07 do arquivo `DEFEITOS.md`.