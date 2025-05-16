package com.example.foundit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class ReplyDialog extends Dialog {

    public interface ReplyListener {
        void onReplySent(String replyText);
    }

    private ReplyListener listener;
    private EditText etReplyText;
    private Button btnSendReply;

    public ReplyDialog(@NonNull Context context, ReplyListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reply_dialog);

        etReplyText = findViewById(R.id.etReplyText);
        btnSendReply = findViewById(R.id.btnSendReply);

        btnSendReply.setOnClickListener(v -> {
            String reply = etReplyText.getText().toString().trim();
            if (TextUtils.isEmpty(reply)) {
                Toast.makeText(getContext(), "Please write a reply", Toast.LENGTH_SHORT).show();
                return;
            }
            listener.onReplySent(reply);
            dismiss();
        });
    }
}
