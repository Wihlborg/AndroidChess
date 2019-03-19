package com.example.androidchess.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.androidchess.R;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>   {



    private static final String TAG = "RecycleViewAdapter";
MenuActivity menuActivity=new MenuActivity();
    private ArrayList<String> mImageNames=new ArrayList<>();
    private ArrayList<String> mImages=new ArrayList<>();
    private Context mContext;



    public interface mClickListener {

        void mClick(View v, int position);
    }




    public RecycleViewAdapter(Context mContext, ArrayList<String> mImageNames, ArrayList<String> mImages) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Log.d(TAG,"onbindviewHolder: called.");
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
        .into(viewHolder.image);

        viewHolder.imagename.setText(mImageNames.get(position));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View view) {
                Log.d(TAG,"onClick: clicked on: "+mImageNames.get(position));
              Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();

              if (mImageNames.get(position)=="Lobby 1"){

              Intent intent = new Intent(view.getContext(), menuinLobby.class);
              view.getContext().startActivity(intent);
              }
          }
                              }
        );
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
CircleImageView image;
TextView imagename;
RelativeLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          image=itemView.findViewById(R.id.image);
          imagename=itemView.findViewById(R.id.image_name);
          parentLayout=itemView.findViewById(R.id.parent_layout);


        }
    }


}
