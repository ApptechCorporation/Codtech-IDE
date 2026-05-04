package com.androidide.editor;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import com.androidide.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxHighlighter {

    private Context context;
    private static final String[] JAVA_KEYWORDS = {
        "public", "private", "protected", "static", "final", "abstract", "class", "interface",
        "extends", "implements", "new", "return", "if", "else", "for", "while", "do", "switch",
        "case", "default", "break", "continue", "try", "catch", "finally", "throw", "throws",
        "import", "package", "void", "int", "long", "float", "double", "boolean", "char",
        "String", "true", "false", "null", "this", "super", "instanceof"
    };

    private static final String[] XML_KEYWORDS = {
        "xmlns", "android", "layout_width", "layout_height", "text", "id", "orientation",
        "gravity", "padding", "margin", "background", "textColor", "textSize"
    };

    public SyntaxHighlighter(Context context) {
        this.context = context;
    }

    public void highlight(Editable editable) {
        // Remove existing spans
        ForegroundColorSpan[] spans = editable.getSpans(0, editable.length(), ForegroundColorSpan.class);
        for (ForegroundColorSpan span : spans) {
            editable.removeSpan(span);
        }

        // Highlight strings
        highlightPattern(editable, "\"[^\"]*\"", R.color.editor_string);
        highlightPattern(editable, "'[^']*'", R.color.editor_string);

        // Highlight comments
        highlightPattern(editable, "//.*", R.color.editor_comment);
        highlightPattern(editable, "/\\*.*?\\*/", R.color.editor_comment);

        // Highlight numbers
        highlightPattern(editable, "\\b\\d+\\b", R.color.editor_number);

        // Highlight Java keywords
        for (String keyword : JAVA_KEYWORDS) {
            highlightPattern(editable, "\\b" + keyword + "\\b", R.color.editor_keyword);
        }

        // Highlight XML tags
        highlightPattern(editable, "<[^>]+>", R.color.editor_keyword);
    }

    private void highlightPattern(Editable editable, String pattern, int colorResId) {
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(editable.toString());
            int color = context.getColor(colorResId);

            while (m.find()) {
                editable.setSpan(
                    new ForegroundColorSpan(color),
                    m.start(),
                    m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
