package com.example.zaas.pocketbanker.adapters;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.ChatBotUIItem;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/27/16.
 */
public class ChatBotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<ChatBotUIItem> mUiItems;
    private Context mContext;

    private static long TIME_DIFFERENCE_TO_SHOW_ON_UI = 10 * 60 * 1000;
    private static StyleSpan styleSpanBold = new StyleSpan(Typeface.BOLD);

    FrameLayout.LayoutParams questionLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT);

    FrameLayout.LayoutParams answerLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.LEFT);

    public ChatBotAdapter(Context context, List<ChatBotUIItem> uiItems)
    {
        mUiItems = uiItems;
        mContext = context;
    }

    public void setUiItems(List<ChatBotUIItem> uiItems)
    {
        this.mUiItems = uiItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View rootView = inflater.inflate(R.layout.chat_bot_item, parent, false);

        return new ChatBotViewHolder(rootView);

    }

    @Override
    public int getItemCount()
    {
        if (mUiItems != null) {
            return mUiItems.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        ChatBotUIItem uiItem = mUiItems.get(position);
        ChatBotViewHolder vh = (ChatBotViewHolder) holder;
        vh.message.setText(uiItem.getData());

        /*
         * long nextTimeStamp = 0L; if (position != (mUiItems.size() - 1)) {
         * 
         * ChatBotUIItem nextUIItem = mUiItems.get(position + 1); nextTimeStamp = nextUIItem.getTime(); }
         * 
         * long timestamp = uiItem.getTime();
         * 
         * if (Math.abs(timestamp - nextTimeStamp) > TIME_DIFFERENCE_TO_SHOW_ON_UI) {
         * vh.timeStampView.setText(getTimeStampFormat(timestamp)); vh.timeStampView.setVisibility(View.VISIBLE);
         * vh.timestampLayout.setVisibility(View.VISIBLE); } else { vh.timeStampView.setVisibility(View.GONE);
         * vh.timestampLayout.setVisibility(View.GONE);
         * 
         * }
         */

        vh.timeStampView.setText(getTimeStampFormat(uiItem.getTime()));

        if (uiItem.getType().equals(Constants.CHAT_BOT_TYPE_ANSWER)) {

            vh.messageLL.setGravity(Gravity.LEFT);
            vh.message.setLayoutParams(answerLayoutParams);
            vh.timeStampView.setLayoutParams(answerLayoutParams);
            vh.message.setTextColor(Color.WHITE);
            vh.message.setBackgroundResource(R.drawable.answer);

        }
        else if (uiItem.getType().equals(Constants.CHAT_BOT_TYPE_QUESTION)) {

            vh.messageLL.setGravity(Gravity.RIGHT);
            vh.message.setLayoutParams(questionLayoutParams);
            vh.timeStampView.setLayoutParams(questionLayoutParams);
            vh.message.setTextColor(Color.BLACK);
            vh.message.setBackgroundResource(R.drawable.question);

        }

    }

    public class ChatBotViewHolder extends RecyclerView.ViewHolder
    {

        private TextView timeStampView;
        private TextView message;
        // private LinearLayout timestampLayout;
        private LinearLayout messageLL;
        private FrameLayout messageFL;

        public ChatBotViewHolder(View itemView)
        {
            super(itemView);
            timeStampView = (TextView) itemView.findViewById(R.id.timeStamp);
            message = (TextView) itemView.findViewById(R.id.message_tv);
            // timestampLayout = (LinearLayout) itemView.findViewById(R.id.timeStampLayout);
            messageLL = (LinearLayout) itemView.findViewById(R.id.chat_item_ll);
            messageFL = (FrameLayout) itemView.findViewById(R.id.message_framelayout);

        }
    }

    /**
     * utility to give the timestamp in the format and style to be displayed in conversation view
     *
     * @param timeStamp
     *            : timestamp of the message from messages table
     *
     * @return SpannableStringBuilder
     */
    private SpannableStringBuilder getTimeStampFormat(long timeStamp)
    {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (DateUtils.isToday(timeStamp)) {
            builder.append("Today");
            builder.setSpan(styleSpanBold, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(" " + getTimeText(mContext, timeStamp));

        }
        else if (isYesterday(timeStamp)) {
            builder.append("Yesterday");
            builder.setSpan(styleSpanBold, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(" " + getTimeText(mContext, timeStamp));
        }
        else {

            builder.append(getDateText(mContext, timeStamp));
            builder.setSpan(styleSpanBold, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(" " + getTimeText(mContext, timeStamp));

        }
        return builder;
    }

    private static String getDateText(Context context, long time)
    {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR;

        return DateUtils.formatDateTime(context, time, flags);
    }

    private static boolean isYesterday(long timeStamp)
    {
        Calendar now = Calendar.getInstance();
        Calendar givenTime = Calendar.getInstance();
        givenTime.setTimeInMillis(timeStamp);

        if ((now.get(Calendar.DATE) - givenTime.get(Calendar.DATE) == 1)
                && (now.get(Calendar.MONTH) == givenTime.get(Calendar.MONTH))
                && (now.get(Calendar.YEAR) == givenTime.get(Calendar.YEAR))) {
            return true;
        }

        return false;
    }

    private String getTimeText(Context context, long time)
    {
        int flags = DateUtils.FORMAT_SHOW_TIME;
        return DateUtils.formatDateTime(context, time, flags);
    }
}
