//package com.seatrend.pdf;
//
//import ch.qos.logback.core.joran.conditional.IfAction;
//import com.seatrend.smarthall.entity.hall.*;
//import net.sf.jasperreports.engine.*;
//import org.apache.commons.codec.binary.Base64;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.MediaType;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: zhaochengzhe
// * @Date: 2019/10/15 15:01
// */
//public class JasperUtil {
//    /**
//     * 机动车注册、转移、注销登记/转入申请表
//     *
//     * @param vehicleTemp
//     * @param flowByLsh
//     * @param dlr
//     * @param dlrdh
//     * @param zcs1
//     * @param zcs2
//     * @throws JRException
//     * @throws IOException
//     */
//    public static byte[] createJdczczyzxdjzrb(VehicleTemp vehicleTemp, VehicleFlow flowByLsh, String dlr, String dlrdh, String zcs1, String zcs2) throws JRException, IOException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy年 MM月 dd日");
//        //1.读取.japser文件，构建输入流
//        ClassPathResource resource = new ClassPathResource("model/jdczczyzxdjzrsqb.jasper");
//        InputStream inputStream = resource.getInputStream();
//        //2.构建Print对象，用于让模块结合数据
//        //第二个参数就是用来填充模板中的parameters
//        Map<String, Object> map = new HashMap<>();
//        map.put("xm", StringUtils.isBlank(vehicleTemp.getSyr()) ? flowByLsh.getSyr() : vehicleTemp.getSyr());
//        map.put("sjhm", vehicleTemp.getSjhm());
//        map.put("yjdz", vehicleTemp.getZsxxdz());
//        map.put("yzbm", vehicleTemp.getYzbm1());
//        map.put("gddh", vehicleTemp.getLxdh());
//        map.put("dlrxm", dlr);
//        map.put("dlrsjhm", dlrdh);
//        map.put("phzl", StringUtils.isBlank(vehicleTemp.getHpzl()) ? flowByLsh.getHpzl() : vehicleTemp.getHpzl());
//        map.put("hphm", StringUtils.isBlank(vehicleTemp.getHphm()) ? flowByLsh.getHphm() : vehicleTemp.getHphm());
//        map.put("ppxh", StringUtils.isBlank(vehicleTemp.getClpp1()) ? vehicleTemp.getClpp2() : vehicleTemp.getClpp1());
//        map.put("clsbdh", StringUtils.isBlank(vehicleTemp.getClsbdh()) ? flowByLsh.getClsbdh() : vehicleTemp.getClsbdh());
//        map.put("date", format.format(new Date()));
//        //性质
//        map.put("selxz" + vehicleTemp.getSyxz(), "√");
//        //申请事项
//        map.put("sel" + flowByLsh.getYwlx(), "√");
//
//        //转出地
//        map.put("zcs1", zcs1);
//        map.put("zcs2", zcs2);
////        //市（地、州）
////        map.put("zcs2", " ");
//        JasperPrint print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
//        //3.使用Exporter导出PDF
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            JasperExportManager.exportReportToPdfStream(print, outputStream);
//            return outputStream.toByteArray();
//        } finally {
//            outputStream.close();
//        }
//    }
//
//    /**
//     * 机动车变更登记/备案申请表
//     *
//     * @param vehicleTemp
//     * @param flowByLsh
//     * @param dlr
//     * @param dlrdh
//     * @param zcs1
//     * @param zcs2
//     * @throws JRException
//     * @throws IOException
//     */
//    public static byte[] createJdcbgdjbasqb(VehicleTemp vehicleTemp, VehicleFlow flowByLsh, String dlr, String dlrdh, String zcs1, String zcs2) throws JRException, IOException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy年 MM月 dd日");
//        //1.读取.japser文件，构建输入流
//        ClassPathResource resource = new ClassPathResource("model/jdcbgdjbasqb.jasper");
//        InputStream inputStream = resource.getInputStream();
//        //2.构建Print对象，用于让模块结合数据
//        //第二个参数就是用来填充模板中的parameters
//        Map<String, Object> map = new HashMap<>();
//        String yw = flowByLsh.getYwlx() + flowByLsh.getYwyy();
//        //变更
//        map.put("sel" + yw, "√");
//        if ("DN".equals(yw)) {
//            map.put("gbsyrxm", vehicleTemp.getSyr());
//        } else if ("DA".equals(yw)) {
//            map.put("gbgtsyr", vehicleTemp.getSyr());
//        } else if ("DO".equals(yw)) {
//            map.put("xqnqy", vehicleTemp.getZsxxdz());
//        } else if ("DP".equals(yw)) {
//            map.put("bglxfsyjdz", vehicleTemp.getZsxxdz());
//            map.put("bglxfsyzbm", vehicleTemp.getYzbm1());
//            map.put("bglxfssjhm", vehicleTemp.getSjhm());
//            map.put("bglxfsgddh", vehicleTemp.getLxdh());
//        } else if ("DI".equals(yw)) {
//            //性质
//            map.put("selxz" + vehicleTemp.getSyxz(), "√");
//        } else if ("DJ".equals(yw)) {
//            //省(自治区、直辖市)
//            map.put("zrs1", zcs1);
//            //市（地、州）
//            map.put("zrs2", zcs2);
//        } else if ("DF".equals(yw)) {
//            map.put("gbhxx", vehicleTemp.getFdjh());
//        } else if ("DG".equals(yw)) {
//            map.put("gbhxx", vehicleTemp.getClsbdh());
//        } else if ("DD".equals(yw)) {
//            map.put("gbhxx", vehicleTemp.getCsys());
//        } else if ("DH".equals(yw)) {
//            map.put("gbhxx", StringUtils.isBlank(vehicleTemp.getClpp1()) ? vehicleTemp.getClpp2() : vehicleTemp.getClpp1() + vehicleTemp.getClxh());
//        } else if ("DL".equals(yw)) {
//            map.put("gbhxx", vehicleTemp.getFdjh());
//        } else if ("DK".equals(yw)) {
//            map.put("gbhxx", vehicleTemp.getClsbdh());
//        } else if ("DM".equals(yw)) {
//            map.put("gbhxx", vehicleTemp.getSfzmhm());
//        }
//
//        map.put("hpzl", flowByLsh.getHpzl());
//        map.put("hphm", flowByLsh.getHphm());
//        if (StringUtils.isNotBlank(dlr)) {
//            map.put("dlrxm", dlr);
//            map.put("dlrsjhm", dlrdh);
//        }
//        map.put("qzrq", format.format(new Date()));
//
//
//        JasperPrint print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
//        //3.使用Exporter导出PDF
////        JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            JasperExportManager.exportReportToPdfStream(print, outputStream);
//            return outputStream.toByteArray();
//        } finally {
//            outputStream.close();
//        }
//    }
//
//    /**
//     * 机动车抵押登记/质押备案申请表
//     *
//     * @param vehicleTemp
//     * @param flowByLsh
//     * @param impawnTemp
//     * @param impawn
//     * @param dlr
//     * @param dlrdh
//     * @throws JRException
//     * @throws IOException
//     */
//    public static byte[] createJdcdydjzybasqb(VehicleTemp vehicleTemp, VehicleFlow flowByLsh, VehImpawnTemp impawnTemp, VehImpawn impawn, String dlr, String dlrdh) throws JRException, IOException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy年 MM月 dd日");
//        //1.读取.japser文件，构建输入流
//        ClassPathResource resource = new ClassPathResource("model/jdcdydjzybasqb.jasper");
//        InputStream inputStream = resource.getInputStream();
//        //2.构建Print对象，用于让模块结合数据
//        //第二个参数就是用来填充模板中的parameters
//        Map<String, Object> map = new HashMap<>();
//        if (StringUtils.isNotBlank(dlr)) {
//            //机动车所有人的代理人
////            map.put("syrdlryzbm", "邮政编码");
//            map.put("syrdlrmc", dlr);
////            map.put("syrdlryjdz", "邮寄地址");
//            map.put("syrdlrlxdh", dlrdh);
//        }
//        map.put("syfqzrq", format.format(new Date()));
//        if (ValidateHelper.isEmpty(impawnTemp)) {
//            //抵押权人/典当行
//            map.put("dyqryzbm", impawn.getYzbm());
//            map.put("dyqrxm", impawn.getDyqr());
//            map.put("dyqryjdz", impawn.getXxdz());
//            map.put("dyqrlxdh", impawn.getLxdh());
//        } else {
//            //抵押权人/典当行
//            map.put("dyqryzbm", impawnTemp.getYzbm());
//            map.put("dyqrxm", impawnTemp.getDyqr());
//            map.put("dyqryjdz", impawnTemp.getXxdz());
//            map.put("dyqrlxdh", impawnTemp.getLxdh());
//        }
//        map.put("dyqrqzrq", "年   月   日");
//        //抵押权人/典当行的代理人
////        map.put("dyqrdlryzbm", "邮政编码");
////        map.put("dyqrdlrxm", "姓名/名称");
////        map.put("dyqrdlryjdz", "邮寄地址");
////        map.put("dyqrdlrlxdh", "联系电话");
//        map.put("dyqrdlrqzrq", "年   月   日");
//        //表头
//        map.put("hpzl", flowByLsh.getHpzl());
//        map.put("hphm", flowByLsh.getHphm());
//        map.put("syrmc", flowByLsh.getSyr());
//        //sel:申请事项
//        if ("A".equals(flowByLsh.getYwyy())) {
//            //抵押登记
//            map.put("selsqdyj", "√");
//        } else if ("B".equals(flowByLsh.getYwyy())) {
//            //解除抵押登记
//            map.put("selsqjcdydj", "√");
//        } else if ("C".equals(flowByLsh.getYwyy())) {
//            //质押
//            map.put("selsqzy", "√");
//        } else if ("D".equals(flowByLsh.getYwyy())) {
//            //解除质押
//            map.put("selsqjczy", "√");
//        }
//
//        JasperPrint print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
//        //3.使用Exporter导出PDF
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            JasperExportManager.exportReportToPdfStream(print, outputStream);
//            return outputStream.toByteArray();
//        } finally {
//            outputStream.close();
//        }
//    }
//
//    /**
//     * 机动车牌证申请表
//     *
//     * @param vehicleTemp
//     * @param flowByLsh
//     * @param dlr
//     * @param dlrdh
//     * @return
//     * @throws JRException
//     * @throws IOException
//     */
//    public static byte[] createJdcpzsqb(VehicleTemp vehicleTemp, VehicleFlow flowByLsh, String dlr, String dlrdh) throws JRException, IOException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy年 MM月 dd日");
//        //1.读取.japser文件，构建输入流
//        ClassPathResource resource = new ClassPathResource("model/jdcpzsqb.jasper");
//        InputStream inputStream = resource.getInputStream();
//        //2.构建Print对象，用于让模块结合数据
//        //第二个参数就是用来填充模板中的parameters
//        Map<String, Object> map = new HashMap<>();
//        map.put("date", format.format(new Date()));
//        //机动车所有人
//        map.put("xm", StringUtils.isBlank(vehicleTemp.getSyr()) ? flowByLsh.getSyr() : vehicleTemp.getSyr());
//        map.put("yzbm", vehicleTemp.getYzbm1());
//        map.put("sjhm", vehicleTemp.getSjhm());
//        map.put("yjdz", vehicleTemp.getZsxxdz());
//        map.put("gddh", vehicleTemp.getLxdh());
//        //代理人
//        if (StringUtils.isNotBlank(dlr)) {
//            map.put("dlrxm", dlr);
//            map.put("dlrsjhm", dlrdh);
//        } else {
//            map.put("sjhm", StringUtils.isNotBlank(vehicleTemp.getSjhm()) ? vehicleTemp.getSjhm() : dlrdh);
//        }
//
//        map.put("hpzl", StringUtils.isBlank(vehicleTemp.getHpzl()) ? flowByLsh.getHpzl() : vehicleTemp.getHpzl());
//        map.put("hphm", StringUtils.isBlank(vehicleTemp.getHphm()) ? flowByLsh.getHphm() : vehicleTemp.getHphm());
//
//        //业务
//        String yw = flowByLsh.getYwlx() + flowByLsh.getYwyy();
//        map.put("sel" + yw, "√");
//
////        //号牌
////        map.put("selhpblds", "丢失");
////        map.put("selhpblms", "灭失");
////        map.put("selhpblqhp", "前号牌");
////        map.put("selhpblhhp", "后号牌");
////
////        map.put("selhphlqhp", "前号牌");
////        map.put("selxhphlhhp", "后号牌");
////
////        //行驶证
////        map.put("selxszblds", "丢失");
////        map.put("selxszblms", "灭失");
////        //登记证书
////
////        map.put("seldjzsblds", "丢失");
////        map.put("seldjzsblms", "灭失");
////        map.put("seldjzsblwhd", "未获得");
////
////        //检验合格标志
////        map.put("seljyhgdjdsl", "在登记地车辆管理所申请");
////        map.put("seljyhgwdsl", "在登记地以外车辆管理所申请");
////
////        map.put("seljyhgbzblds", "丢失");
////        map.put("seljyhgbzblms", "灭失");
//
//
//        JasperPrint print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
//        //3.使用Exporter导出PDF
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            JasperExportManager.exportReportToPdfStream(print, outputStream);
//            return outputStream.toByteArray();
//        } finally {
//            outputStream.close();
//        }
//    }
//
//    /**
//     * 校车标牌领取表
//     *
//     * @param schoolbusPermit
//     * @param schoolbusPermitDrivers
//     * @param flowByLsh
//     * @param dlr
//     * @param dlrdh
//     * @return
//     * @throws JRException
//     * @throws IOException
//     */
//    public static byte[] createXcbplqb(VehSchoolbusPermit schoolbusPermit, List<VehSchoolbusPermitDriver> schoolbusPermitDrivers, VehicleFlow flowByLsh, String dlr, String dlrdh) throws JRException, IOException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy年 MM月 dd日");
//        //1.读取.japser文件，构建输入流
//        ClassPathResource resource = new ClassPathResource("model/xcbplqb.jasper");
//        InputStream inputStream = resource.getInputStream();
//        //2.构建Print对象，用于让模块结合数据
//        //第二个参数就是用来填充模板中的parameters
//        Map<String, Object> map = new HashMap<>();
//
//        map.put("date", format.format(new Date()));
//        map.put("xcsyr", schoolbusPermit.getSyr());
//        map.put("fwxx", schoolbusPermit.getFwxx());
//        map.put("kxsj", schoolbusPermit.getKxsj());
//        map.put("xsxl", schoolbusPermit.getXsxl());
//        map.put("tkzd", schoolbusPermit.getTkzd());
//        map.put("hpzl", schoolbusPermit.getHpzl());
//        map.put("hphm", StringUtils.isNotBlank(schoolbusPermit.getHphm()) ? schoolbusPermit.getFzjg() + schoolbusPermit.getHphm() : null);
//        if (schoolbusPermitDrivers.size() > 0) {
//            int num = 3;
//            num = num > schoolbusPermitDrivers.size() ? schoolbusPermitDrivers.size() : num;
//            for (int i = 0; i < num; i++) {
//                int index = i + 1;
//                //校车驾驶人
//                map.put("jsr" + index + "xm", schoolbusPermitDrivers.get(i).getXm());
//                map.put("jsr" + index + "sfzhm", schoolbusPermitDrivers.get(i).getSfzmhm());
//                map.put("jsr" + index + "lxdh", schoolbusPermitDrivers.get(i).getLxdh());
//            }
//        }
//
//        map.put("zcdjsj", schoolbusPermit.getDjrq() == null ? null : format.format(schoolbusPermit.getDjrq()));
//        //核定载客人数
//        map.put("hdzkcrs", schoolbusPermit.getCrrs());
//        map.put("hdzkxss", schoolbusPermit.getXsrs());
//        //业务类型
//        //核发标牌
//        map.put("selywhfbp1", " ");
//        //换发标牌
//        map.put("selywhfbp2", " ");
//        //补发标牌
//        map.put("selywbfbp", " ");
//        //回收标牌
//        map.put("selywhsbp", " ");
//        //校车使用性质
//        if ("X1".equals(schoolbusPermit.getClyt())) {
//            //接送幼儿
//            map.put("selxzye", "√");
//        } else if ("X2".equals(schoolbusPermit.getClyt())) {
//            //接送小学生
//            map.put("selxzxxs", "√");
//        } else if ("X3".equals(schoolbusPermit.getClyt())) {
//            //接送中小学生
//            map.put("selxzzxxs", "√");
//        } else if ("X4".equals(schoolbusPermit.getClyt())) {
//            //接送初中生
//            map.put("selxzzxs", "√");
//        }
//        //校车类别
//        if ("1".equals(schoolbusPermit.getYtsx())) {
//            //专用
//            map.put("sellbzy", "√");
//        } else {
//            //非专用
//            map.put("sellbfzy", "√");
//        }
//        //校车外观标识
//        if ("0".equals(schoolbusPermit.getXcwgbz())) {
//            //未喷涂粘贴
//            map.put("selwgbswpt", "√");
//        } else {
//            //喷涂粘贴
//            map.put("selwgbspt", "√");
//        }
//        if (StringUtils.isNotBlank(dlr)) {
//            //代理人
////            map.put("dlryzbm", "邮政编码");
//            map.put("dlrxm", dlr);
////            map.put("dlryjdz", "邮寄地址");
//            map.put("dlrlxdh", dlrdh);
//        } else {
//            //领取人
////            map.put("yzbm", "邮政编码");
//            map.put("xm", schoolbusPermit.getSyr());
////            map.put("yjdz", "邮寄地址");
////            map.put("lxdh", "联系电话");
//        }
//
//
//        JasperPrint print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
//        //3.使用Exporter导出PDF
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            JasperExportManager.exportReportToPdfStream(print, outputStream);
//            return outputStream.toByteArray();
//        } finally {
//            outputStream.close();
//        }
//    }
//
//    /**
//     * 机动车检验标志申请表
//     *
//     * @param vehicleTemp
//     * @param flowByLsh
//     * @param dlr
//     * @param dlrdh
//     * @return
//     * @throws JRException
//     * @throws IOException
//     */
//    public static byte[] createJdcjybzsqb(VehicleTemp vehicleTemp, VehicleFlow flowByLsh, String dlr, String dlrdh) throws JRException, IOException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy年 MM月 dd日");
//        //1.读取.japser文件，构建输入流
//        ClassPathResource resource = new ClassPathResource("model/jdcjybzsqb.jasper");
//        InputStream inputStream = resource.getInputStream();
//        //2.构建Print对象，用于让模块结合数据
//        //第二个参数就是用来填充模板中的parameters
//        Map<String, Object> map = new HashMap<>();
//
//        //所有人
//        map.put("xm", StringUtils.isBlank(vehicleTemp.getSyr()) ? flowByLsh.getSyr() : vehicleTemp.getSyr());
//        map.put("yzbm", vehicleTemp.getYzbm1());
//        map.put("sjhm", vehicleTemp.getSjhm());
//        map.put("yjdz", vehicleTemp.getZsxxdz());
//        map.put("gddh", vehicleTemp.getLxdh());
//        //代理人
//        if (StringUtils.isNotBlank(dlr)) {
//            map.put("dlrxm", dlr);
//            map.put("dlrsjhm", dlrdh);
//        } else {
//            map.put("sjhm", StringUtils.isNotBlank(vehicleTemp.getSjhm()) ? vehicleTemp.getSjhm() : dlrdh);
//        }
//        map.put("jdchphm", StringUtils.isBlank(vehicleTemp.getHphm()) ? flowByLsh.getHphm() : vehicleTemp.getHphm());
//        map.put("date", format.format(new Date()));
//
//
//        JasperPrint print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
//        //3.使用Exporter导出PDF
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            JasperExportManager.exportReportToPdfStream(print, outputStream);
//            return outputStream.toByteArray();
//        } finally {
//            outputStream.close();
//        }
//    }
//
//    /**
//     * 机动车驾驶证申请表
//     *
//     * @param vioDriver
//     * @param licenseFlow
//     * @param dlr
//     * @param dlrdh
//     * @param dlrsfz
//     * @return
//     * @throws JRException
//     * @throws IOException
//     */
//    public static byte[] createJdcjszsqb(VioDriver vioDriver, LicenseFlow licenseFlow, String dlr, String dlrdh, String dlrsfz) throws JRException, IOException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy年 MM月 dd日");
//        //1.读取.japser文件，构建输入流
//        ClassPathResource resource = new ClassPathResource("model/jdcjszsqb.jasper");
//        InputStream inputStream = resource.getInputStream();
//        //2.构建Print对象，用于让模块结合数据
//        //第二个参数就是用来填充模板中的parameters
//        Map<String, Object> map = new HashMap<>();
//
//        map.put("dabh", vioDriver.getDabh());
//        //申请人信息
//        map.put("sqrxm", licenseFlow.getXm());
//        map.put("sqrxb", vioDriver.getXb());
//        map.put("sqrcsrq", vioDriver.getCsrq() == null ? null : vioDriver.getCsrq().replace(".0", ""));
//        if ("A".equals(vioDriver.getSfzmmc())) {
//            map.put("sqrsfzmmc1", "身份证");
//        }
////        map.put("sqrsfzmmc2", "身份证明名称2");
//        map.put("sqryjdz", vioDriver.getLxzsxxdz());
//        map.put("sqrgddh", vioDriver.getLxdh());
//        map.put("sqryddh", vioDriver.getSjhm());
//        map.put("sqrdzyx", vioDriver.getDzyx());
//        map.put("sqryzbm", vioDriver.getLxzsyzbm());
//        String yw = licenseFlow.getYwlx() + licenseFlow.getYwyy();
//
//
//        switch (yw) {
//            case "BA":
//                //增加准驾车型
//                map.put("selzjzjcx", "√");
//                //驾校培训
//                map.put("selzjzjjxpx", "√");
////                //最高准驾车型被注销
////                map.put("selzjzjzgbzx", " ");
////                //全日制职业教育
////                map.put("selzjzjqrzjy", " ");
//                break;
//            case "BY":
//                //增加准驾车型
//                map.put("selzjzjcx", "√");
//                //自学直考
//                map.put("selzjzjzxzk", "√");
//                break;
//            case "BB":
//                //增加准驾车型
//                map.put("selzjzjcx", "√");
//                //驾校培训
//                map.put("selzjzjjxpx", "√");
//                break;
//            case "CA":
//                //有效期满换证
//                map.put("selyxqmhz", "√");
//                break;
//            case "CB":
//                //转入换证
//                map.put("selzrhz", "√");
//                break;
//            case "CC":
//                //达到规定年龄换证
//                map.put("selddgdnlhz", "√");
//                map.put("ghzjcxdh", licenseFlow.getZjcx());
//                break;
//            case "CD":
//                //自愿降低准假车型换证
//                map.put("selzyjdzjcxhz", "√");
//                map.put("ghzjcxdh", licenseFlow.getZjcx());
//                break;
//            case "CE":
//                //信息变化换证
//                map.put("selxxbhhz", "√");
//                break;
//            case "CF":
//                //补证
//                map.put("selbz", "√");
//                //丢失
//                map.put("selbzds", "√");
//                break;
//            case "CG":
//                //证件损坏换证
//                map.put("selzjshhz", "√");
//                break;
//            case "CZ":
//                //补证
//                map.put("selbz", "√");
//                //其他
//                map.put("selbzqt", "√");
//                break;
//            case "CJ":
//                //因身体条件变化降低准驾车型换证
//                map.put("selysttjbhjdzjcxhz", "√");
//                map.put("ghzjcxdh", licenseFlow.getZjcx());
//                break;
//            case "CK":
//                //注销最高准驾车型
//                map.put("selzxzg", "√");
//                break;
//            case "CL":
//                //注销实习准驾车型
//                map.put("selzxsx", "√");
//                break;
//            case "GA":
//                //注销
//                map.put("selzx", "√");
//                //本人申请
//                map.put("selbrsq", "√");
//                //丧失民事行为能力
//                map.put("selssmsnl", " ");
//                break;
//            case "GC":
//                //注销
//                map.put("selzx", "√");
//                //身体条件不适合
//                map.put("selsttjbsh", "√");
//                break;
//            case "GD":
//                //注销
//                map.put("selzx", "√");
//                //死亡
//                map.put("selsw", "√");
//                break;
//            case "GI":
//                //注销
//                map.put("selzx", "√");
//                //其他
//                map.put("selzxqt", "√");
//                break;
//            case "GO":
//                //注销
//                map.put("selzx", "√");
//                //丧失民事行为能力
//                map.put("selssmsnl", "√");
//                break;
//            case "SA":
//                //延期换证
//                map.put("selyqhz", "√");
////                //服兵役
////                map.put("selfby", " ");
////                //出国（境）
////                map.put("selcg", " ");
////                //其他
////                map.put("selqt", " ");
//                break;
//            case "SE":
//                //延期审验
//                map.put("selyqsj", "√");
//                break;
//            case "SC":
//                //延期提交身体条件证明
//                map.put("selyqtjstzm", "√");
//                break;
//            case "UA":
//                //恢复驾驶资格
//                map.put("selhfjszg", "√");
//                //超过有效期一年以上未换证被注销未满两年
//                map.put("selgq", "√");
//                map.put("hfzjcxdh", licenseFlow.getZjcx());
//                break;
//            case "UB":
//                //恢复驾驶资格
//                map.put("selhfjszg", "√");
//                //未按规定提交体检证明被注销未满两年
//                map.put("selwtjzm", "√");
//                map.put("hfzjcxdh", licenseFlow.getZjcx());
//                break;
//            default:
//        }
////        //业务
////        map.put("sqzjcxdh", "申请申领的准假车型代号");
////        //换证
////        map.put("hzgbsx", "变更事项");
////        map.put("hzgbnr", "变更内容");
////        //备案
////        map.put("bacydw", "从业单位");
//        //委托代理人监护人信息
//        map.put("dljhrxm", dlr);
//        map.put("dljhrsfzmmc", StringUtils.isBlank(dlr) ? null : "身份证");
//        map.put("dljhrsfzmhm", dlrsfz);
////        map.put("dljhrlxdz", "联系地址");
//        map.put("dljhrlxdh", dlrdh);
//        map.put("wtr", dlr);
//        if (vioDriver.getSfzmhm() != null) {
//            char[] chars = vioDriver.getSfzmhm().toCharArray();
//            //身份证明1号码
//            for (int i = 0; i < chars.length; i++) {
//                int index = i + 1;
//                map.put("sfzmhm_" + index, String.valueOf(chars[i]));
//            }
//        }
//        //身份证明2号码
////        map.put("sfzmhm1_1", " ");
////        map.put("sfzmhm1_2", " ");
////        map.put("sfzmhm1_3", " ");
////        map.put("sfzmhm1_4", " ");
////        map.put("sfzmhm1_5", " ");
////        map.put("sfzmhm1_6", " ");
////        map.put("sfzmhm1_7", " ");
////        map.put("sfzmhm1_8", " ");
////        map.put("sfzmhm1_9", " ");
////        map.put("sfzmhm1_10", " ");
////        map.put("sfzmhm1_11", " ");
////        map.put("sfzmhm1_12", " ");
////        map.put("sfzmhm1_13", " ");
////        map.put("sfzmhm1_14", " ");
////        map.put("sfzmhm1_15", " ");
////        map.put("sfzmhm1_16", " ");
////        map.put("sfzmhm1_17", " ");
////        map.put("sfzmhm1_18", " ");
////        //业务
////        //初次申领
////        map.put("selccsl", "");
////        //驾校培训
////        map.put("selccsljxpx", " ");
////        //有驾驶经历
////        map.put("selccslyjsjl", " ");
////        //自学直考
////        map.put("selccslzxzk", " ");
//
////
////        //持军警驾驶证申领
////        map.put("selcjjjszsl", "");
////        //军队驾驶证
////        map.put("seljdjsz", " ");
////        //武警驾驶证
////        map.put("selwjjsz", " ");
////
////        //持境外驾驶证申领
////        map.put("selcjwjszsl", " ");
////        //台湾驾驶证
////        map.put("seltwjsz", " ");
////        //澳门驾驶证
////        map.put("selamjsz", " ");
////        //香港驾驶证
////        map.put("selxgjsz", " ");
////        //外国驾驶证
////        map.put("selwgjsz", " ");
////
////
////
////        //信息备案
////        map.put("selxxba", " ");
//
////        //连续三个记分周期不参加审验
////        map.put("sellxszq", " ");
////        //发生交通事故造成人员死亡，承担同等以上责任
////        map.put("seljtsgzs", " ");
////        //记满12分
////        map.put("selmsef", " ");
////        //延长的实习期内再次记6分以上但未达到12分
////        map.put("selsxqlf", " ");
//        if (StringUtils.isNotBlank(dlr)) {
//            //委托
//            map.put("selwt", "√");
//            map.put("dlwtrqzrq", format.format(new Date()));
//        } else {
//            //本人申请
//            map.put("selbrsq1", "√");
//            map.put("sqrqzrq", format.format(new Date()));
//        }
//        //申请方式
//        //监护人申请
////        map.put("seljhrsq", " ");
//
//
//        JasperPrint print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
//        //3.使用Exporter导出PDF
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            JasperExportManager.exportReportToPdfStream(print, outputStream);
//            return outputStream.toByteArray();
//        } finally {
//            outputStream.close();
//        }
//    }
//
//    public static byte[] createJdcjszsqb(DrvTemp drvTemp, LicenseFlow licenseFlow, String dlr, String dlrdh, String dlrsfz) throws JRException, IOException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy年 MM月 dd日");
//        //1.读取.japser文件，构建输入流
//        ClassPathResource resource = new ClassPathResource("model/jdcjszsqb.jasper");
//        InputStream inputStream = resource.getInputStream();
//        //2.构建Print对象，用于让模块结合数据
//        //第二个参数就是用来填充模板中的parameters
//        Map<String, Object> map = new HashMap<>();
//
//        map.put("dabh", drvTemp.getDabh());
//        //申请人信息
//        map.put("sqrxm", licenseFlow.getXm());
//        map.put("sqrxb", drvTemp.getXb());
//        map.put("sqrcsrq", drvTemp.getCsrq() != null ? format.format(drvTemp.getCsrq()) : null);
//        map.put("sqrsfzmmc1", "身份证");
////        map.put("sqrsfzmmc2", "身份证明名称2");
//        map.put("sqryjdz", drvTemp.getLxzsxxdz());
//        map.put("sqrgddh", drvTemp.getLxdh());
//        map.put("sqryddh", drvTemp.getSjhm());
//        map.put("sqrdzyx", drvTemp.getDzyx());
//        map.put("sqryzbm", drvTemp.getLxzsyzbm());
//        String yw = licenseFlow.getYwlx() + licenseFlow.getYwyy();
//
//
//        switch (yw) {
//            case "AA":
//                //初次申领
//                map.put("selccsl", "√");
//                //驾校培训
//                map.put("selccsljxpx", "√");
//                break;
//            case "AB":
//                //初次申领
//                map.put("selccsl", "√");
//                //驾校培训
//                map.put("selccsljxpx", "√");
//                break;
//            case "AX":
//                //初次申领
//                map.put("selccsl", "√");
//                //自学直考
//                map.put("selccslzxzk", "√");
//                break;
//            case "AD":
//                //持军警驾驶证申领
//                map.put("selcjjjszsl", "√");
//                break;
//            case "AE":
//                //持境外驾驶证申领
//                map.put("selcjwjszsl", "√");
//                //外国驾驶证
//                map.put("selwgjsz", "√");
//                break;
//            case "AF":
//                //持境外驾驶证申领
//                map.put("selcjwjszsl", "√");
//                //香港驾驶证
//                map.put("selxgjsz", "√");
//                break;
//            case "AH":
//                //持境外驾驶证申领
//                map.put("selcjwjszsl", "√");
//                //台湾驾驶证
//                map.put("seltwjsz", "√");
//                break;
//            case "AI":
//                //持境外驾驶证申领
//                map.put("selcjwjszsl", "√");
//                break;
//            default:
//        }
//
//        //委托代理人监护人信息
//        map.put("dljhrxm", dlr);
//        map.put("dljhrsfzmmc", StringUtils.isBlank(dlr) ? null : "身份证");
//        map.put("dljhrsfzmhm", dlrsfz);
////        map.put("dljhrlxdz", "联系地址");
//        map.put("dljhrlxdh", dlrdh);
//        map.put("wtr", dlr);
//        if (licenseFlow.getSfzmhm() != null) {
//            char[] chars = licenseFlow.getSfzmhm().toCharArray();
//            //身份证明1号码
//            for (int i = 0; i < chars.length; i++) {
//                int index = i + 1;
//                map.put("sfzmhm_" + index, String.valueOf(chars[i]));
//            }
//        }
////        //有驾驶经历
////        map.put("selccslyjsjl", " ");
//
////
////        //持军警驾驶证申领
////        map.put("selcjjjszsl", "");
////        //军队驾驶证
////        map.put("seljdjsz", " ");
////        //武警驾驶证
////        map.put("selwjjsz", " ");
////
////        //持境外驾驶证申领
////        map.put("selcjwjszsl", " ");
////        //澳门驾驶证
////        map.put("selamjsz", " ");
//
//
//        if (StringUtils.isNotBlank(drvTemp.getJhrxm())) {
//            //监护人申请
//            map.put("seljhrsq", "√");
//            map.put("dlwtrqzrq", format.format(new Date()));
//        } else if (StringUtils.isNotBlank(dlr)) {
//            //委托
//            map.put("selwt", "√");
//            map.put("dlwtrqzrq", format.format(new Date()));
//        } else {
//            //本人申请
//            map.put("selbrsq1", "√");
//            map.put("sqrqzrq", format.format(new Date()));
//        }
//
//
//        JasperPrint print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
//        //3.使用Exporter导出PDF
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            JasperExportManager.exportReportToPdfStream(print, outputStream);
//            return outputStream.toByteArray();
//        } finally {
//            outputStream.close();
//        }
//    }
//
//}
