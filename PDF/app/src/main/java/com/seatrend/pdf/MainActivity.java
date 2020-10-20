package com.seatrend.pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



//可行的例子1
public class MainActivity extends AppCompatActivity {
    private static final int PDF_SAVE_RESULT = 2;// 保存PDF文件的结果开始意图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView view = findViewById(R.id.text);
        view.setText(Html.fromHtml("<div style=\"font-family:PMingLiU;\">DIV 採用新細明體的文字</div>"));
        View pdfview = this.getLayoutInflater().inflate(R.layout.pdfview, null); //1
        pdfview.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), //2, 测量大小
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        pdfview.layout(0, 0, pdfview.getMeasuredWidth(), pdfview.getMeasuredHeight()); //3, 测量位置
        PdfDocument document = new PdfDocument();//1, 建立PdfDocument
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                pdfview.getMeasuredWidth(), pdfview.getMeasuredHeight(), 1).create();//2
        PdfDocument.Page page = document.startPage(pageInfo);
        pdfview.draw(page.getCanvas());//3
        document.finishPage(page);//4

        try {
            String path = Environment.getExternalStorageDirectory() + File.separator + "table.pdf";

            File e = new File(path);
            if (e.exists()) {
                e.delete();
            }
            document.writeTo(new FileOutputStream(e));
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();//5

    }
}