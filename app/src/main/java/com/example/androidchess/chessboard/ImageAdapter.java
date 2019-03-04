package com.example.androidchess.chessboard;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.androidchess.R;

public class ImageAdapter extends BaseAdapter {

    private final Context mContext;
    public final ImageViewCell[][] pieces2 = new ImageViewCell[8][8];
    public final ImageViewCell[] pieces = new ImageViewCell[64];
    private int currentCells = 0;
    private int currentCellsX = 0;
    private int currentCellsY = 0;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return pieceIds.length;
    }

    @Override
    public Object getItem(int position) {
        return pieces[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageViewCell img;

        if (convertView == null) {

            // if it's not recycled, initialize some attributes
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();

            int width = metrics.widthPixels / 8;

            //img = new ImageViewCell(mContext, getPieceName(position));
            img = new ImageViewCell(mContext);

            img.setLayoutParams(new GridView.LayoutParams(width, width));

            img.setScaleType(ImageView.ScaleType.FIT_XY);
            img.setPadding(1, 1, 1, 1);
        } else {
            img = (ImageViewCell) convertView;
        }

        img.setImageResource(pieceIds[position]);
        img.setFileName(img.getResources().getResourceName(pieceIds[position]));

        if (currentCellsX < 8) {
            if (currentCellsY < 8) {
                pieces2[currentCellsX][currentCellsY] = img;
                currentCellsY = currentCellsY++ % 8;
            }
            currentCellsX++;
        }

        pieces[currentCells++] = img;
        return img;
    }

    public Integer[] pieceIds = {
            R.drawable.rw,
            R.drawable.nw,
            R.drawable.bw,
            R.drawable.qw,
            R.drawable.kw,
            R.drawable.bw,
            R.drawable.nw,
            R.drawable.rw,

            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,

            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,

            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,

            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,

            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,
            R.drawable.ts,

            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,

            R.drawable.rb,
            R.drawable.nb,
            R.drawable.bb,
            R.drawable.kb,
            R.drawable.qb,
            R.drawable.bb,
            R.drawable.nb,
            R.drawable.rb,

    };

}
