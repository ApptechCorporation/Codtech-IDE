package com.androidide.ui;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;

public class XMLLayoutParser {

    private Context context;

    public XMLLayoutParser(Context context) {
        this.context = context;
    }

    public ViewGroup parseLayout(String xmlContent) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlContent));

            return parseElement(parser);
        } catch (Exception e) {
            e.printStackTrace();
            return createErrorView();
        }
    }

    private ViewGroup parseElement(XmlPullParser parser) throws Exception {
        int eventType = parser.getEventType();
        ViewGroup rootView = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();
                
                if (tagName.equals("LinearLayout")) {
                    rootView = createLinearLayout(parser);
                    parseChildren(parser, rootView);
                } else if (tagName.equals("FrameLayout")) {
                    rootView = createFrameLayout(parser);
                    parseChildren(parser, rootView);
                } else if (tagName.equals("RelativeLayout")) {
                    rootView = createRelativeLayout(parser);
                    parseChildren(parser, rootView);
                }
                break;
            }
            eventType = parser.next();
        }

        return rootView != null ? rootView : createErrorView();
    }

    private void parseChildren(XmlPullParser parser, ViewGroup parent) throws Exception {
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();

                switch (tagName) {
                    case "TextView":
                        parent.addView(createTextView(parser));
                        break;
                    case "Button":
                        parent.addView(createButton(parser));
                        break;
                    case "EditText":
                        parent.addView(createEditText(parser));
                        break;
                    case "ImageView":
                        parent.addView(createImageView(parser));
                        break;
                    case "LinearLayout":
                        LinearLayout nestedLinear = createLinearLayout(parser);
                        parseChildren(parser, nestedLinear);
                        parent.addView(nestedLinear);
                        break;
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                if (parser.getName().equals(parent.getClass().getSimpleName())) {
                    break;
                }
            }
            eventType = parser.next();
        }
    }

    private LinearLayout createLinearLayout(XmlPullParser parser) {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        String orientation = parser.getAttributeValue(null, "android:orientation");
        if ("vertical".equals(orientation)) {
            layout.setOrientation(LinearLayout.VERTICAL);
        } else {
            layout.setOrientation(LinearLayout.HORIZONTAL);
        }

        String gravity = parser.getAttributeValue(null, "android:gravity");
        if ("center".equals(gravity)) {
            layout.setGravity(android.view.Gravity.CENTER);
        }

        String padding = parser.getAttributeValue(null, "android:padding");
        if (padding != null) {
            int padValue = Integer.parseInt(padding.replaceAll("[^0-9]", ""));
            layout.setPadding(padValue, padValue, padValue, padValue);
        }

        return layout;
    }

    private FrameLayout createFrameLayout(XmlPullParser parser) {
        FrameLayout layout = new FrameLayout(context);
        layout.setLayoutParams(new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return layout;
    }

    private RelativeLayout createRelativeLayout(XmlPullParser parser) {
        RelativeLayout layout = new RelativeLayout(context);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return layout;
    }

    private TextView createTextView(XmlPullParser parser) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        String text = parser.getAttributeValue(null, "android:text");
        if (text != null) {
            textView.setText(text);
        }

        String textSize = parser.getAttributeValue(null, "android:textSize");
        if (textSize != null) {
            float size = Float.parseFloat(textSize.replaceAll("[^0-9.]", ""));
            textView.setTextSize(size);
        }

        textView.setTextColor(context.getColor(android.R.color.white));
        return textView;
    }

    private Button createButton(XmlPullParser parser) {
        Button button = new Button(context);
        button.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        String text = parser.getAttributeValue(null, "android:text");
        if (text != null) {
            button.setText(text);
        }

        return button;
    }

    private EditText createEditText(XmlPullParser parser) {
        EditText editText = new EditText(context);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        String hint = parser.getAttributeValue(null, "android:hint");
        if (hint != null) {
            editText.setHint(hint);
        }

        return editText;
    }

    private ImageView createImageView(XmlPullParser parser) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return imageView;
    }

    private ViewGroup createErrorView() {
        LinearLayout errorLayout = new LinearLayout(context);
        errorLayout.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ));
        errorLayout.setOrientation(LinearLayout.VERTICAL);

        TextView errorText = new TextView(context);
        errorText.setText("Erro ao parsear layout XML");
        errorText.setTextColor(context.getColor(android.R.color.holo_red_light));
        errorLayout.addView(errorText);

        return errorLayout;
    }
}
