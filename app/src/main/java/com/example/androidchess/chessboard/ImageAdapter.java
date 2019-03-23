package com.example.androidchess.chessboard;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.androidchess.R;

import static com.example.androidchess.chessboard.GameActivity.getFilename;

public class ImageAdapter extends BaseAdapter {

    private final Context mContext;
    public ImageView[] squares = new ImageView[64];
    public int currentCells = 0;

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

        return squares[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.d("array" ,""+currentCells);
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

            //String fileName = img.getResources().getResourceName(pieceIds[position]);
            //fileName = fileName.charAt(fileName.length()-2) + "" + fileName.charAt(fileName.length()-1);

            if (currentCells < 64) {
                squares[currentCells++] = img;
                //piecesStr[currentCells++] = fileName;
            }

            int x = position % 8;
            int y = position / 8;
            char c = 97;

            String str = "" + Character.toString(((char)(c+x))) + Integer.toString((y+1));
            img.setTag(str);
            //System.out.println(img.getTag().toString());
            //Log.d("cell" , ""+Character.toString(((char)(c+x)))+Integer.toString((y+1)));
            //System.out.println(str);

            //if (position > 15 && position < 48)
              //  img.setAlpha(0f);

        }
        else {
            //Log.d("" ,  "converrtView");
            img = (ImageView) convertView;
            //System.out.println(img.getTag().toString());
        }

        img.setImageResource(pieceIds[position]);
        String fileName = img.getResources().getResourceName(pieceIds[position]);
        fileName = fileName.charAt(fileName.length()-2) + "" + fileName.charAt(fileName.length()-1);

        if (fileName.charAt(0) == 't' && !GameActivity.possibleMoves[position]) {
            img.setAlpha(0f);
        }
        else {
            img.setAlpha(1f);
        }

        //img.setTag(fileName);
        //Log.d("currentCells", Integer.toString(currentCells));
        return img;
    }

    public String getBoardStr() {
        String fenString = "";
        int emptyCellCounter = 0;
        int n;
        for (int i = 0; i<pieceIds.length; i++) {
            String imgName = getFilename(i);
            n = i % 8;

            // at the start of a new row check if there was empty cells
            if (n == 0 && !fenString.isEmpty()) {
                if (emptyCellCounter > 0) {
                    fenString += Integer.toString(emptyCellCounter);
                    emptyCellCounter = 0;
                }
                fenString += '/';
            }

            // for white pieces
            if (imgName.charAt(1) == 'w') {
                if (imgName.charAt(0) != 't') {
                    if (emptyCellCounter > 0)
                        fenString += Integer.toString(emptyCellCounter);
                    fenString += imgName.toUpperCase().charAt(0);
                    emptyCellCounter = 0;
                }
                else {
                    emptyCellCounter++;
                }
            }

            // else black pieces
            else {
                if (imgName.charAt(0) != 't') {
                    if (emptyCellCounter > 0)
                        fenString += emptyCellCounter;
                    fenString += imgName.charAt(0);
                    emptyCellCounter = 0;
                }
                else {
                    emptyCellCounter++;
                }
            }
        }
        return fenString;
    }

    public Integer[] pieceIds = {
            R.drawable.rb,
            R.drawable.nb,
            R.drawable.bb,
            R.drawable.qb,
            R.drawable.kb,
            R.drawable.bb,
            R.drawable.nb,
            R.drawable.rb,

            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,
            R.drawable.pb,

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

            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,
            R.drawable.pw,

            R.drawable.rw,
            R.drawable.nw,
            R.drawable.bw,
            R.drawable.qw,
            R.drawable.kw,
            R.drawable.bw,
            R.drawable.nw,
            R.drawable.rw,

    };

}
