# Relatório de Projeto - POO
## Sistema de Gerenciamento de Biblioteca

---

## 1. Tema e Problema

O sistema desenvolvido gerencia o acervo e empréstimos de uma biblioteca acadêmica do IFPB.

**Objetivo do sistema:**
- Registrar usuários de diferentes categorias com limites específicos de empréstimo
- Cadastrar itens do acervo (livros, revistas, teses) com características próprias
- Realizar empréstimos validando disponibilidade e limites
- Calcular multas por atraso considerando o tipo de item e categoria do usuário
- Enviar notificações por email ou SMS
- Consultar histórico de empréstimos e situação de usuários

---

## 2. Funcionalidades Principais

### 2.1 Realizar Empréstimo
- **Entrada:** Matrícula do usuário, código do item, tipo de notificação
- **Validações:** 
  - Verifica se o item está disponível
  - Verifica se o usuário não atingiu seu limite de empréstimos simultâneos
  - Valida existência de usuário e item
- **Saída:** Confirmação do empréstimo com prazo de devolução
- **Ação:** Marca item como indisponível e envia notificação

### 2.2 Listar Itens
- Versão 1: Lista todos os itens do acervo
- Versão 2: Lista apenas itens disponíveis (sobrecarga)
- Mostra título, código, tipo e status de cada item

### 2.3 Consultar Empréstimos de Usuário
- **Entrada:** Matrícula do usuário
- **Saída:** Lista todos os empréstimos (ativos e finalizados) com datas e multas
- Exibe total de multas acumuladas

### 2.4 Listar Empréstimos Ativos
- Mostra todos os empréstimos não finalizados no sistema
- Útil para controle geral da biblioteca

### 2.5 Cadastrar Itens e Usuários
- Permite adicionar novos livros, revistas ou teses
- Permite cadastrar novos alunos, professores ou funcionários
- Interface guiada com validações

### 2.6 Sistema de Notificações
- Notifica sobre empréstimos realizados
- Pode enviar por Email ou SMS (polimorfismo de interface)

---

## 3. Modelagem Orientada a Objetos

### 3.1 Principais Classes e Responsabilidades

#### **ItemBiblioteca** (classe abstrata)
- Representa um item genérico do acervo
- Armazena: título, código, disponibilidade, dias de empréstimo
- Define método abstrato `calcularMultaPorDia()` para polimorfismo

#### **Livro, Revista, Tese** (herança de ItemBiblioteca)
- **Livro:** Adiciona autor e ISBN; 14 dias de empréstimo; R$ 1,50/dia de multa
- **Revista:** Adiciona edição e data; 7 dias de empréstimo; R$ 0,50/dia de multa
- **Tese:** Adiciona autor, orientador e ano; 21 dias de empréstimo; R$ 2,00/dia de multa
- Cada classe implementa seu próprio cálculo de multa

#### **Usuario** (classe abstrata)
- Representa um usuário genérico da biblioteca
- Armazena: nome, matrícula, lista de empréstimos, limite de empréstimos
- Define método abstrato `aplicarDescontoMulta()` para diferentes descontos

#### **Aluno, Professor, Funcionario** (herança de Usuario)
- **Aluno:** Limite de 3 empréstimos; sem desconto em multas; possui curso
- **Professor:** Limite de 5 empréstimos; 50% de desconto; possui departamento
- **Funcionário:** Limite de 4 empréstimos; 30% de desconto; possui setor
- Cada classe define sua política de desconto

#### **Emprestimo**
- Registra um empréstimo de item para usuário
- Calcula datas (empréstimo, previsão, devolução)
- Calcula multa automaticamente considerando: dias de atraso, tipo de item e categoria do usuário
- Controla status (ativo/finalizado)

#### **Notificavel** (interface)
- Contrato para objetos capazes de enviar notificações
- Método: `notificar(String mensagem)`

#### **NotificacaoEmail, NotificacaoSMS** (implementam Notificavel)
- **NotificacaoEmail:** Simula envio de email para endereço específico
- **NotificacaoSMS:** Simula envio de SMS para telefone
- Demonstram polimorfismo de interface

#### **BibliotecaService**
- Classe de serviço que gerencia todas as operações
- Mantém coleções de usuários, itens e empréstimos (composição)
- Implementa lógica de negócio e validações
- Possui métodos com sobrecarga para diferentes formas de busca e listagem

#### **Exceções Personalizadas**
- **ItemIndisponivelException:** Item não está disponível para empréstimo
- **LimiteEmprestimoException:** Usuário atingiu limite de empréstimos
- **UsuarioNaoEncontradoException:** Matrícula não cadastrada
- **ItemNaoEncontradoException:** Código de item não encontrado

#### **MainBiblioteca**
- Classe com método `main` - ponto de entrada do sistema
- Exibe menu interativo
- Orquestra chamadas aos métodos de BibliotecaService
- Trata exceções e exibe mensagens amigáveis
- Inicializa dados de demonstração

---

### 3.2 Diagrama de Classes (Descrição Textual)

