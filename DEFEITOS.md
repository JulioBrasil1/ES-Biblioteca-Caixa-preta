# Defeitos encontrados — Sistema Biblioteca

## Fase 1 — Testes de caixa-preta

### Defeito 01 — Prazo incorreto para visitantes

- **Método:** `prazoDias`
- **Entrada:** `TipoUsuario.VISITANTE`
- **Resultado esperado:** 7 dias
- **Resultado obtido:** 10 dias
- **Técnica utilizada:** Partição de equivalência
- **Classe de equivalência:** Válida — usuário do tipo VISITANTE
- **Teste responsável:** `RegrasBibliotecaTest.visitanteDeveTerPrazoDe7Dias`
- **Situação:** Defeito identificado

**Descrição:** O sistema retorna 10 dias como prazo de empréstimo para visitantes, contrariando a especificação do Anexo A, que estabelece 7 dias.

### Defeito 02 — Ajuste incorreto da devolução no domingo

- **Fase:** 1 — Caixa-preta
- **Método:** `dataDevolucao`
- **Entrada:** `LocalDate.of(2026, 11, 1)`, `TipoUsuario.ALUNO`
- **Resultado esperado:** 16/11/2026
- **Resultado obtido:** 14/11/2026
- **Técnica:** Análise de valor-limite
- **Limite analisado:** Data calculada de devolução em domingo, entre sábado e segunda-feira
- **Teste:** `devolucaoNoDomingoDeveIrParaSegunda`

**Descrição:** A data calculada inicialmente é domingo, 15/11/2026. A especificação determina que a devolução seja transferida para segunda-feira, 16/11/2026. Entretanto, o sistema retorna sábado, 14/11/2026.

### Defeito 03 — Data de devolução incorreta para visitantes

- **Fase:** 1 — Caixa-preta
- **Método:** `dataDevolucao`
- **Entrada:** `LocalDate.of(2026, 11, 2)`, `TipoUsuario.VISITANTE`
- **Resultado esperado:** 09/11/2026
- **Resultado obtido:** 12/11/2026
- **Técnica:** Partição de equivalência
- **Classe:** Válida — usuário VISITANTE
- **Teste:** `visitanteDeveDevolverApos7Dias`

**Descrição:** A especificação estabelece um prazo de sete dias para visitantes, mas a data retornada corresponde a dez dias após a retirada.

**Observação:** Esta falha pode estar relacionada ao Defeito 01, identificado no método `prazoDias`. A causa interna não pode ser confirmada nesta fase de caixa-preta.

### Defeito 04 — Cobrança incorreta na mudança de faixa

- **Método:** `multaCentavos`
- **Entradas:** `(ALUNO, 11)` e `(VISITANTE, 11)`
- **Resultado esperado:** 1200 centavos em ambos os casos
- **Resultado obtido:** 1100 centavos em ambos os casos
- **Técnica:** Análise de valores-limite
- **Fronteira:** 10 para 11 dias cobrados
- **Testes:** `multaAlunoComOnzeDiasDeAtraso`, `multaVisitanteComOnzeDiasDeAtraso`

**Descrição:** A especificação determina que o 11º dia cobrado custe 200 centavos. Entretanto, o sistema retorna 1100 centavos, quando o valor correto seria 1200.

### Defeito 05 — Carência incorreta para professores

- **Método:** `multaCentavos`
- **Entradas e resultados:**

| Dias de atraso | Esperado | Obtido |
|---|---:|---:|
| 3 | 0 | 100 |
| 4 | 100 | 200 |
| 13 | 1000 | 1100 |
| 14 | 1200 | 1400 |

- **Técnicas:** Partição de equivalência e análise de valores-limite
- **Fronteiras:** Fim da carência e mudança de faixa após a carência
- **Testes:** `multaProfessorComTresDiasDeAtraso`, `multaProfessorComQuatroDiasDeAtraso`, `multaProfessorComTrezeDiasDeAtraso`, `multaProfessorComQuatorzeDiasDeAtraso`

**Descrição:** Os resultados não respeitam a carência de três dias prevista para professores. Foram observadas cobranças antecipadas e valores incorretos após a carência.

### Defeito 06 — Multa ultrapassa o teto máximo

- **Método:** `multaCentavos`
- **Entradas:** `(ALUNO, 31)` e `(PROFESSOR, 33)`
- **Resultado esperado:** 5000 centavos em ambos os casos
- **Resultado obtido:** 5200 centavos em ambos os casos
- **Técnica:** Análise de valores-limite
- **Fronteira:** Teto máximo de 5000 centavos
- **Testes:** `multaAlunoComTrintaEUmDiasDeAtraso`, `multaProfessorComTrintaETresDiasDeAtraso`

**Descrição:** O método permite que a multa ultrapasse o limite máximo de 5000 centavos por empréstimo, contrariando a especificação.

### Defeito 07 — Dias negativos não geram exceção

- **Método:** `multaCentavos`
- **Entrada:** `(ALUNO, -1)`
- **Resultado esperado:** `IllegalArgumentException`
- **Resultado obtido:** Nenhuma exceção lançada
- **Técnicas:** Partição de equivalência inválida e análise de valor-limite
- **Fronteira:** -1 dia, imediatamente abaixo do mínimo válido (0)
- **Teste:** `multaComDiasNegativosDeveLancarExcecao`

