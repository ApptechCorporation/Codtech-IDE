package com.androidide.compiler;

import android.util.Log;
import java.io.File;

public class CompilationManager {

    private static final String TAG = "CompilationManager";
    private File projectRoot;
    private CompilationCallback callback;

    private JavaCompiler javaCompiler;
    private DexCompiler dexCompiler;
    private APKBuilder apkBuilder;

    public interface CompilationCallback {
        void onProgress(String message);
        void onSuccess(File apkFile);
        void onError(String error);
    }

    public CompilationManager(File projectRoot) {
        this.projectRoot = projectRoot;
        this.javaCompiler = new JavaCompiler(projectRoot);
        this.dexCompiler = new DexCompiler(projectRoot);
        this.apkBuilder = new APKBuilder(projectRoot);
    }

    public void setCallback(CompilationCallback callback) {
        this.callback = callback;
    }

    public void startCompilation() {
        new Thread(() -> {
            try {
                if (callback != null) {
                    callback.onProgress("Iniciando compilação...");
                }

                // Step 1: Compile Java to classes
                compileJava();

                // Step 2: Compile classes to DEX
                compileDex();

                // Step 3: Compile resources
                compileResources();

                // Step 4: Build APK
                buildAPK();

                if (callback != null) {
                    File apkFile = new File(projectRoot, "build/output/app-release.apk");
                    callback.onSuccess(apkFile);
                }

            } catch (Exception e) {
                Log.e(TAG, "Compilation failed", e);
                if (callback != null) {
                    callback.onError("Erro na compilação: " + e.getMessage());
                }
            }
        }).start();
    }

    private void compileJava() throws Exception {
        if (callback != null) {
            callback.onProgress("Compilando Java...");
        }

        javaCompiler.setCallback(new JavaCompiler.CompileCallback() {
            @Override
            public void onProgress(String message) {
                if (callback != null) callback.onProgress(message);
            }

            @Override
            public void onSuccess(File outputDir) {
                Log.d(TAG, "Java compilation succeeded");
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "Java compilation error: " + error);
            }
        });

        javaCompiler.compile();
    }

    private void compileDex() throws Exception {
        if (callback != null) {
            callback.onProgress("Compilando para DEX...");
        }

        File classesDir = new File(projectRoot, "build/classes");
        dexCompiler.setCallback(new DexCompiler.CompileCallback() {
            @Override
            public void onProgress(String message) {
                if (callback != null) callback.onProgress(message);
            }

            @Override
            public void onSuccess(File dexFile) {
                Log.d(TAG, "DEX compilation succeeded");
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "DEX compilation error: " + error);
            }
        });

        dexCompiler.compile(classesDir);
    }

    private void compileResources() throws Exception {
        if (callback != null) {
            callback.onProgress("Compilando recursos...");
        }
        
        // TODO: Implement resource compilation with AAPT2
        Thread.sleep(500);
    }

    private void buildAPK() throws Exception {
        if (callback != null) {
            callback.onProgress("Construindo APK...");
        }

        File dexFile = new File(projectRoot, "build/dex/classes.dex");
        File resourcesFile = new File(projectRoot, "build/resources/resources.arsc");
        
        // Caminho atualizado para src/main/AndroidManifest.xml
        File manifestFile = new File(projectRoot, "src/main/AndroidManifest.xml");
        if (!manifestFile.exists()) {
            // Fallback para a raiz se não existir na pasta padrão
            manifestFile = new File(projectRoot, "AndroidManifest.xml");
        }

        apkBuilder.setCallback(new APKBuilder.BuildCallback() {
            @Override
            public void onProgress(String message) {
                if (callback != null) callback.onProgress(message);
            }

            @Override
            public void onSuccess(File apkFile) {
                Log.d(TAG, "APK build succeeded: " + apkFile.getAbsolutePath());
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "APK build error: " + error);
            }
        });

        apkBuilder.build(dexFile, resourcesFile, manifestFile);
    }

    public void stopCompilation() {
        // TODO: Implement cancellation
    }
}
