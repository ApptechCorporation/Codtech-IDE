package com.androidide.editor;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;
import com.androidide.R;

public class CodeEditor extends AppCompatEditText {

    private SyntaxHighlighter syntaxHighlighter;
    private boolean isHighlighting = false;

    public CodeEditor(Context context) {
        super(context);
        init();
    }

    public CodeEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CodeEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        syntaxHighlighter = new SyntaxHighlighter(getContext());
        setTextColor(getContext().getColor(R.color.editor_text));
        setBackgroundColor(getContext().getColor(R.color.editor_background));
        setTypeface(android.graphics.Typeface.MONOSPACE);
        setTextSize(12);
        setPadding(16, 16, 16, 16);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!isHighlighting) {
                    isHighlighting = true;
                    syntaxHighlighter.highlight(s);
                    isHighlighting = false;
                }
            }
        });
    }

    public String getCode() {
        return getText().toString();
    }

    public void setCode(String code) {
        setText(code);
    }
}
