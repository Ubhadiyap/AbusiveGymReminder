package com.pipit.agc.agc.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pipit.agc.agc.util.Constants;
import com.pipit.agc.agc.R;
import com.pipit.agc.agc.util.Util;
import com.pipit.agc.agc.activity.MessageBodyActivity;
import com.pipit.agc.agc.model.DayRecord;
import com.pipit.agc.agc.model.Message;

import java.util.List;

/**
 * Created by Eric on 1/22/2016.
 */
public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedAdapter.CardViewHolder> {
    public static final double GYM_STATUS_HEIGHT_RATIO=.333;
    private List<Message> _messages;
    private List<DayRecord> _days;
    private Context _context;
    private int _offset = 0; //Used because the first element is not a list item

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView timestamp;
        TextView header;
        TextView comment;
        ImageView icon;

        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            header = (TextView)itemView.findViewById(R.id.header);
            comment = (TextView)itemView.findViewById(R.id.comment);
            icon = (ImageView)itemView.findViewById(R.id.icon);
            timestamp = (TextView) itemView.findViewById(R.id.date);
        }
    }

    public NewsfeedAdapter(List<Message> mMessages, List<DayRecord> mDays, Context context) {
        _messages = mMessages;
        _context = context;
        _days = mDays;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newsfeed_item_card, parent, false);
        CardViewHolder vh = new CardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Message m = _messages.get(position - _offset);
        holder.header.setText(_messages.get(position - _offset).getHeader());
        holder.comment.setText(_messages.get(position - _offset).getBody());
        holder.timestamp.setText(_messages.get(position - _offset).getIntelligentDateString());
        if (!m.getRead()){
            //Todo: Add "read" field to database
            //holder.header.setTypeface(null, Typeface.BOLD);
            //holder.timestamp.setTypeface(null, Typeface.BOLD);
        }
        Bitmap bMap = BitmapFactory.decodeResource(_context.getResources(), R.drawable.notification_icon);
        final int mpos = position;
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, MessageBodyActivity.class);
                intent.putExtra(Constants.MESSAGE_ID, _messages.get(mpos - _offset).getId());
                _context.startActivity(intent);
                //Todo: Mark comment as "read"
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return _messages.size()+_offset;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private int calculateGymStatusCardHeight(){
        double screenheight = Util.getScreenHeightMinusStatusBar(_context);
        screenheight*=GYM_STATUS_HEIGHT_RATIO;
        return (int) screenheight;
    }


    public void updateDayrecords(List<DayRecord> newSet){
        _days=newSet;
    }

}