package com.androidide.compiler;

import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaCompiler {

    private static final String TAG = "JavaCompiler";
    private File projectRoot;
    private CompileCallback callback;

    public interface CompileCallback {
        void onProgress(String message);
        void onSuccess(File outputDir);
        void onError(String error);
    }

    public JavaCompiler(File projectRoot) {
        this.projectRoot = projectRoot;
    }

    public void setCallback(CompileCallback callback) {
        this.callback = callback;
    }

    public void compile() {
        new Thread(() -> {
            try {
                if (callback != null) {
                    callback.onProgress("Iniciando compilação Java...");
                }

                // Find all Java files
                List<File> javaFiles = findJavaFiles(new File(projectRoot, "src/main/java"));

                if (javaFiles.isEmpty()) {
                    if (callback != null) {
                        callback.onError("Nenhum arquivo Java encontrado");
                    }
                    return;
                }

                if (callback != null) {
                    callback.onProgress("Encontrados " + javaFiles.size() + " arquivo(s) Java");
                }

                // Create output directory
                File outputDir = new File(projectRoot, "build/classes");
                outputDir.mkdirs();

                // TODO: Use ECJ to compile Java files
                // For now, just simulate compilation
                simulateCompilation(javaFiles, outputDir);

                if (callback != null) {
                    callback.onSuccess(outputDir);
                }

            } catch (Exception e) {
                Log.e(TAG, "Compilation error", e);
                if (callback != null) {
                    callback.onError("Erro na compilação: " + e.getMessage());
                }
            }
        }).start();
    }

    private List<File> findJavaFiles(File dir) {
        List<File> files = new ArrayList<>();
        if (dir.isDirectory()) {
            File[] listFiles = dir.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        files.addAll(findJavaFiles(file));
                    } else if (file.getName().endsWith(".java")) {
                        files.add(file);
                    }
                }
            }
        }
        return files;
    }

    private void simulateCompilation(List<File> javaFiles, File outputDir) throws InterruptedException {
        // Simulate compilation delay
        Thread.sleep(1000);
        Log.d(TAG, "Simulated compilation of " + javaFiles.size() + " files");
    }
}
