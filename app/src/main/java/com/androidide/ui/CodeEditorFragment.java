package com.androidide.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.androidide.editor.CodeEditorWithLineNumbers;

public class CodeEditorFragment extends Fragment {

    private CodeEditorWithLineNumbers editorWithLineNumbers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create editor with line numbers
        editorWithLineNumbers = new CodeEditorWithLineNumbers(requireContext());
        
        // Set default code
        String defaultCode = "public class MainActivity extends AppCompatActivity {\n" +
                "    @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_main);\n" +
                "    }\n" +
                "}\n";
        editorWithLineNumbers.setCode(defaultCode);
        
        return editorWithLineNumbers;
    }

    public String getCode() {
        if (editorWithLineNumbers != null) {
            return editorWithLineNumbers.getCode();
        }
        return "";
    }

    public void setCode(String code) {
        if (editorWithLineNumbers != null) {
            editorWithLineNumbers.setCode(code);
        }
    }
}
