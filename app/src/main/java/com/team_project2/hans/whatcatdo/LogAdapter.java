package com.team_project2.hans.whatcatdo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.team_project2.hans.whatcatdo.database.Emotion;
import com.team_project2.hans.whatcatdo.database.LogEmotion;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.MyViewHolder> {
    private ArrayList<LogEmotion> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public TextView text_comment;
        public TextView text_emotion;
        public ImageView img_log;
        public TextView text_date;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            text_date = v.findViewById(R.id.text_date);
            text_comment = v.findViewById(R.id.text_comment);
            text_emotion = v.findViewById(R.id.text_emotion);
            img_log = v.findViewById(R.id.img_log);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LogAdapter(ArrayList<LogEmotion> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_log, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.textView.setText(mDataset[position]);
        LogEmotion log = mDataset.get(position);

        String time = new SimpleDateFormat("HH:mm:SS").format(log.getTimestamp());
        String date =  new SimpleDateFormat("yyyy.MM.dd").format(log.getTimestamp());

        holder.text_date.setText(date);
        holder.text_comment.setText(log.getComment());
        if(log.getComment().isEmpty()){
            holder.text_comment.setText("코멘트가 없습니다");
        }

        holder.text_emotion.setText(log.getPrimaryEmotion());

        File imgFile = new File(log.getPath());
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.img_log.setImageBitmap(bitmap);
        }



    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
