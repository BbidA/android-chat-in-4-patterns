package nju.androidchat.client.hw1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.android.volley.VolleyLog.TAG;

/**
 * Author: J.D. Liao
 * Date: 2019-07-04
 * Description:
 */
public class ImageGetter implements Html.ImageGetter {

    private Context context;
    private TextView textView;

    ImageGetter(TextView textView, Context context) {
        super();
        this.textView = textView;
        this.context = context;
    }

    @Override
    public Drawable getDrawable(String source) {
        Drawable drawable = null;
        File file = new File(context.getFilesDir(), getImageName(source));
        if (file.exists()) {
            drawable = Drawable.createFromPath(file.getAbsolutePath());
            assert drawable != null;
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 2, drawable.getIntrinsicHeight() * 2);
        } else {
            getNetworkImage(source);
        }
        return drawable;
    }

    private void getNetworkImage(String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageRequest request = new ImageRequest(url, response -> {
            saveImage(getImageName(url), response);
            System.out.println("Text View text is:" + textView.getText());
            textView.setText(Html.fromHtml(textView.getText().toString(), Html.FROM_HTML_MODE_LEGACY, this, null));
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, error -> Log.d(TAG, "onErrorResponse:" + error));
        queue.add(request);
    }

    private void saveImage(String name, Bitmap bitmap) {
        try (FileOutputStream fout = context.openFileOutput(name, Context.MODE_PRIVATE)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getImageName(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
}
