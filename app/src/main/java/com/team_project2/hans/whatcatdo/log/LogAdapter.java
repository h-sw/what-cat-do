package com.team_project2.hans.whatcatdo.log;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.team_project2.hans.whatcatdo.R;
import com.team_project2.hans.whatcatdo.database.LogEmotion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.MyViewHolder> {
    private ArrayList<LogEmotion> mDataset;

    public void setDataSet(ArrayList<LogEmotion> myDataset) {
        mDataset = myDataset;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView text_comment;
        public TextView text_emotion;
        public ImageView img_log;
        public TextView text_date;
        public CardView card_log;
        public MyViewHolder(View v) {
            super(v);
            view = v;
            text_date = v.findViewById(R.id.text_date);
            text_comment = v.findViewById(R.id.text_comment);
            text_emotion = v.findViewById(R.id.text_emotion);
            img_log = v.findViewById(R.id.img_log);
            card_log = v.findViewById(R.id.card_log);

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
                .inflate(R.layout.fragment_item_log, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.textView.setText(mDataset[position]);

        final LogEmotion log = mDataset.get(position);

        //String time = new SimpleDateFormat("HH:mm:SS").format(log.getTimestamp());
        String date =  new SimpleDateFormat("yyyy.MM.dd").format(log.getTimestamp());

        holder.text_date.setText(date);
        holder.text_comment.setText(log.getComment());
        if(log.getComment().isEmpty()){
            holder.text_comment.setText("코멘트가 없습니다");
        }

        holder.text_emotion.setText(log.getPrimaryEmotion());

        if(!log.getPath().isEmpty()){
            final int THUMBSIZE = 64;
            //용량이 큰 bitmap image를 thumbnail로 변경하여 보여줌
            //recyclerview에서 원본을 사용하면 메모리 부족 오류 발생
            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(log.getPath()), THUMBSIZE, THUMBSIZE);

            holder.img_log.setImageBitmap(ThumbImage);
        }
        holder.card_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.view.getContext(),LogContentsActivity.class);
                intent.putExtra("logEmotion",log);
                holder.view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(mDataset == null){
            return 0;
        }
        return mDataset.size();
    }
}
