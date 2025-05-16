package com.example.foundit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {

    private final Context context;
    private final List<Reply> replyList;

    public ReplyAdapter(Context context, List<Reply> replies) {
        this.context = context;
        this.replyList = replies;
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reply_row, parent, false);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
        Reply reply = replyList.get(position);

        holder.tvUserName.setText(reply.getUserName());
        holder.tvReplyText.setText(reply.getReplyText());
        holder.tvTimestamp.setText(reply.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return replyList.size();
    }

    static class ReplyViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserName, tvReplyText, tvTimestamp;

        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tvReplyUserName);
            tvReplyText = itemView.findViewById(R.id.tvReplyText);
            tvTimestamp = itemView.findViewById(R.id.tvReplyTimestamp);
        }
    }
}
