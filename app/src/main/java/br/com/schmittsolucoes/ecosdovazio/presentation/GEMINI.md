# Padrões de Desenvolvimento - Camada de Presentation

Este documento define os padrões técnicos para a criação e manutenção de telas, componentes, ViewModels e navegação na camada de `presentation` do aplicativo **Ecos do Vazio**.

---

## 1. Organização dos Composables e Pacotes

Dentro do pacote `br.com.schmittsolucoes.ecosdovazio.presentation`, são criados pacotes específicos para dividir os conceitos e funcionalidades do aplicativo (ex.: `chars/selection`, `home`, `classes/selection`).

Estrutura interna padrão de um pacote de conceito:

```text
presentation/<conceito>/
├── <Conceito>UIState.kt
├── <Conceito>ViewModel.kt
├── composables/
│   ├── <Conceito>Screen.kt
│   ├── <Conceito>PreviewData.kt
│   └── components/
│       └── <ComponenteEspecifico>.kt
├── model/
│   └── <Conceito>UIModel.kt
└── navigation/
    ├── <Conceito>Route.kt
    └── <Conceito>Navigation.kt
```

### Divisão Lógica dos Pacotes
- **`composables/`**: Armazena a tela principal (`Screen`) e os composables específicos do conceito.
- **`composables/components/`**: Subdivisão interna de `composables` para agrupar componentes visuais reutilizáveis exclusivos daquela funcionalidade.
- **`model/`**: Contém classes com sufixo `UIModel` (ex.: `CharSelectionUIModel`) que atuam como objetos de tela adaptados para simplificar a exibição dos dados na UI.
- **`navigation/`**: Centraliza tudo referente à navegação do conceito (`Route`, extensões do `NavGraphBuilder` e do `NavController`).

---

## 2. Estruturação da Tela (Stateless / Stateful e Previews)

### Padrão Stateful e Stateless
Toda tela é composta por um arquivo com o sufixo `Screen` (ex.: `CharSelectionScreen.kt`) contendo duas funções composable sobrecarregadas:

1. **Stateful (`<Conceito>Screen`)**:
   - Recebe o `ViewModel`, a classe `WindowSizeClass` e callbacks de navegação global.
   - Coleta o estado do ViewModel via `collectAsStateWithLifecycle()`.
   - Escuta e reage a eventos de navegação disparados via `@Composable ObserveAsEvents(viewModel.navigationEvent)`.
   - Repassa os dados coletados para a função Stateless.

2. **Stateless (`<Conceito>Screen`)**:
   - Recebe diretamente o estado da UI (`UIState`), preferências de layout e callbacks de ação (ex.: `onCharSelected = {}`).
   - Não possui acoplamento com o `ViewModel` ou Hilt, facilitando a reutilização, testes e testes de layout/preview.
   - É decomposta em composables menores com o objetivo de manter a leitura limpa e de fácil manutenção.

### Previews e PreviewData
- **Previews Obrigatórios**: Como a aplicação suporta Tema Claro e Tema Escuro, todo componente e tela deve ter obrigatoriamente Previews declarados para os dois cenários:
  ```kotlin
  @Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
  @Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
  ```
- **Previews Multi-Dispositivo para Telas (`Screen`)**: Em composables de tela (`<Conceito>Screen.kt`), é **obrigatório** declarar previews especificando diferentes dispositivos e form factors (ex.: `device = Devices.PHONE`, `device = Devices.FOLDABLE`, `device = Devices.TABLET`).
  - **Importância**: O aplicativo adota layout adaptativo via `WindowSizeClass`. Criar previews com diferentes dispositivos permite validar visualmente a resposta da UI em múltiplos tamanhos de tela (mudança no número de colunas do grid, adaptação do layout, distribuição de espaço e usabilidade em smartphones, dobráveis e tablets) diretamente no Compose Preview do Android Studio, eliminando a necessidade de rodar emuladores para cada tamanho de dispositivo.
  - **Cálculo de `WindowSizeClass` nos Previews**: Utilize `@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)` e calcule a `WindowSizeClass` dinamicamente com base nas dimensões do container para repassar à tela:
  ```kotlin
  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  @Preview(
      name = "Phone - Compact (2 Columns) - Light",
      device = Devices.PHONE,
      uiMode = UI_MODE_NIGHT_NO,
      showBackground = true,
  )
  @Preview(
      name = "Phone - Compact (2 Columns) - Dark",
      device = Devices.PHONE,
      uiMode = UI_MODE_NIGHT_YES,
      showBackground = true,
  )
  @Composable
  private fun CharSkillsScreenPreviewPhone() {
      val containerSize = LocalWindowInfo.current.containerSize
      val windowSizeClass = WindowSizeClass.calculateFromSize(
          DpSize(containerSize.width.dp, containerSize.height.dp),
      )

      EcosDoVazioTheme {
          CharSkillsScreen(
              state = CharSkillsPreviewData.uiStateLoaded,
              windowSizeClass = windowSizeClass,
          )
      }
  }
  ```
