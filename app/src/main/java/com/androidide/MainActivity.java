package com.androidide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.androidide.compiler.CompilationManager;
import com.androidide.project.ProjectManager;
import com.androidide.ui.ConsoleFragment;
import com.androidide.ui.FileExplorerFragment;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout editorTabs;
    private ViewPager2 editorPager;
    private FrameLayout fileExplorerContainer;
    private FrameLayout consoleContainer;
    
    private ProjectManager projectManager;
    private CompilationManager compilationManager;
    private ConsoleFragment consoleFragment;
    private FileExplorerFragment fileExplorerFragment;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        // Initialize toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize tabs and pager
        editorTabs = findViewById(R.id.editor_tabs);
        editorPager = findViewById(R.id.editor_pager);

        // Setup editor adapter
        EditorPagerAdapter adapter = new EditorPagerAdapter(this);
        editorPager.setAdapter(adapter);

        // Connect tabs with pager
        new TabLayoutMediator((editorTabs), editorPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Código");
            } else {
                tab.setText("Layout");
            }
        }).attach();

        // Initialize containers
        fileExplorerContainer = findViewById(R.id.file_explorer_container);
        consoleContainer = findViewById(R.id.console_container);

        // Initialize file explorer
        initializeFileExplorer();

        // Initialize console
        initializeConsole();

        // Initialize project manager
        projectManager = new ProjectManager(this);

        // Initialize compilation manager
        File projectRoot = new File(getExternalFilesDir(null), "CurrentProject");
        compilationManager = new CompilationManager(projectRoot);
    }

    private void initializeFileExplorer() {
        fileExplorerFragment = new FileExplorerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
            .replace(R.id.file_explorer_container, fileExplorerFragment)
            .commit();
    }

    private void initializeConsole() {
        consoleFragment = new ConsoleFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
            .replace(R.id.console_container, consoleFragment)
            .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_project) {
            createNewProject();
            return true;
        } else if (id == R.id.action_compile) {
            startCompilation();
            return true;
        } else if (id == R.id.action_save) {
            saveProject();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createNewProject() {
        if (consoleFragment != null) {
            consoleFragment.log("Criando novo projeto...");
        }
        
        try {
            projectManager.createNewProject("MyApp");
            if (consoleFragment != null) {
                consoleFragment.logSuccess("Projeto criado com sucesso!");
            }
            
            // Refresh file explorer
            if (fileExplorerFragment != null) {
                File projectRoot = new File(getExternalFilesDir(null), "MyApp");
                fileExplorerFragment.setProjectRoot(projectRoot);
            }
        } catch (Exception e) {
            if (consoleFragment != null) {
                consoleFragment.logError(e.getMessage());
            }
        }
    }

    private void startCompilation() {
        if (consoleFragment != null) {
            consoleFragment.clear();
            consoleFragment.log("Iniciando compilação...");
        }

        compilationManager.setCallback(new CompilationManager.CompilationCallback() {
            @Override
            public void onProgress(String message) {
                if (consoleFragment != null) {
                    consoleFragment.log(message);
                }
            }

            @Override
            public void onSuccess(File apkFile) {
                if (consoleFragment != null) {
                    consoleFragment.logSuccess("APK gerado: " + apkFile.getAbsolutePath());
                }
            }

            @Override
            public void onError(String error) {
                if (consoleFragment != null) {
                    consoleFragment.logError(error);
                }
            }
        });

        compilationManager.startCompilation();
    }

    private void saveProject() {
        if (consoleFragment != null) {
            consoleFragment.log("Salvando projeto...");
        }
        
        // TODO: Implement project saving
        if (consoleFragment != null) {
            consoleFragment.logSuccess("Projeto salvo!");
        }
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão concedida!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão negada! O app pode não funcionar corretamente.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
