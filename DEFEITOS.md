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