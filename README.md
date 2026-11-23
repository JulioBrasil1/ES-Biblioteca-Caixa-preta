# Kit da fase 1 (caixa-preta)

- `lib/biblioteca-1.0.jar`: o sistema da biblioteca, compilado. Ele contém defeitos.
- `src/test/java`: escrevam aqui os testes (JUnit 5). Há um exemplo em `ExemploTest.java`.
- A especificação do sistema está no Anexo A do enunciado.

Para rodar: abram a pasta como projeto Maven (IntelliJ, VS Code ou Eclipse) ou usem `mvn test`.
Requer JDK 17 ou superior.

Testes que falham são esperados: o resultado esperado vem da especificação, então uma falha
indica um possível defeito. Nunca ajustem o valor esperado para o que o sistema devolve.

Façam um repositório git desta pasta desde já: o histórico será usado na fase 3.

## Testes de caixa-preta — Fase 1

Este repositório contém os testes de caixa-preta desenvolvidos para o sistema de biblioteca, utilizando Java, JUnit 5 e Maven.

Os testes foram elaborados com base na especificação funcional do sistema, utilizando técnicas de partição de equivalência e análise de valores-limite.

### Resultados

- **106 testes executados**, incluindo o teste de exemplo.
- **65 testes aprovados.**
- **41 testes com falha.**
- **0 erros de execução.**
- **18 registros de defeitos documentados.**

As falhas são esperadas nesta etapa, pois a biblioteca fornecida contém defeitos intencionais.

### Documentação

- `PLANO-DE-TESTES.md`: planejamento, técnicas utilizadas e resultados dos testes.
- `DEFEITOS.md`: registro das divergências identificadas.
- `USO-DE-IA.md`: documentação da utilização de inteligência artificial durante a atividade.

### Execução

Para executar todos os testes:

```bash
mvn test
```

**Requisito:** JDK 17 ou superior.
