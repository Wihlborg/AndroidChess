package com.example.androidchess.chessboard;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class ImageViewCell extends android.support.v7.widget.AppCompatImageView {

    String fileName;

    public ImageViewCell(Context context, String fileName) {
        super(context);
        this.fileName = fileName;
        String resource = "@drawable/" + fileName;
        this.setImageResource(getResources().getIdentifier(resource, null, null));
    }

    public ImageViewCell(Context context) {
        super(context);
    }

    public ImageViewCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean allowDrag () {
        // There is something to drag if the cell is not empty.
        if (fileName == "")
            return false;
        else
            return true;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
