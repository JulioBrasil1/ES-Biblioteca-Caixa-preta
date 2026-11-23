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