- **Dados Realistas**: Os dados passados nos previews devem refletir cenários o mais reais possível (incluindo estados de sucesso, seleção, carregamento e erro).
- **`PreviewData`**: Crie objetos/arquivos com o sufixo `PreviewData` (ex.: `CharSkillsPreviewData.kt`) para centralizar as instâncias de `UIState` e objetos simulados utilizados nos Previews.

---

## 3. Estrutura do ViewModel das Telas

Os ViewModels das telas herdam de `CommonViewModel` (`br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel`) e seguem as seguintes convenções:

### Injeção de Dependência
- Anotados com `@HiltViewModel` e com construtor marcado com `@Inject`.
- Quando houver necessidade de mensagens de erro formatadas/traduzidas pelo ViewModel, injetar o `Context` utilizando a anotação `@ApplicationContext`.

### Gerenciamento de Estado Mutável Interno (`InternalState`)
- Para evitar a proliferação de múltiplos `MutableStateFlow`s privados, utiliza-se uma `data class` privada com sufixo `InternalState` (ex.: `CharSelectionInternalState`).
- Mantém-se apenas um `MutableStateFlow` encapsulando esse estado interno.

### Eventos de Navegação (`NavigationEvent`)
- Eventos de navegação de disparo único são representados por uma `sealed interface` ou `sealed class` com sufixo `NavigationEvent` (ex.: `CharSelectionNavigationEvent`).
- Disparados por meio de um `Channel` privado em formato de `Flow` exposto e consumidos na UI através de `ObserveAsEvents.kt` (`br.com.schmittsolucoes.ecosdovazio.presentation.components.ObserveAsEvents`).

### Tratamento de Erros e Corrotinas
- **Tratamento de Exceções dos UseCases**: O ViewModel deve estender o método `getErrorMessageFrom(throwable: Throwable): String` para tratar e mapear exceções específicas lançadas pelos UseCases (ex.: `UserException`).
- **Execução Suspensa (`launch`)**: Qualquer operação suspensa no ViewModel deve utilizar o método `launch { ... }` disponibilizado por `CommonViewModel` para garantir que exceções não tratadas sejam capturadas centraladamente.
- **Consumo de Flows e Reatividade (`stateInWithCommonError`)**: Ao redefinir ou combinar `Flow`s (via `combine`) para expor o `uiState`, deve-se utilizar a extensão `stateInWithCommonError(initialValue = ...)` de `CommonViewModel`. Ela assegura as configurações padrão de parada da observabilidade (`SharingStarted.WhileSubscribed(5000L)`) e tratamento unificado de erros no fluxo.

---

## 4. Tema e Cores

### Definição do Tema
- **`Color.kt`**: Centraliza a paleta de cores do aplicativo e disponibiliza getters composable que alternam os valores dinamicamente entre tema escuro e claro baseado em `isSystemInDarkTheme()`.
- **`Theme.kt`**: Mapeia o esquema de cores (`darkColorScheme` / `lightColorScheme`) no `MaterialTheme`.

### Diretrizes do Material 3 Expressive
- A aplicação adota o padrão **Material 3 Expressive**.
- **Cores Padrão**: Deve-se dar preferência ao uso das cores padrão nativas dos componentes do Material 3.
- **Customizações**: Evitar customizar individualmente as cores dos componentes nos composables. Quando for necessário ajustar a identidade visual, altere ou adicione a definição global do tema nos arquivos `@Color.kt` e `@Theme.kt`.

---

## 5. Navegação

A navegação segue o Jetpack Compose Navigation fortemente tipado:

1. **Route (`<Conceito>Route.kt`)**: Cada tela precisa de um objeto ou classe serializável (`@Serializable`) com sufixo `Route` que representa a rota de navegação.
2. **Extensão `NavGraphBuilder` (`<Conceito>Navigation.kt`)**: Toda tela possui uma função de extensão para `NavGraphBuilder` (ex.: `fun NavGraphBuilder.charSelectionScreen(...)`). Ela define a composable rota e obtém a instância do ViewModel via Hilt (`hiltViewModel()`).
3. **Extensão `NavController` (`<Conceito>Navigation.kt`)**: Toda tela deve fornecer uma função de extensão de navegação no `NavController` (ex.: `fun NavController.navigateToCharSelection(navOptions: NavOptions? = null)`) para realizar a transição permitindo repassar `NavOptions` opcionalmente.
4. **`AppNavHost.kt`**: Registra e encadeia todas as telas e grafos de navegação da aplicação.
