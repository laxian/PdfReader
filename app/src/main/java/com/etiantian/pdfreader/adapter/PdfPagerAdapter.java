package com.etiantian.pdfreader.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.etiantian.pdfreader.App;
import com.etiantian.pdfreader.util.PdfHelper;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by zhouweixian on 2017/3/24
 */

public class PdfPagerAdapter extends PagerAdapter {

    private PdfHelper helper;

    public PdfPagerAdapter(PdfHelper helper) {
        this.helper = helper;
    }

    @Override
    public int getCount() {
        return helper.getPageCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ImageView iv = (ImageView)object;
        if (iv==null)
            return;
        releaseImageViewResourse(iv);
        container.removeView(iv);

    }

    private void releaseImageViewResourse(ImageView iv) {

        if (iv == null)
            return;
        Drawable drawable = iv.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        //希望做一次垃圾回收
        System.gc();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        Bitmap bitmap = helper.getBitmap(position);
        ImageView imageView = new PhotoView(App.getInstance());
        imageView.setImageBitmap(bitmap);
        view.addView(imageView);
        return imageView;
    }

}