**Descrição:** O método aceita um número negativo de dias de atraso sem lançar a exceção exigida pela especificação.

### Defeito 08 — Empréstimo permitido com limite atingido

- **Método:** `podeEmprestar`
- **Entradas e resultados:**

| Entrada (tipo, ativos, multa, atraso) | Esperado | Obtido |
|---|---|---|
| ALUNO, 3, 0, false | false | true |
| PROFESSOR, 8, 0, false | false | true |
| VISITANTE, 1, 0, false | false | true |

- **Técnica:** Análise de valores-limite
- **Fronteiras:** 3 empréstimos para ALUNO, 8 para PROFESSOR e 1 para VISITANTE
- **Testes:** `alunoComTresEmprestimosNaoPodeEmprestar`, `professorComOitoEmprestimosNaoPodeEmprestar`, `visitanteComUmEmprestimoNaoPodeEmprestar`

**Descrição:** O método permite novos empréstimos mesmo quando o usuário já atingiu o limite máximo de empréstimos ativos estabelecido pela especificação.

### Defeito 09 — Multa superior ao limite não bloqueia empréstimo

- **Método:** `podeEmprestar`
- **Entrada:** `(ALUNO, 0, 1001, false)`
- **Resultado esperado:** false
- **Resultado obtido:** true
- **Técnica:** Análise de valores-limite
- **Fronteira:** 1000 para 1001 centavos
- **Teste:** `multaDe1001CentavosBloqueiaEmprestimo`

**Descrição:** A especificação permite novos empréstimos somente quando a multa pendente é menor ou igual a 1000 centavos. O sistema permite empréstimos com 1001 centavos de multa pendente.

### Defeito 10 — Usuário com atraso pode realizar novo empréstimo

- **Método:** `podeEmprestar`
- **Entrada:** `(ALUNO, 0, 0, true)`
- **Resultado esperado:** false
- **Resultado obtido:** true
- **Técnica:** Partição de equivalência
- **Classe:** Válida — usuário com empréstimo em atraso
- **Teste:** `usuarioComAtrasoNaoPodeEmprestar`

**Descrição:** O sistema permite um novo empréstimo mesmo quando o parâmetro `possuiAtraso` é verdadeiro, contrariando a especificação.

### Defeito 11 — Multa pendente negativa não gera exceção

- **Método:** `podeEmprestar`
- **Entradas:** `(ALUNO, 0, -1, false)` e `(ALUNO, 0, -1, true)`
- **Resultado esperado:** IllegalArgumentException
- **Resultado obtido:** Nenhuma exceção lançada
- **Técnicas:** Partição de equivalência inválida e análise de valores-limite
- **Fronteira:** -1 centavo, imediatamente abaixo do mínimo válido (0)
- **Testes:** `multaNegativaDeveLancarExcecao`, `multaNegativaComAtrasoDeveLancarExcecao`

**Descrição:** O método aceita valores negativos para a multa pendente sem lançar a exceção exigida pela especificação.

### Evidência complementar — Condições impeditivas combinadas

- **Método:** `podeEmprestar`
- **Entrada:** `(ALUNO, 3, 1001, false)`
- **Resultado esperado:** false
- **Resultado obtido:** true
- **Teste:** `usuarioComLimiteAtingidoEMultaAltaNaoPodeEmprestar`

**Descrição:** O sistema permite um empréstimo mesmo quando duas condições impeditivas estão presentes simultaneamente. Essa falha reforça os Defeitos 08 e 09, sem necessariamente representar uma causa independente.

### Defeito 12 — Professor pode renovar após atingir o limite

- **Método:** `podeRenovar`
- **Entrada:** `(PROFESSOR, 3, false, false)`
- **Resultado esperado:** `false`
- **Resultado obtido:** `true`
- **Técnica:** Análise de valores-limite
- **Fronteira:** 3 renovações realizadas, limite máximo permitido para professores
- **Teste:** `renovarProfessorComTresRenovacoesDeveBloquear`

**Descrição:** O sistema permite que um professor realize uma nova renovação mesmo quando já efetuou três renovações. A especificação estabelece que professores podem renovar no máximo três vezes, portanto uma quarta renovação deve ser bloqueada.

### Defeito 13 — Visitantes conseguem renovar empréstimos

- **Método:** `podeRenovar`
- **Entradas e resultados:**

| Entrada (tipo, renovações, reserva, atraso) | Esperado | Obtido |
|---|---|---|
| VISITANTE, 0, false, false | false | true |
| VISITANTE, 1, false, false | false | true |

- **Técnica:** Partição de equivalência
- **Classe:** VISITANTE com quantidade válida de renovações
- **Testes:** `renovarVisitanteSemRenovacoesDeveBloquear`, `renovarVisitanteComUmaRenovacaoDeveBloquear`

**Descrição:** O sistema permite renovações para usuários do tipo VISITANTE, contrariando a regra de que visitantes nunca podem renovar empréstimos. O comportamento incorreto foi observado tanto com zero quanto com uma renovação anterior.