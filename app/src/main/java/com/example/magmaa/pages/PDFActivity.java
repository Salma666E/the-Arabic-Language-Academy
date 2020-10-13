package com.example.magmaa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.magmaa.R;
import com.github.barteksc.pdfviewer.PDFView;

public class PDFActivity extends AppCompatActivity {
PDFView mPdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        mPdf=findViewById(R.id.pdfView);
        mPdf.fromAsset("magmaa.pdf").load();
    }
    public void onBackPressed() {
        startActivity(new Intent(PDFActivity.this , MainActivity.class ));
        super.onBackPressed();
    }
}
