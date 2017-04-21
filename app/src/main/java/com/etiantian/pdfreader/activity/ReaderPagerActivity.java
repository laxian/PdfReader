package com.etiantian.pdfreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.etiantian.pdfreader.util.PdfHelper;
import com.etiantian.pdfreader.adapter.PdfPagerAdapter;
import com.etiantian.pdfreader.R;

public class ReaderPagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private PdfHelper pdfHelper;
    private Button mBtDemo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reader2);

        initView();
    }

    private void initView() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("ReaderPagerActivity");

//        pdfHelper = PdfHelper.getPdfHelper(this, "/sdcard/sample.pdf");
        pdfHelper = PdfHelper.getPdfHelper(this, "file:///android_asset/moby.pdf");

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new PdfPagerAdapter(pdfHelper));
        mBtDemo2 = (Button) findViewById(R.id.bt_demo2);
        mBtDemo2.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pdfHelper != null) {
            pdfHelper.close();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_demo2:
                startActivity(new Intent(this, ReaderActivity.class));
                finish();
                break;
        }
    }
}