```
ItemBiblioteca (abstrata)
├── atributos: titulo, codigo, disponivel, diasEmprestimo
├── método abstrato: calcularMultaPorDia()
└── subclasses: Livro, Revista, Tese

Usuario (abstrata)
├── atributos: nome, matricula, emprestimos (List), limiteEmprestimos
├── método abstrato: aplicarDescontoMulta()
└── subclasses: Aluno, Professor, Funcionario

Emprestimo
├── atributos: usuario, item, dataEmprestimo, dataPrevista, dataDevolucao, finalizado, multa
└── associações: Usuario (1), ItemBiblioteca (1)

Notificavel (interface)
├── método: notificar(String)
└── implementações: NotificacaoEmail, NotificacaoSMS

BibliotecaService
├── atributos: usuarios (List<Usuario>), itens (List<ItemBiblioteca>), emprestimos (List<Emprestimo>)
├── composição com Usuario, ItemBiblioteca e Emprestimo
└── métodos com sobrecarga: listarItens(), buscarItem()

MainBiblioteca
└── usa BibliotecaService, Notificavel e todas as classes de domínio
```

---

### 3.3 Onde Aparecem os Conceitos de POO

#### ✅ **Herança**
1. **ItemBiblioteca → Livro, Revista, Tese**
   - Localização: pacote `model`
   - Livro, Revista e Tese herdam de ItemBiblioteca
   - Cada subclasse adiciona atributos específicos (autor, edição, orientador)

2. **Usuario → Aluno, Professor, Funcionario**
   - Localização: pacote `model`
   - Aluno, Professor e Funcionario herdam de Usuario
   - Cada subclasse define seu próprio limite e categoria

#### ✅ **Polimorfismo**
1. **Polimorfismo de classe:**
   ```java
   ItemBiblioteca item = new Livro(...);  // ou Revista, ou Tese
   double multa = item.calcularMultaPorDia();  // chama método da subclasse
   ```
   - Variável tipo ItemBiblioteca pode referenciar qualquer subclasse
   - Método abstrato sobrescrito em cada subclasse

2. **Polimorfismo de interface:**
   ```java
   Notificavel notif = new NotificacaoEmail(...);  // ou NotificacaoSMS
   notif.notificar("Mensagem");  // chama implementação específica
   ```
   - Variável tipo Notificavel referencia diferentes implementações

#### ✅ **Classe Abstrata e Sobreposição**
1. **ItemBiblioteca:**
   - Classe abstrata com método abstrato `calcularMultaPorDia()`
   - Livro retorna 1.50, Revista retorna 0.50, Tese retorna 2.00
   - Uso de `@Override` nas subclasses

2. **Usuario:**
   - Classe abstrata com método abstrato `aplicarDescontoMulta()`
   - Aluno retorna valor integral, Professor aplica 50%, Funcionario aplica 30%
   - Uso de `@Override` nas subclasses

#### ✅ **Interface**
- **Notificavel:** Interface com método `notificar(String)`
- **NotificacaoEmail:** Implementa enviando email
- **NotificacaoSMS:** Implementa enviando SMS
- Usado em `BibliotecaService.realizarEmprestimo()` como parâmetro

#### ✅ **Composição e Coleções**
1. **BibliotecaService mantém:**
   - `List<Usuario> usuarios` - composição com Usuario
   - `List<ItemBiblioteca> itens` - composição com ItemBiblioteca
   - `List<Emprestimo> emprestimos` - composição com Emprestimo

2. **Usuario mantém:**
   - `List<Emprestimo> emprestimos` - composição one-to-many

3. **Emprestimo tem:**
   - 1 Usuario e 1 ItemBiblioteca - composição many-to-one

#### ✅ **Sobrecarga (Overloading)**
**Em BibliotecaService:**

1. **listarItens()** vs **listarItens(boolean apenasDisponiveis)**
   ```java
   biblioteca.listarItens();           // lista todos
   biblioteca.listarItens(true);       // lista só disponíveis
   ```

2. **buscarItem(String codigo)** vs **buscarItem(String titulo, boolean buscarPorTitulo)**
   ```java
   ItemBiblioteca item = biblioteca.buscarItem("LIV001");        // busca por código
   List<ItemBiblioteca> itens = biblioteca.buscarItem("Java", true);  // busca por título
   ```

#### ✅ **Tratamento de Exceções**
1. **Exceções personalizadas criadas:**
   - `ItemIndisponivelException`
   - `LimiteEmprestimoException`
   - `UsuarioNaoEncontradoException`
   - `ItemNaoEncontradoException`

2. **Lançamento de exceções:**
   - `BibliotecaService.buscarUsuario()` lança `UsuarioNaoEncontradoException`
   - `BibliotecaService.realizarEmprestimo()` lança múltiplas exceções

3. **Tratamento na main:**
   ```java
   try {
       realizarEmprestimo(scanner, biblioteca);
   } catch (ItemIndisponivelException | LimiteEmprestimoException e) {
       System.out.println("✗ " + e.getMessage());
   }
   ```

