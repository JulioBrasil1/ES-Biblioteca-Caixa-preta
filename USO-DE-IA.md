# Registro de Uso de Inteligência Artificial — Fase 1

## 1. Ferramenta utilizada

- **Ferramenta:** ChatGPT (OpenAI)
- **Modelo:** GPT-6
- **Finalidade:** Apoio ao planejamento, elaboração, execução e documentação de testes de software.
- **Disciplina:** Engenharia de Software
- **Projeto:** Sistema de Biblioteca — Testes de Caixa-preta
- **Etapa:** Fase 1

## 2. Objetivo da utilização

A inteligência artificial foi utilizada como ferramenta de apoio durante a elaboração dos testes de caixa-preta para a biblioteca Java fornecida na atividade.

O objetivo foi auxiliar na interpretação das regras especificadas, identificação de classes de equivalência e valores-limite, construção de casos de teste com JUnit 5 e organização da documentação dos resultados.

A IA não foi utilizada para acessar, descompilar ou analisar o código interno do arquivo JAR fornecido.

## 3. Atividades realizadas com auxílio da IA

### 3.1. Interpretação da especificação

A IA auxiliou na compreensão dos requisitos funcionais dos sete métodos disponibilizados:

1. `prazoDias`
2. `dataDevolucao`
3. `limiteEmprestimos`
4. `multaCentavos`
5. `podeEmprestar`
6. `podeRenovar`
7. `multaTotalCentavos`

Foram discutidas as regras de prazo, limites de empréstimos, cálculo de multas, renovação e validação de entradas inválidas.

### 3.2. Planejamento dos testes

A IA auxiliou na identificação de:

- Classes de equivalência válidas e inválidas.
- Valores-limite e condições de fronteira.
- Casos de teste positivos e negativos.
- Cenários com valores nulos, negativos e combinações de condições.
- Resultados esperados conforme a especificação.

Os casos foram organizados no arquivo `PLANO-DE-TESTES.md`.

### 3.3. Implementação dos testes automatizados

A IA auxiliou na elaboração de métodos de teste utilizando Java e JUnit 5, incluindo as seguintes verificações:

- `assertEquals` para comparar valores esperados e obtidos.
- `assertTrue` e `assertFalse` para verificar condições booleanas.
- `assertThrows` para validar exceções previstas na especificação.

Os testes foram implementados no arquivo `src/test/java/br/edu/biblioteca/RegrasBibliotecaTest.java`.

Ao final, foram implementados 105 testes nessa classe, além do teste de exemplo já fornecido no projeto.

### 3.4. Execução e interpretação dos resultados

A execução foi realizada localmente, utilizando Maven e o comando:

`mvn test`

Os resultados apresentados pelo terminal foram compartilhados com a IA para auxiliar na identificação das divergências entre os valores esperados e os retornados pela biblioteca.

**Resultado consolidado:**

| Indicador | Quantidade |
|---|---:|
| Testes executados | 106 |
| Testes aprovados | 65 |
| Testes com falha | 41 |
| Erros de execução | 0 |
| Testes ignorados | 0 |

A IA auxiliou na interpretação dos relatórios do JUnit e na distinção entre falhas de comportamento e erros de execução.

Também foi esclarecido que a mensagem `BUILD SUCCESS` do Maven não significa necessariamente que todos os testes passaram, devido à configuração do projeto.

### 3.5. Documentação dos defeitos

A IA auxiliou na organização dos comportamentos incorretos identificados durante os testes, incluindo:

- Método testado.
- Entradas utilizadas.
- Resultado esperado.
- Resultado obtido.
- Técnica de teste aplicada.
- Descrição da divergência.

Os resultados foram organizados no arquivo `DEFEITOS.md`, com 18 registros de defeitos.

Falhas relacionadas foram agrupadas quando representavam um mesmo comportamento incorreto, evitando considerar automaticamente cada teste que falhou como um defeito independente.

## 4. Exemplos de solicitações feitas à IA

Durante a atividade, foram realizadas solicitações com objetivos semelhantes aos seguintes:

**Exemplo 1 — Planejamento:**

> Com base na especificação do método, identifique classes de equivalência e valores-limite e proponha casos de teste de caixa-preta.

**Exemplo 2 — Implementação:**

> Gere testes JUnit 5 para o método, utilizando os resultados esperados definidos pela especificação, sem consultar a implementação da biblioteca.

**Exemplo 3 — Análise de resultados:**

> Analise a saída do Maven, identifique quais testes falharam e compare os resultados esperados com os resultados obtidos.

**Exemplo 4 — Documentação:**

> Organize os defeitos encontrados com método, entrada, resultado esperado, resultado obtido e técnica de teste utilizada.

**Exemplo 5 — Revisão:**

> Verifique se os resultados dos testes e os registros de defeitos estão consistentes com as regras especificadas.

Esses exemplos representam os tipos de interação realizados durante o desenvolvimento, não necessariamente transcrições literais de todas as mensagens.

## 5. Verificação e correção das respostas da IA

As respostas da IA foram utilizadas como apoio e precisaram ser verificadas em relação à especificação e aos resultados reais dos testes.

Um exemplo ocorreu no teste `multaTotalAbaixoDoTeto`, com a entrada:

`ALUNO, [29, 29, 1]`

Inicialmente, a IA indicou incorretamente o valor esperado de 9800 centavos.

Após a revisão do cálculo, o resultado correto foi identificado:

- Primeiro empréstimo: 4800 centavos.
- Segundo empréstimo: 4800 centavos.
- Terceiro empréstimo: 100 centavos.
- Total: 9700 centavos.

O teste e a documentação foram corrigidos para utilizar 9700 centavos como resultado esperado.

Esse caso demonstra que as respostas da IA podem conter erros e não devem ser aceitas sem conferência.

## 6. Responsabilidade e limitações

A IA foi utilizada como ferramenta auxiliar, não como fonte de verdade para o comportamento esperado do sistema.

Os resultados esperados foram fundamentados nas regras da especificação, enquanto os resultados obtidos foram coletados por meio da execução real dos testes.

As falhas encontradas não foram consideradas evidência suficiente para determinar a causa interna dos problemas, pois a atividade utiliza a abordagem de caixa-preta.

Não foram realizadas alterações no arquivo JAR para corrigir os defeitos identificados.

## 7. Conclusão

O uso da inteligência artificial contribuiu para estruturar o planejamento dos testes, ampliar a cobertura de cenários, apoiar a implementação em JUnit 5 e organizar a documentação dos defeitos.

A execução final contemplou os sete métodos especificados, totalizando 106 testes, com 41 falhas observadas e 18 registros de defeitos.

A atividade demonstrou a importância de utilizar a IA de forma crítica, conferindo seus resultados e mantendo a especificação como referência para definir o comportamento correto do sistema.