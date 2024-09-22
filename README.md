# Configurando o projeto do jogo no Eclipse

Bem-vindo a este guia sobre como configurar o jogo Timeline no Eclipse. Siga os passos abaixo para configurar seu ambiente para desenvolvimento em JavaFX.

## Pré-requisitos

- Eclipse IDE
- Java Development Kit (JDK)

## Guia Passo a Passo

### 1. Instalar EFX extension

1. Abra o Eclipse.
2. Navegue até `Ajuda` > `Marketplace do Eclipse`.
3. Procure por **EFX** e clique em `Instalar`.  
   *Nota: Se você já tiver instalado, pode pular esta etapa.*

### 2. Baixar o SDK do JavaFX

1. Acesse a [Página de Download do JavaFX](https://openjfx.io/).
2. Clique em `Downloads` e selecione o SDK apropriado para o seu sistema operacional.
3. Baixe o SDK (ignore as opções JMOD ou Monocle).

### 3. Configurar Biblioteca de Usuário

1. Vá para `Janela` > `Preferências`.
2. Na janela de preferências, navegue até `Java` > `build path` > `Bibliotecas de Usuário`.
3. Clique em `Novo` para criar uma nova biblioteca e nomeie-a como `JavaFX`.
4. Selecione `Adicionar JARs Externos` e navegue até a pasta `lib` do SDK baixado.
5. Selecione todos os arquivos JAR e clique em `Abrir`.
6. Clique em `Aplicar e Fechar` para salvar a configuração da biblioteca.

### 4. Criar um Novo Projeto JavaFX

1. Vá para `Arquivo` > `Novo` > `Outro`.
2. Em `JavaFX`, selecione `Projeto JavaFX` e clique em `Avançar`.
3. Insira um nome para o seu projeto e clique em `Concluir`.

### 5. Resolver Erros

1. Localize o arquivo `module-info.java` em seu projeto e exclua-o.
2. Clique com o botão direito no projeto, selecione `Caminho de Construção` > `Configurar Caminho de Construção`.
3. Na aba `Bibliotecas`, verifique se você está na `Classpath`:
   - Clique em `Adicionar Biblioteca`, selecione `Biblioteca de Usuário` e escolha `JavaFX`.
4. Clique em `Concluir` e depois em `Aplicar`.

### 6. Configurar Execução

1. Clique com o botão direito no projeto e selecione `Configurações de Execução`.
2. Vá para a aba `Argumentos` e adicione os seguintes argumentos de VM: --module-path <caminho_para_suas_bibliotecas_javafx> --add-modules javafx.controls,javafx.media,javafx.fxml,

*Substitua `<caminho_para_suas_bibliotecas_javafx>` pelo caminho real para suas bibliotecas JavaFX.*
3. Vá para a aba `Dependências`:
- Remova o JavaFX da `Classpath`.
- Clique em `Entradas do Módulo`, depois em `Avançado`, e adicione a biblioteca `JavaFX`.
4. Clique em `Aplicar`, depois em `Executar`.

### Conclusão

Agora você deve ser capaz de executar seu projeto Timeline em JavaFX com sucesso. Se encontrar algum problema, verifique novamente os passos acima.
