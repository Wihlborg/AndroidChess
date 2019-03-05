package com.example.androidchess.chessboard;

import android.content.Context;
import android.nfc.Tag;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.androidchess.R;

public class ImageAdapter extends BaseAdapter {

    private final Context mContext;
    public ImageView[] pieces = new ImageView[64];
    private int currentCells = 0;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
        //Log.d("chess", Integer.toString(pieceIds.length));
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

        ImageView img;

        // if the view has not been created before
        if (convertView == null) {

            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();

            int width = metrics.widthPixels / 8;

            //img = new ImageViewCell(mContext, getPieceName(position));
            img = new ImageView(mContext);

            img.setLayoutParams(new GridView.LayoutParams(width, width));

            img.setScaleType(ImageView.ScaleType.FIT_XY);
            img.setPadding(1, 1, 1, 1);
            pieces[currentCells++] = img;
        } else {
            img = (ImageView) convertView;
        }

        img.setImageResource(pieceIds[position]);

        String fileName = img.getResources().getResourceName(pieceIds[position]);
        fileName = fileName.charAt(fileName.length()-2) + "" + fileName.charAt(fileName.length()-1);
        img.setTag(fileName);

        if (position > 15 && position < 48)
            img.setAlpha(0f);

        //Log.d("currentCells", Integer.toString(currentCells));
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
