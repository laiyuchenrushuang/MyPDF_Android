package com.seatrend.pdf;

import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hdy on 2020/10/16 18:21
 *
 * 方案一 Jasper 用表格对应的ijasper文件进行参数绑定
 * 缺陷 java.awt.Font的依赖，体积过大，环境调整困难
 */
public class TestActivty2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_2);
//
        SimpleDateFormat format = new SimpleDateFormat("yyyy年 MM月 dd日");

        try {
//            InputStream inputStream = this.getAssets().open("jdczczyzxdjzrsqb.jasper");

            String srcFile = Environment.getExternalStorageDirectory() + File.separator + "jdczczyzxdjzrsqb.jasper";
            InputStream inputStream  =new FileInputStream(srcFile);
//2.构建Print对象，用于让模块结合数据
            //第二个参数就是用来填充模板中的parameters
            Map<String, Object> map = new HashMap<>();
            map.put("xm", "赖雨");

            JasperPrint print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            JasperExportManager.exportReportToPdfStream(print, outputStream);
            
            String path = Environment.getExternalStorageDirectory() + File.separator + "tableJasper.pdf";

            File e = new File(path);
            if (e.exists()) {
                e.delete();
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(path);

            byte bytes[] = new byte[1024];

            fos.write(outputStream.toByteArray());

        } catch (IOException | JRException e) {
            e.printStackTrace();
        }

    }
}
