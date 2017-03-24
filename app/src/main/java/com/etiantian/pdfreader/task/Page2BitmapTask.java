package com.etiantian.pdfreader.task;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.etiantian.pdfreader.util.PdfHelper;

/**
 * Created by zhouweixian on 2017/3/24
 */

public class Page2BitmapTask extends AsyncTask<PdfRenderer.Page,Integer, Bitmap> {

    private final ImageView imageView;

    public Page2BitmapTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(PdfRenderer.Page... params) {
        return PdfHelper.getBitmap(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d("pdf", "进度"+values[0]);
    }
}
