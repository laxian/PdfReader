package com.etiantian.pdfreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.etiantian.pdfreader.util.PdfHelper;
import com.etiantian.pdfreader.R;
import com.etiantian.pdfreader.task.Page2BitmapTask;

public class ReaderActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView mIvCover;
    private PdfHelper pdfHelper;
    private ImageView mIvPdf;
    private Button mBtDemo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reader);
        initView();
    }

    private void initView() {
        pdfHelper = PdfHelper.getPdfHelper(this, "file:///android_asset/sample.pdf");
        mIvCover = (ImageView) findViewById(R.id.iv_pdf);
        new Page2BitmapTask(mIvCover).execute(pdfHelper.next());

        mIvCover.setOnClickListener(this);
        mIvPdf = (ImageView) findViewById(R.id.iv_pdf);
        mIvPdf.setOnClickListener(this);
        mBtDemo1 = (Button) findViewById(R.id.bt_demo1);
        mBtDemo1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_pdf:
                if (pdfHelper.hasNext()) {
                    new Page2BitmapTask(mIvCover).execute(pdfHelper.next());
                }
                break;
            case R.id.bt_demo1:
                startActivity(new Intent(this, ReaderPagerActivity.class));
                finish();
                break;
        }
    }
}
