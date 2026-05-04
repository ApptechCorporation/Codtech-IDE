package com.androidide.project;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProjectManager {

    private Context context;
    private File projectRoot;

    public ProjectManager(Context context) {
        this.context = context;
    }

    public void createNewProject(String projectName) throws IOException {
        projectRoot = new File(context.getExternalFilesDir(null), projectName);
        projectRoot.mkdirs();

        // Create directory structure
        new File(projectRoot, "src/main/java/com/example").mkdirs();
        new File(projectRoot, "src/main/res/layout").mkdirs();
        new File(projectRoot, "src/main/res/values").mkdirs();

        // Create default files
        createDefaultMainActivity();
        createDefaultManifest();
        createDefaultLayout();
    }

    private void createDefaultMainActivity() throws IOException {
        String mainActivityCode = "package com.example;\n\n" +
                "import android.app.Activity;\n" +
                "import android.os.Bundle;\n\n" +
                "public class MainActivity extends Activity {\n" +
                "    @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_main);\n" +
                "    }\n" +
                "}\n";

        File mainActivityFile = new File(projectRoot, "src/main/java/com/example/MainActivity.java");
        Files.write(Paths.get(mainActivityFile.getAbsolutePath()), mainActivityCode.getBytes());
    }

    private void createDefaultManifest() throws IOException {
        String manifest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    package=\"com.example\">\n\n" +
                "    <application>\n" +
                "        <activity android:name=\".MainActivity\"\n" +
                "            android:exported=\"true\">\n" +
                "            <intent-filter>\n" +
                "                <action android:name=\"android.intent.action.MAIN\" />\n" +
                "                <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
                "            </intent-filter>\n" +
                "        </activity>\n" +
                "    </application>\n" +
                "</manifest>\n";

        // Corrigido para a pasta src/main/ para seguir o padrão Android
        File manifestDir = new File(projectRoot, "src/main");
        if (!manifestDir.exists()) manifestDir.mkdirs();
        File manifestFile = new File(manifestDir, "AndroidManifest.xml");
        Files.write(Paths.get(manifestFile.getAbsolutePath()), manifest.getBytes());
    }

    private void createDefaultLayout() throws IOException {
        String layout = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"match_parent\"\n" +
                "    android:orientation=\"vertical\"\n" +
                "    android:gravity=\"center\">\n\n" +
                "    <TextView\n" +
                "        android:layout_width=\"wrap_content\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        android:text=\"Hello World!\" />\n\n" +
                "</LinearLayout>\n";

        File layoutFile = new File(projectRoot, "src/main/res/layout/activity_main.xml");
        Files.write(Paths.get(layoutFile.getAbsolutePath()), layout.getBytes());
    }

    public File getProjectRoot() {
        return projectRoot;
    }

    public void setProjectRoot(File root) {
        this.projectRoot = root;
    }
}
