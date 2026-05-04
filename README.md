# Android IDE Light

Uma IDE Android leve e funcional para editar, compilar e gerar APKs de projetos Android simples diretamente no dispositivo.

## Características

- 📝 **Editor de Código** com destaque de sintaxe para Java e XML
- 🔨 **Compilação Real** (Java → DEX → APK)
- 📱 **Preview de Layout** XML em tempo real
- 📁 **Gerenciador de Arquivos** integrado
- 🎨 **Interface Escura** otimizada para desenvolvimento
- ⚡ **Performance** otimizada para dispositivos com 2GB RAM

## Estrutura do Projeto

```
AndroidIDELight/
├── app/
│   ├── src/main/
│   │   ├── java/com/androidide/
│   │   │   ├── MainActivity.java
│   │   │   ├── EditorPagerAdapter.java
│   │   │   ├── editor/
│   │   │   │   ├── CodeEditor.java
│   │   │   │   └── SyntaxHighlighter.java
│   │   │   ├── compiler/
│   │   │   │   ├── JavaCompiler.java
│   │   │   │   ├── DexCompiler.java
│   │   │   │   └── APKBuilder.java
│   │   │   ├── project/
│   │   │   │   └── ProjectManager.java
│   │   │   └── ui/
│   │   │       ├── CodeEditorFragment.java
│   │   │       └── LayoutPreviewFragment.java
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   └── drawable/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Compilação

### Pré-requisitos

- Android SDK (API 24+)
- Android Build Tools (34.0.0+)
- Java Development Kit (JDK 11+)
- Gradle 8.0+

### Compilar o APK

```bash
cd AndroidIDELight
./gradlew build
```

### Instalar no Dispositivo

```bash
./gradlew installDebug
```

## Funcionalidades Implementadas

### ✅ Editor de Código
- Destaque de sintaxe para Java e XML
- Numeração de linhas
- Suporte a múltiplos arquivos
- Auto-complete básico

### ✅ Compilação
- Compilador Java (ECJ)
- Compilador DEX (d8)
- Builder de APK
- Logs de compilação em tempo real

### ✅ Interface
- Layout com editor central
- Explorador de arquivos lateral
- Console inferior para logs
- Tema escuro Material Design

### ⏳ Em Desenvolvimento
- Preview de layout XML completo
- Gerenciador de projetos avançado
- Suporte a bibliotecas externas

## Uso

1. **Criar Novo Projeto**: Toque em "Novo Projeto" para criar um projeto Android padrão
2. **Editar Código**: Use o editor para modificar arquivos Java e XML
3. **Compilar**: Toque em "Compilar" para gerar o APK
4. **Executar**: Instale e execute o APK gerado no dispositivo

## Limitações

- Não suporta Kotlin (apenas Java)
- Preview XML é simplificado
- Sem suporte completo a Gradle
- Compilação pode ser lenta em dispositivos fracos

## Requisitos de Performance

- Mínimo 2GB RAM
- Processador ARM64
- Android 7.0+ (API 24+)

## Licença

MIT License

## Autor

Android IDE Light - 2026
