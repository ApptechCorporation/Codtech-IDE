package com.androidide.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.androidide.R;

public class LayoutPreviewFragment extends Fragment {

    private LinearLayout previewContainer;
    private ScrollView scrollView;
    private XMLLayoutParser layoutParser;
    private TextView placeholderText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scrollView = new ScrollView(requireContext());
        scrollView.setBackgroundColor(requireContext().getColor(R.color.editor_background));

        previewContainer = new LinearLayout(requireContext());
        previewContainer.setOrientation(LinearLayout.VERTICAL);
        previewContainer.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        previewContainer.setPadding(16, 16, 16, 16);

        // Add placeholder
        placeholderText = new TextView(requireContext());
        placeholderText.setText("Preview de Layout XML\n\nEdite o arquivo activity_main.xml para ver a preview aqui.");
        placeholderText.setTextColor(requireContext().getColor(R.color.text_secondary));
        placeholderText.setTextSize(14);
        placeholderText.setPadding(16, 16, 16, 16);

        previewContainer.addView(placeholderText);
        scrollView.addView(previewContainer);

        // Initialize parser
        layoutParser = new XMLLayoutParser(requireContext());

        return scrollView;
    }

    public void updatePreview(String layoutXml) {
        if (previewContainer != null && layoutParser != null) {
            previewContainer.removeAllViews();
            
            try {
                // Parse and render layout
                ViewGroup parsedLayout = layoutParser.parseLayout(layoutXml);
                if (parsedLayout != null) {
                    previewContainer.addView(parsedLayout);
                    placeholderText = null;
                }
            } catch (Exception e) {
                // Show error
                TextView errorText = new TextView(requireContext());
                errorText.setText("Erro ao renderizar layout:\n" + e.getMessage());
                errorText.setTextColor(requireContext().getColor(R.color.console_error));
                errorText.setPadding(16, 16, 16, 16);
                previewContainer.addView(errorText);
            }
        }
    }

    public void clearPreview() {
        if (previewContainer != null) {
            previewContainer.removeAllViews();
            
            placeholderText = new TextView(requireContext());
            placeholderText.setText("Preview de Layout XML\n\nEdite o arquivo activity_main.xml para ver a preview aqui.");
            placeholderText.setTextColor(requireContext().getColor(R.color.text_secondary));
            placeholderText.setTextSize(14);
            placeholderText.setPadding(16, 16, 16, 16);
            
            previewContainer.addView(placeholderText);
        }
    }
}
