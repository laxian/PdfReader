package com.etiantian.pdfreader.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * 读取pdf文档工具类
 */
public class PdfHelper {

    private PdfRenderer mRenderer;
    private ParcelFileDescriptor mFileDescriptor;
    private Context mContext;
    private int index = 0;
    private static final String SAMPLE_FILE = "moby.pdf";


    public static PdfHelper getPdfHelper(Context context, String pdfPath) {
        PdfHelper pdfHelper = new PdfHelper();
        pdfHelper.mContext = context;
        pdfHelper.init(pdfPath);
        return pdfHelper;
    }

    private void init(String pdfPath) {
        try {
            if (pdfPath.contains("file:///android_asset")) {
                mFileDescriptor = getSeekableFileDescriptor(FileUtils.fileFromAsset(mContext, SAMPLE_FILE));
            } else {
                mFileDescriptor = getSeekableFileDescriptor(pdfPath);
            }
            assert mFileDescriptor != null;
            mRenderer = new PdfRenderer(mFileDescriptor);
        } catch (Exception e) {
            Log.d("pdf", e.toString());
            close();
        }
    }


    private ParcelFileDescriptor getSeekableFileDescriptor(String path) throws IOException {
        return getSeekableFileDescriptor(new File(path));
    }


    private ParcelFileDescriptor getSeekableFileDescriptor(File file) throws IOException {
        ParcelFileDescriptor pfd;
        if (file.exists()) {
            pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            return pfd;
        }
        return null;
    }

    private synchronized PdfRenderer.Page getPDFPage(int position) throws Exception {
        synchronized (PdfHelper.class) {
            return mRenderer.openPage(position);
        }
    }

    public Bitmap getBitmap(int position) {
        PdfRenderer.Page page = null;
        try {
            page = getPDFPage(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap(page);
    }

    public synchronized static Bitmap getBitmap(PdfRenderer.Page page) {
        return getBitmap(page, Bitmap.Config.ARGB_8888);
    }

    private synchronized static Bitmap getBitmap(PdfRenderer.Page page, Bitmap.Config config) {
        synchronized (PdfHelper.class) {
            try {
                Bitmap bitmap = null;
                try {
                    float withSc = 1280f / page.getWidth();
                    bitmap = Bitmap.createBitmap(
                            (int) (page.getWidth() * withSc),
                            (int) (page.getHeight() * withSc),
                            config);

                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                } catch (Exception e) {
                    Log.d("pdf", e.toString());
                } finally {
                    page.close();
                }
                return bitmap;
            } catch (Exception e) {
                Log.d("pdf", e.toString());
            }
            return null;
        }
    }

    public void close() {
        try {
            mRenderer.close();
        } catch (Exception e) {
            Log.d("pdf", e.toString());
        }
        try {
            mFileDescriptor.close();
        } catch (Exception e) {
            Log.d("pdf", e.toString());
        }
        mContext = null;
        mRenderer = null;
        mFileDescriptor = null;
    }

    public synchronized PdfRenderer.Page next() {
        PdfRenderer.Page page = null;
        try {
            page = mRenderer.openPage(index++);
        } catch (Exception e) {
            e.printStackTrace();
            --index;
            try {
                Thread.sleep(50);
                page = mRenderer.openPage(index++);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return page;
    }

    public boolean hasNext() {
        return index < mRenderer.getPageCount();
    }

    public int getPageCount() {
        return mRenderer.getPageCount();
    }

}
