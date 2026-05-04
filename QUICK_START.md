# Android IDE Light - Guia Rápido de Instalação

## 🚀 Como Compilar e Instalar em 5 Minutos

### Passo 1: Extrair o ZIP
1. Baixe o arquivo `AndroidIDELight.zip`
2. Extraia em qualquer pasta no seu computador
3. Abra a pasta `AndroidIDELight`

### Passo 2: Abrir no Android Studio
1. Abra o **Android Studio**
2. Clique em **"File"** → **"Open"**
3. Selecione a pasta `AndroidIDELight` extraída
4. Aguarde a sincronização do Gradle (2-5 minutos)

### Passo 3: Compilar o APK
1. Clique em **"Build"** no menu superior
2. Selecione **"Build Bundle(s)/APK(s)"** → **"Build APK(s)"**
3. Aguarde a compilação (3-5 minutos)
4. Uma notificação aparecerá: "Build Successful"

### Passo 4: Instalar no Dispositivo
**Opção A: Via Android Studio (Mais Fácil)**
1. Conecte seu dispositivo Android via USB
2. Habilite "USB Debugging" nas opções de desenvolvedor
3. Clique em **"Run"** (Shift+F10) ou ▶️ verde
4. Selecione seu dispositivo
5. O app será instalado automaticamente

**Opção B: Via ADB (Terminal)**
```bash
# Navegar até a pasta do projeto
cd ~/Downloads/AndroidIDELight

# Instalar o APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Abrir o app
adb shell am start -n com.androidide/.MainActivity
```

---

## ✅ Pronto!

Seu Android IDE Light está instalado e pronto para usar!

### Primeiro Uso:
1. Abra o app no seu dispositivo
2. Clique no menu (⋮) → **"Novo Projeto"**
3. Comece a editar código Java e XML
4. Clique em **"Compilar"** para gerar APK

---

## 📋 Pré-requisitos

- **Android Studio** (gratuito): [Download](https://developer.android.com/studio)
- **Android SDK 24+** (instalado automaticamente pelo Android Studio)
- **Java 11+** (instalado automaticamente pelo Android Studio)
- **USB Debugging habilitado** no seu dispositivo

---

## 🆘 Problemas Comuns

### "Gradle sync failed"
- Clique em **"File"** → **"Sync Now"**
- Ou **"File"** → **"Invalidate Caches"** → **"Invalidate and Restart"**

### "Device not found"
- Certifique-se de que USB Debugging está habilitado
- Reconecte o dispositivo
- Execute: `adb devices`

### "Build failed"
- Clique em **"Build"** → **"Clean Project"**
- Depois **"Build"** → **"Build APK(s)"** novamente

---

## 📱 Requisitos do Dispositivo

- **Android**: 7.0+ (API 24)
- **RAM**: 2GB mínimo
- **Espaço**: 100MB para IDE + 500MB para projetos
- **Processador**: ARM64

---

## 📚 Documentação Completa

Veja os arquivos de documentação inclusos:
- `ANDROID_IDE_LIGHT_GUIDE.md` - Guia completo
- `ANDROID_IDE_LIGHT_FINAL_SUMMARY.md` - Sumário detalhado
- `ANDROID_IDE_LIGHT_COMPONENTS.md` - Descrição técnica

---

**Criado em**: 2026-05-04
**Versão**: 1.0.0
