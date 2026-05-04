package com.androidide.compiler;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ResourceCompiler {

    private static final String TAG = "ResourceCompiler";
    private File projectRoot;
    private CompileCallback callback;

    public interface CompileCallback {
        void onProgress(String message);
        void onSuccess(File resourcesFile);
        void onError(String error);
    }

    public ResourceCompiler(File projectRoot) {
        this.projectRoot = projectRoot;
    }

    public void setCallback(CompileCallback callback) {
        this.callback = callback;
    }

    public void compile() {
        new Thread(() -> {
            try {
                if (callback != null) {
                    callback.onProgress("Compilando recursos...");
                }

                // Create output directory
                File outputDir = new File(projectRoot, "build/resources");
                outputDir.mkdirs();

                File resourcesFile = new File(outputDir, "resources.arsc");

                // Collect resources
                List<File> layoutFiles = findLayoutFiles(new File(projectRoot, "src/main/res/layout"));
                List<File> valueFiles = findValueFiles(new File(projectRoot, "src/main/res/values"));

                // Create resources archive
                createResourcesArchive(resourcesFile, layoutFiles, valueFiles);

                if (callback != null) {
                    callback.onSuccess(resourcesFile);
                }

            } catch (Exception e) {
                Log.e(TAG, "Resource compilation error", e);
                if (callback != null) {
                    callback.onError("Erro na compilação de recursos: " + e.getMessage());
                }
            }
        }).start();
    }

    private List<File> findLayoutFiles(File dir) {
        List<File> files = new ArrayList<>();
        if (dir.isDirectory()) {
            File[] listFiles = dir.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.getName().endsWith(".xml")) {
                        files.add(file);
                    }
                }
            }
        }
        return files;
    }

    private List<File> findValueFiles(File dir) {
        List<File> files = new ArrayList<>();
        if (dir.isDirectory()) {
            File[] listFiles = dir.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.getName().endsWith(".xml")) {
                        files.add(file);
                    }
                }
            }
        }
        return files;
    }

    private void createResourcesArchive(File resourcesFile, List<File> layoutFiles, List<File> valueFiles) throws Exception {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(resourcesFile));

        // Add layout files
        for (File layoutFile : layoutFiles) {
            addFileToZip(zos, layoutFile, "layout/" + layoutFile.getName());
        }

        // Add value files
        for (File valueFile : valueFiles) {
            addFileToZip(zos, valueFile, "values/" + valueFile.getName());
        }

        zos.close();
        Log.d(TAG, "Resources archive created: " + resourcesFile.getAbsolutePath());
    }

    private void addFileToZip(ZipOutputStream zos, File file, String zipPath) throws Exception {
        ZipEntry entry = new ZipEntry(zipPath);
        zos.putNextEntry(entry);

        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, length);
        }
        fis.close();
        zos.closeEntry();
    }
}
