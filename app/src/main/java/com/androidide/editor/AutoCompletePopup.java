package com.androidide.editor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.PopupWindow;
import com.androidide.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoCompletePopup {

    private Context context;
    private CodeEditor codeEditor;
    private PopupWindow popupWindow;
    private AutoCompleteTextView autoCompleteView;
    private List<String> suggestions;

    private static final String[] JAVA_KEYWORDS = {
        "public", "private", "protected", "static", "final", "abstract", "class", "interface",
        "extends", "implements", "new", "return", "if", "else", "for", "while", "do", "switch",
        "case", "default", "break", "continue", "try", "catch", "finally", "throw", "throws",
        "import", "package", "void", "int", "long", "float", "double", "boolean", "char",
        "String", "true", "false", "null", "this", "super", "instanceof", "synchronized",
        "volatile", "transient", "native", "strictfp", "enum", "assert"
    };

    private static final String[] ANDROID_CLASSES = {
        "Activity", "Fragment", "Service", "BroadcastReceiver", "ContentProvider",
        "Intent", "Bundle", "Context", "View", "ViewGroup", "LinearLayout", "FrameLayout",
        "RelativeLayout", "GridLayout", "TextView", "Button", "EditText", "ImageView",
        "ListView", "RecyclerView", "Toolbar", "ActionBar", "Menu", "MenuItem",
        "SharedPreferences", "SQLiteDatabase", "Cursor", "ContentResolver", "Uri",
        "Toast", "Dialog", "AlertDialog", "ProgressDialog", "DatePickerDialog",
        "TimePickerDialog", "Notification", "NotificationManager", "PendingIntent"
    };

    public AutoCompletePopup(Context context, CodeEditor editor) {
        this.context = context;
        this.codeEditor = editor;
        this.suggestions = new ArrayList<>();
        initSuggestions();
        setupAutoComplete();
    }

    private void initSuggestions() {
        suggestions.addAll(Arrays.asList(JAVA_KEYWORDS));
        suggestions.addAll(Arrays.asList(ANDROID_CLASSES));
    }

    private void setupAutoComplete() {
        autoCompleteView = new AutoCompleteTextView(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            context,
            android.R.layout.simple_dropdown_item_1line,
            suggestions
        );
        autoCompleteView.setAdapter(adapter);
        autoCompleteView.setThreshold(1);

        popupWindow = new PopupWindow(
            autoCompleteView,
            300,
            PopupWindow.WRAP_CONTENT,
            true
        );
        popupWindow.setBackgroundDrawable(context.getDrawable(android.R.color.darker_gray));

        codeEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    String currentWord = getCurrentWord(s, start + count);
                    if (currentWord.length() > 0) {
                        showAutoComplete(currentWord);
                    } else {
                        hideAutoComplete();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private String getCurrentWord(CharSequence text, int position) {
        int start = position - 1;
        while (start >= 0 && Character.isJavaIdentifierPart(text.charAt(start))) {
            start--;
        }
        return text.subSequence(start + 1, position).toString();
    }

    private void showAutoComplete(String prefix) {
        List<String> filtered = new ArrayList<>();
        for (String suggestion : suggestions) {
            if (suggestion.toLowerCase().startsWith(prefix.toLowerCase())) {
                filtered.add(suggestion);
            }
        }

        if (!filtered.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_dropdown_item_1line,
                filtered
            );
            autoCompleteView.setAdapter(adapter);
            
            if (!popupWindow.isShowing()) {
                popupWindow.showAsDropDown(codeEditor);
            }
        } else {
            hideAutoComplete();
        }
    }

    private void hideAutoComplete() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}
