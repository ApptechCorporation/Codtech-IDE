package com.androidide.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.androidide.R;

public class ConsoleFragment extends Fragment {

    private TextView consoleOutput;
    private ScrollView scrollView;
    private StringBuilder logBuffer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scrollView = new ScrollView(requireContext());
        scrollView.setBackgroundColor(requireContext().getColor(R.color.console_background));

        consoleOutput = new TextView(requireContext());
        consoleOutput.setTextColor(requireContext().getColor(R.color.console_text));
        consoleOutput.setTextSize(11);
        consoleOutput.setTypeface(android.graphics.Typeface.MONOSPACE);
        consoleOutput.setPadding(16, 16, 16, 16);

        ScrollView.LayoutParams params = new ScrollView.LayoutParams(
            ScrollView.LayoutParams.MATCH_PARENT,
            ScrollView.LayoutParams.WRAP_CONTENT
        );
        scrollView.addView(consoleOutput, params);

        logBuffer = new StringBuilder();
        
        return scrollView;
    }

    public void log(String message) {
        logBuffer.append(message).append("\n");
        if (consoleOutput != null) {
            consoleOutput.setText(logBuffer.toString());
            // Auto-scroll to bottom
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
        }
    }

    public void logError(String error) {
        log("❌ ERROR: " + error);
    }

    public void logSuccess(String message) {
        log("✅ SUCCESS: " + message);
    }

    public void logWarning(String warning) {
        log("⚠️ WARNING: " + warning);
    }

    public void clear() {
        logBuffer.setLength(0);
        if (consoleOutput != null) {
            consoleOutput.setText("");
        }
    }

    public String getConsoleOutput() {
        return logBuffer.toString();
    }
}
