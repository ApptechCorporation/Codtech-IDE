package com.androidide.editor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatEditText;
import com.androidide.R;

public class CodeEditorWithLineNumbers extends LinearLayout {

    private LineNumberView lineNumberView;
    private CodeEditor codeEditor;
    private AutoCompletePopup autoCompletePopup;

    public CodeEditorWithLineNumbers(Context context) {
        super(context);
        init();
    }

    public CodeEditorWithLineNumbers(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CodeEditorWithLineNumbers(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setBackgroundColor(getContext().getColor(R.color.editor_background));

        // Create line number view
        lineNumberView = new LineNumberView(getContext());
        LayoutParams lineNumberParams = new LayoutParams(60, LayoutParams.MATCH_PARENT);
        addView(lineNumberView, lineNumberParams);

        // Create code editor
        codeEditor = new CodeEditor(getContext());
        LayoutParams editorParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        addView(codeEditor, editorParams);

        // Connect line numbers to editor
        lineNumberView.setCodeEditor(codeEditor);

        // Initialize auto-complete
        autoCompletePopup = new AutoCompletePopup(getContext(), codeEditor);
    }

    public String getCode() {
        return codeEditor.getCode();
    }

    public void setCode(String code) {
        codeEditor.setCode(code);
    }

    public CodeEditor getCodeEditor() {
        return codeEditor;
    }
}
