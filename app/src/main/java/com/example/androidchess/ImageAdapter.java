package com.example.androidchess;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private final Context mContext;
    private final ImageViewCell[] pieces;

    public ImageAdapter(Context mContext, ImageViewCell[] pieces) {
        this.mContext = mContext;
        this.pieces = pieces;
    }

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
        this.pieces = null;
    }

    @Override
    public int getCount() {
        return pieceIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
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

    private String getPieceName(int position) {
        switch (position) {
            case 0:
                return "rw";

            default:
                return "ts";
        }
    }
}
