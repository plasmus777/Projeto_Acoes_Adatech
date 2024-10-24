# Projeto Ações AdaTech 📊💻

## Descrição
O **Projeto Ações AdaTech** é uma aplicação Java desenvolvida com **Spring Boot** que facilita o gerenciamento de ativos financeiros, como ações, fundos imobiliários e renda fixa. O sistema permite que os usuários cadastrem seus investimentos e configurem alertas de compra e venda com base em variações nos valores dos ativos.

Este projeto foi criado para ajudar investidores a monitorar suas carteiras e tomar decisões estratégicas, definindo limites de preço personalizados.

---

## Funcionalidades ⚙️
- 📈 **Cadastro de ativos**: Ações, fundos imobiliários e renda fixa.
- 🚨 **Alertas de preço**: Configure notificações para alertar sobre:
  - Valor mínimo (indicado para compra).
  - Valor máximo (indicado para venda).
- 📩 **Notificações automáticas**: Receba alertas por e-mail quando os ativos atingirem os limites configurados.
- 📊 **Relatórios diários**: Gere relatórios sobre a performance dos ativos cadastrados, auxiliando na análise de investimentos.

---

## Tecnologias Utilizadas 🛠️

| Tecnologia  | Descrição  |  |
| ----------- | ----------- | ---- |
| **Java**    | Linguagem principal utilizada no projeto. | ![Java](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg) |
| **Spring Boot** | Framework usado para construção da API. | ![Spring Boot](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg) |
| **H2 Database**   | Banco de dados utilizado para armazenar informações sobre ativos e usuários. |
| **Notificações via E-mail** | Integração com serviços de notificação para alertas automáticos. | 📧 |

---

## Requisitos 📋
- **Java 17+** (ou versão mais recente)
- **Maven** (para gerenciamento de dependências)

---

## Download, compilação e execução 🚀
> [!WARNING]
> Para baixar, compilar e executar este programa, é necessário instalar: [Git](https://git-scm.com/downloads) e o [JDK](https://www.oracle.com/java/technologies/downloads/).

Utilizando um terminal, é possível baixar, compilar e executar este programa em sua máquina local com os seguintes comandos:

1 - Baixe o código deste repositório para a sua máquina:
```
git clone https://github.com/plasmus777/Projeto_Acoes_Adatech.git
```

2 - Entre na pasta do projeto:
```
cd Projeto_Acoes_Adatech
```
Edite o arquivo "src/main/resources/application.properties" com seu editor de texto preferido:
```
nano src/main/resources/application.properties
```
Agora, troque o campo "api.finnhub.key=" por "api.finnhub.key={SUA_CHAVE_API}", onde {SUA_CHAVE_API} é o valor obtido através da chave de API do [FinnHub](https://finnhub.io/).
Por fim, salve o arquivo e feche o editor de texto.


3 - Compile o projeto:
```
./mvnw package
```

4 - Execute o projeto:
```
cd target

java -jar ./ProjetoAcoesAdatech-0.0.1-SNAPSHOT.jar
```

---

## Desenvolvido por 💻
- [Fernando Lopes](https://github.com/plasmus777)
- [Jonathan Eduardo de Oliveira](https://github.com/jonathaneduardodeoliveira)
- [Lucas Souza](https://github.com/Luuqee)
