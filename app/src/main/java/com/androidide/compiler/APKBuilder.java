package com.androidide.compiler;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class APKBuilder {

    private static final String TAG = "APKBuilder";
    private File projectRoot;
    private BuildCallback callback;

    public interface BuildCallback {
        void onProgress(String message);
        void onSuccess(File apkFile);
        void onError(String error);
    }

    public APKBuilder(File projectRoot) {
        this.projectRoot = projectRoot;
    }

    public void setCallback(BuildCallback callback) {
        this.callback = callback;
    }

    public void build(File dexFile, File resourcesFile, File manifestFile) {
        new Thread(() -> {
            try {
                if (callback != null) {
                    callback.onProgress("Construindo APK...");
                }

                File outputDir = new File(projectRoot, "build/output");
                outputDir.mkdirs();

                File apkFile = new File(outputDir, "app-release.apk");

                // Create APK as ZIP file
                createAPK(apkFile, dexFile, resourcesFile, manifestFile);

                if (callback != null) {
                    callback.onSuccess(apkFile);
                }

            } catch (Exception e) {
                Log.e(TAG, "APK build error", e);
                if (callback != null) {
                    callback.onError("Erro ao construir APK: " + e.getMessage());
                }
            }
        }).start();
    }

    private void createAPK(File apkFile, File dexFile, File resourcesFile, File manifestFile) throws Exception {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(apkFile));

        // Add DEX file
        if (dexFile != null && dexFile.exists()) {
            addFileToZip(zos, dexFile, "classes.dex");
        }

        // Add resources
        if (resourcesFile != null && resourcesFile.exists()) {
            addFileToZip(zos, resourcesFile, "resources.arsc");
        }

        // Add manifest
        if (manifestFile != null && manifestFile.exists()) {
            addFileToZip(zos, manifestFile, "AndroidManifest.xml");
        }

        zos.close();
        Log.d(TAG, "APK created: " + apkFile.getAbsolutePath());
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
