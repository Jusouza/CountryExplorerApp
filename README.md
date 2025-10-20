# 🌍 Country Explorer

Aplicativo Android desenvolvido em **Kotlin** para explorar países do mundo consumindo a **REST Countries API**.
O projeto segue o padrão **Clean Architecture + MVVM**, com camadas bem definidas e separação clara de responsabilidades.

---

## 🧭 Visão Geral

O **Country Explorer** permite:

* Listar os **193 países** disponíveis na API;
* Buscar países pelo nome;
* Visualizar detalhes completos (bandeira, capital, população, idioma, moedas, países vizinhos);
* Navegar entre países vizinhos e marcar favoritos *(opcional)*.

---

## ⚙️ Tecnologias e Conceitos

* **Kotlin**
* **Jetpack Compose** ou **XML + ViewBinding**
* **MVVM Architecture**
* **Clean Architecture** (camadas: `data`, `domain`, `presentation`)
* **Retrofit** + **Coroutines** para consumo de API
* **ViewModel** + **StateFlow** para gerenciamento de estado
* **Material Design 3**

---

## 🏗️ Estrutura do Projeto

```
com.example.certificationdevspaceapp/
│
├── data/                          # Camada de dados
│   ├── model/                     # DTOs vindos da API
│   │   └── CountryDto.kt
│   ├── network/                   # Comunicação com a API
│   │   ├── Api.kt
│   │   ├── RetrofitClient.kt
│   │   └── Repository.kt
│   └── repository/                # Implementação do repositório
│       └── Repository.kt
│
├── domain/                        # Camada de domínio (regras de negócio)
│   ├── model/                     # Modelos de domínio + mappers
│   │   ├── CountryDomain.kt
│   │   └── CountryMapper.kt
│   └── usecase/                   # Casos de uso
│       ├── GetAllCountriesUseCase.kt
│       ├── GetCountryByCodeUseCase.kt
│       └── ViewModelFactory.kt
│
├── presentation/                  # Camada de apresentação (UI + lógica)
│   ├── adapter/
│   │   └── CountryListAdapter.kt
│   ├── data/
│   │   ├── CountryListState.kt
│   │   ├── CountryUiState.kt
│   │   └── ErrorMapper.kt
│   ├── model/
│   │   ├── CountryDetailViewModel.kt
│   │   └── CountryListViewModel.kt
│   └── ui/
│       ├── MainActivity.kt
│       ├── CountryListFragment.kt
│       ├── CountryDetailFragment.kt
│       └── FavoritesListFragment.kt
│
└── utils/
    └── FormatterUtils.kt
```

🧩 **Resumo das camadas:**

* `data`: Acesso à API, DTOs e repositórios.
* `domain`: Modelos puros e casos de uso.
* `presentation`: ViewModels, UI Fragments e adapters.
* `utils`: Funções auxiliares e formatadores.

---

## 💡 Funcionalidades

✅ **Listagem de países** com bandeira, nome e região.
🔍 **Busca por nome** com atualização dinâmica.
📄 **Tela de detalhes** com capital, população, idiomas, moedas e vizinhos.
⭐ **Favoritos** (estrutura pronta para expansão).

---

## 📦 API

* **Fonte:** [REST Countries API](https://restcountries.com/)
* **Endpoint base:** `https://restcountries.com/v3.1/`

---

## 🧠 Aprendizados Principais

Durante o desenvolvimento, foram praticados conceitos como:

* Separação em camadas com **Clean Architecture**
* Criação de **UseCases reutilizáveis**
* Consumo de APIs com **Retrofit e Coroutines**
* Estados reativos com **Flow / LiveData**
* Injeção de dependência via **ViewModelFactory**

---

## 🧪 Testes

🔸 O projeto foi estruturado para incluir testes unitários de `UseCase` e `ViewModel`,
porém ainda **não implementados nesta versão**.

---

## 🚀 Próximos Passos (Nice to Have)

* **Offline First** com Room Database
* **Filtro por região / população / idioma**
* **Testes unitários e de UI**
  
---

## 🧑‍💻 Autor

**Juliana Souza** — [@jusouza.dev](https://github.com/Jusouza)
💼 Android Developer | Kotlin | Jetpack Compose | AI