#### ✅ **Encapsulamento**
- Todos os atributos são `private` ou `protected`
- Acesso apenas via getters/setters apropriados
- Lógica de negócio encapsulada nas classes de modelo e serviço

---

## 4. Estrutura da Main

A classe **MainBiblioteca** tem responsabilidades bem definidas:

### 4.1 Responsabilidades
1. **Inicializar o sistema:**
   - Criar instância de `BibliotecaService`
   - Carregar dados iniciais de demonstração (`inicializarDados()`)

2. **Gerenciar interface com usuário:**
   - Exibir menu formatado
   - Ler opções do teclado
   - Validar entrada numérica

3. **Orquestrar operações:**
   - Chamar métodos privados específicos para cada funcionalidade
   - Passar referências de Scanner e BibliotecaService

4. **Tratar exceções:**
   - Capturar exceções de negócio e exibir mensagens amigáveis
   - Capturar erros de formato e entrada inválida
   - Manter sistema rodando mesmo após erros

### 4.2 Métodos Auxiliares Privados
- `exibirMenu()` - apenas exibe o menu
- `realizarEmprestimo()` - coleta dados e chama serviço
- `consultarEmprestimos()` - coleta matrícula e exibe histórico
- `cadastrarNovoItem()` - guia cadastro de item
- `cadastrarNovoUsuario()` - guia cadastro de usuário
- `inicializarDados()` - cria dados de exemplo

### 4.3 O que a Main NÃO faz
❌ Não contém regras de negócio (cálculos, validações complexas)  
❌ Não manipula coleções internas de outras classes  
❌ Não implementa lógica de empréstimo, multas ou disponibilidade  
❌ Não acessa diretamente atributos privados de objetos

**Princípio:** A main é apenas o maestro - coordena a orquestra, mas não toca os instrumentos.

---

## 5. Considerações Finais

O sistema atende plenamente aos requisitos propostos, demonstrando domínio dos conceitos fundamentais de Programação Orientada a Objetos:

### 5.1 Requisitos Atendidos
✅ **6+ classes de domínio:** ItemBiblioteca, Livro, Revista, Tese, Usuario, Aluno, Professor, Funcionario, Emprestimo (9 classes)

✅ **Encapsulamento:** Todos os atributos privados com getters/setters apropriados

✅ **Composição:** BibliotecaService compõe listas de Usuario, ItemBiblioteca e Emprestimo

✅ **Coleções:** Uso extensivo de `List<>` e `ArrayList<>`

✅ **Herança:** Duas hierarquias completas (ItemBiblioteca e Usuario)

✅ **Classe abstrata:** ItemBiblioteca e Usuario com métodos abstratos

✅ **Polimorfismo:** De classe (ItemBiblioteca) e de interface (Notificavel)

✅ **Interface:** Notificavel com duas implementações distintas

✅ **Sobrecarga:** Múltiplos métodos `listarItens()` e `buscarItem()`

✅ **Exceções personalizadas:** 4 exceções de negócio criadas e tratadas

✅ **Main organizada:** Apenas orquestração, sem lógica de negócio

### 5.2 Destaques do Projeto
1. **Cálculo inteligente de multas:** Considera tipo de item E categoria de usuário
2. **Validações robustas:** Verifica disponibilidade, limites e existência
3. **Flexibilidade:** Fácil adicionar novos tipos de itens ou usuários
4. **Notificações plugáveis:** Sistema de notificação extensível via interface
5. **Tratamento de erros completo:** Exceções específicas para cada situação

### 5.3 Possíveis Extensões
- Adicionar funcionalidade de devolução via interface
- Implementar renovação de empréstimos
- Criar relatórios estatísticos
- Adicionar reserva de itens
- Persistência em banco de dados
- Interface gráfica (JavaFX ou Swing)

---

## 6. Instruções de Compilação e Execução

### Estrutura de Pacotes
```
src/ 
    └── model/
    │   ├── ItemBiblioteca.java
    │   ├── Livro.java
    │   ├── Revista.java
    │   ├── Tese.java
    │   ├── Usuario.java
    │   ├── Aluno.java
    │   ├── Professor.java
    │   ├── Funcionario.java
    │   ├── Emprestimo.java
    │   ├── Notificavel.java
    │   ├── NotificacaoEmail.java
    │   └── NotificacaoSMS.java
    ├── service/
    │   └── BibliotecaService.java
    ├── exception/
    │   ├── ItemIndisponivelException.java
    │   ├── LimiteEmprestimoException.java
    │   ├── UsuarioNaoEncontradoException.java
    │   └── ItemNaoEncontradoException.java
    └── view/
        └── Main.java
```

### Comandos
```bash
# Compilar
javac -encoding UTF-8 -d ../bin exception/*.java model/*.java service/*.java Main.java

# Executar
java -cp ../bin Main
```

---

**Desenvolvido como projeto final da disciplina de Programação Orientada a Objetos**  
**IFPB - Instituto Federal da Paraíba**
