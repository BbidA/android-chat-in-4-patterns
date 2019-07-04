package nju.androidchat.client.hw1;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.StyleableRes;
import lombok.Setter;
import nju.androidchat.client.R;
import nju.androidchat.client.Utils;
import nju.androidchat.client.component.OnRecallMessageRequested;

import java.util.UUID;

/**
 * Author: J.D. Liao
 * Date: 2019-07-04
 * Description:
 */
public class ImageTextSend extends LinearLayout implements View.OnLongClickListener {
    @StyleableRes
    int index0 = 0;

    private TextView textView;
    private Context context;
    private UUID messageId;
    @Setter
    private OnRecallMessageRequested onRecallMessageRequested;

    public ImageTextSend(
            Context context,
            String text,
            UUID messageId,
            OnRecallMessageRequested onRecallMessageRequested
    ) {
        super(context);
        this.context = context;
        inflate(context, R.layout.item_text_send, this);
        this.textView = findViewById(R.id.chat_item_content_text);
        this.messageId = messageId;
        this.onRecallMessageRequested = onRecallMessageRequested;

        this.setOnLongClickListener(this);
        setText(text);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public void setText(String text) {
        renderImage(text);
    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确定要撤回这条消息吗？")
                .setPositiveButton("是", (dialog, which) -> {
                    if (onRecallMessageRequested != null) {
                        onRecallMessageRequested.onRecallMessageRequested(this.messageId);
                    }
                })
                .setNegativeButton("否", ((dialog, which) -> {
                }))
                .create()
                .show();

        return true;


    }

    private void renderImage(String text) {
        textView.setText(Html
                .fromHtml(
                        Utils.convertToHtmlImageString(text),
                        Html.FROM_HTML_MODE_LEGACY,
                        new ImageGetter(textView, context, text),
                        null
                ));
    }
}

