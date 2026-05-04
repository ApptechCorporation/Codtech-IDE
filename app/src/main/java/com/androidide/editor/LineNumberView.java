package com.androidide.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import com.androidide.R;

public class LineNumberView extends View {

    private Paint paint;
    private int lineHeight = 0;
    private int ascent = 0;
    private CodeEditor codeEditor;

    public LineNumberView(Context context) {
        super(context);
        init();
    }

    public LineNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(12 * getResources().getDisplayMetrics().density);
        paint.setColor(getContext().getColor(R.color.editor_line_number));
        paint.setTypeface(android.graphics.Typeface.MONOSPACE);
    }

    public void setCodeEditor(CodeEditor editor) {
        this.codeEditor = editor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (codeEditor == null) return;

        int baseline = getBaseline();
        if (baseline < 0) {
            baseline = ascent;
        }

        int lineCount = codeEditor.getLineCount();
        int canvasHeight = getHeight();
        int lineHeight = codeEditor.getLineHeight();

        for (int i = 0; i < lineCount; i++) {
            int y = i * lineHeight + baseline;
            if (y > canvasHeight) {
                break;
            }
            canvas.drawText(String.valueOf(i + 1), 10, y, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(60, MeasureSpec.getSize(heightMeasureSpec));
    }
}
