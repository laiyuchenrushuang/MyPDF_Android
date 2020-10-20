package com.seatrend.pdf;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.itextpdf.text.PageSize.A4;

/**
 * Created by hdy on 2020/10/19 10:03
 */
public class TestActivty3 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_3);


        //

        log("开始");
        FileOutputStream fo = null;
        try {
         fo = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "my_invoice.pdf");
            log("文件读取成功");
        } catch (IOException e) {
            log("开始 读取文件异常");
            e.printStackTrace();

        }

        Document doc = new Document(A4,0f,0f,0f,0f);
        try {
            PdfWriter writer = PdfWriter.getInstance(doc,fo);
            log("PdfWriter读取成功");
        } catch (DocumentException e) {
            log("PdfWriter异常");
            e.printStackTrace();
        }
        log("添加字体开始");
        try {

            initFooterTest(doc);
            log("添加字体成功");
        } catch (IOException e) {
            log("添加字体IOException");
            e.printStackTrace();
        } catch (DocumentException e) {
            log("添加字体DocumentException");
            e.printStackTrace();
        }

    }

    private void initFooterTest(Document doc) throws IOException, DocumentException {
        BaseFont basfontRegular = BaseFont.createFont("assets/fonts/R-PMingLiU-TW-2.ttf", "UTF-8", BaseFont.EMBEDDED);
         Font appFontRegular  =  new Font(basfontRegular, 12f);
        appFontRegular.setColor(new BaseColor(40, 116, 240));
        PdfPTable footerTable = new PdfPTable(1);
        footerTable.setTotalWidth(A4.getWidth());
        footerTable.setLockedWidth(true) ;
        PdfPCell thankYouCell = new PdfPCell(new Phrase("LLLLLALLLALLL", appFontRegular));
        thankYouCell.setBorder( Rectangle.NO_BORDER);
        thankYouCell.setPaddingLeft(40f);
        thankYouCell.setPaddingTop(40f);
        thankYouCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        footerTable.addCell(thankYouCell);
        doc.add(footerTable);
    }

    private void log(Object obj){
        Log.d("[lylog]  "," "+obj);
    }
}
