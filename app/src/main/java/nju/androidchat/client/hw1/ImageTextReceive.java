package nju.androidchat.client.hw1;

import android.content.Context;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.StyleableRes;
import nju.androidchat.client.R;
import nju.androidchat.client.Utils;
import nju.androidchat.client.component.OnRecallMessageRequested;

import java.util.UUID;

/**
 * Author: J.D. Liao
 * Date: 2019-07-04
 * Description:
 */
public class ImageTextReceive extends LinearLayout {


    @StyleableRes
    int index0 = 0;

    private TextView textView;
    private Context context;
    private UUID messageId;
    private OnRecallMessageRequested onRecallMessageRequested;

    public ImageTextReceive(Context context, String text, UUID messageId) {
        super(context);
        this.context = context;
        inflate(context, R.layout.item_text_receive, this);
        this.textView = findViewById(R.id.chat_item_content_text);
        this.messageId = messageId;
        setText(text);
    }

    public void init(Context context) {

    }

    public String getText() {
        return textView.getText().toString();
    }

    public void setText(String text) {
        renderImage(text);
    }

    private void renderImage(String text) {
        text = Utils.convertToHtmlImageString(text);
        textView.setText(Html
                .fromHtml(
                        text,
                        Html.FROM_HTML_MODE_LEGACY,
                        new ImageGetter(textView, context, text),
                        null
                ));
    }
}
