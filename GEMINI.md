# Diretrizes para Agentes IA - Ecos do Vazio

Este repositório contém o projeto do aplicativo Android **Ecos do Vazio**, desenvolvido em Kotlin utilizando Jetpack Compose, Material 3 Expressive, Hilt e arquitetura reativa com Coroutines e Flow.

---

## Mapeamento de Documentação de Arquitetura e Padrões (`GEMINI.md`)

Para garantir consistência técnica e conformidade durante a criação e manutenção de código por agentes IA, consulte os arquivos `GEMINI.md` localizados nos respectivos módulos e pacotes do projeto:

- [Padrões da Camada de Presentation](app/src/main/java/br/com/schmittsolucoes/ecosdovazio/presentation/GEMINI.md)
  - Organização e divisão de pacotes por conceitos (`composables/`, `components/`, `model/`, `navigation/`).
  - Padrão de telas **Stateful** e **Stateless** com subdivisão em composables menores.
  - Obrigatoriedade de **Previews** em Light Mode e Dark Mode alimentados por `PreviewData`.
  - Convenções de **ViewModel** estendendo `CommonViewModel`, utilizando `InternalState`, `NavigationEvent`, `ObserveAsEvents` e `stateInWithCommonError`.
  - Padrões visuais com **Material 3 Expressive** e gerenciamento de cores via `Color.kt` e `Theme.kt`.
  - Estrutura de **Navegação** fortemente tipada com `Route`, extensores de `NavGraphBuilder`, `NavController` e integração no `AppNavHost.kt`.

---

## Regras Gerais para Agentes em Execuções Futuras

1. **Localização de Padrões**: Antes de realizar alterações em um pacote ou criar novas telas/componentes, navegue até a documentação `GEMINI.md` correspondente àquela camada para seguir rigorosamente os padrões do projeto.
2. **Reuso de Padrões de Estado**: Mantenha a consistência de gerenciamento de estado e navegação sem redefinir abordagens fora das já estabelecidas.
3. **Manutenção dos Previews**: Ao implementar ou alterar qualquer componente visual, garanta a criação/atualização dos previews para temas claro e escuro.
