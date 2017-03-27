package com.center.platform.utils;

import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public final class Utils
{
    public static final int REGION_CODE_MAX_LENGTH = 32;

    /**
     * 获取随机数 32
     * @return
     */
    public static final String getRandomString()
    {
        int length = 32;
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取时间
     * @param o
     * @return
     */
    public static java.sql.Date getStatesTime(int o) {
        Calendar cal = Calendar.getInstance();

        java.util.Date date = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String time = format.format(date);
        try {
            java.util.Date d = format.parse(time);
            cal.setTime(date);
            cal.add(5, o);
            d = cal.getTime();
            return new java.sql.Date(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取时间戳
     * @return
     */
    public static String getDatechar(){
        String time = "";
        Calendar cal = Calendar.getInstance();

        java.util.Date date = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        time = format.format(date);

        return time;
    }

    /**
     * date 转字符串
     * @param date
     * @return
     */
    public static  String getDateStr(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static int getDifferDate(java.sql.Date sourceDate, java.sql.Date disctDate) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        double d = sourceDate.getTime() - disctDate.getTime();
        double dif = d / 86400000.0D;
        double x = Math.floor(dif);
        return new Double(x).intValue();
    }

    public static String getPYIndexStr(String strChinese, boolean bUpCase) {
        try {
            StringBuffer buffer = new StringBuffer();
            byte[] b = strChinese.getBytes("GBK");
            int i = 0;
            if ((b[i] & 0xFF) > 128) {
                int char1 = b[(i++)] & 0xFF;
                char1 <<= 8;
                int chart = char1 + (b[i] & 0xFF);
                buffer.append(getPYIndexChar((char)chart, bUpCase));
            }
            return buffer.toString();
        } catch (Exception e) {
            System.out.println("取中文拼音有错" + e.getMessage());
        }
        return null;
    }

    public static String getPYIndexStr1(String strChinese, boolean bUpCase) {
        try {
            StringBuffer buffer = new StringBuffer();
            byte[] b = strChinese.getBytes("GBK");
            for (int i = 0; i < b.length; i++) {
                if ((b[i] & 0xFF) > 128) {
                    int char1 = b[(i++)] & 0xFF;
                    char1 <<= 8;
                    int chart = char1 + (b[i] & 0xFF);
                    buffer.append(getPYIndexChar((char)chart, bUpCase));
                }
            }
            return buffer.toString();
        } catch (Exception e) {
            System.out.println("取中文拼音有错" + e.getMessage());
        }
        return null;
    }



    private static char getPYIndexChar(char strChinese, boolean bUpCase) {
        int charGBK = strChinese;
        char result;
        if ((charGBK >= 45217) && (charGBK <= 45252)) {
            result = 'A';
        }
        else
        {
            if ((charGBK >= 45253) && (charGBK <= 45760)) {
                result = 'B';
            }
            else
            {
                if ((charGBK >= 45761) && (charGBK <= 46317)) {
                    result = 'C';
                }
                else
                {
                    if ((charGBK >= 46318) && (charGBK <= 46825)) {
                        result = 'D';
                    }
                    else
                    {
                        if ((charGBK >= 46826) && (charGBK <= 47009)) {
                            result = 'E';
                        }
                        else
                        {
                            if ((charGBK >= 47010) && (charGBK <= 47296)) {
                                result = 'F';
                            }
                            else
                            {
                                if ((charGBK >= 47297) && (charGBK <= 47613)) {
                                    result = 'G';
                                }
                                else
                                {
                                    if ((charGBK >= 47614) && (charGBK <= 48118)) {
                                        result = 'H';
                                    }
                                    else
                                    {
                                        if ((charGBK >= 48119) && (charGBK <= 49061)) {
                                            result = 'J';
                                        }
                                        else
                                        {
                                            if ((charGBK >= 49062) && (charGBK <= 49323)) {
                                                result = 'K';
                                            }
                                            else
                                            {
                                                if ((charGBK >= 49324) && (charGBK <= 49895)) {
                                                    result = 'L';
                                                }
                                                else
                                                {
                                                    if ((charGBK >= 49896) && (charGBK <= 50370)) {
                                                        result = 'M';
                                                    }
                                                    else
                                                    {
                                                        if ((charGBK >= 50371) && (charGBK <= 50613)) {
                                                            result = 'N';
                                                        }
                                                        else
                                                        {
                                                            if ((charGBK >= 50614) && (charGBK <= 50621)) {
                                                                result = 'O';
                                                            }
                                                            else
                                                            {
                                                                if ((charGBK >= 50622) && (charGBK <= 50905)) {
                                                                    result = 'P';
                                                                }
                                                                else
                                                                {
                                                                    if ((charGBK >= 50906) && (charGBK <= 51386)) {
                                                                        result = 'Q';
                                                                    }
                                                                    else
                                                                    {
                                                                        if ((charGBK >= 51387) && (charGBK <= 51445)) {
                                                                            result = 'R';
                                                                        }
                                                                        else
                                                                        {
                                                                            if ((charGBK >= 51446) && (charGBK <= 52217)) {
                                                                                result = 'S';
                                                                            }
                                                                            else
                                                                            {
                                                                                if ((charGBK >= 52218) && (charGBK <= 52697)) {
                                                                                    result = 'T';
                                                                                }
                                                                                else
                                                                                {
                                                                                    if ((charGBK >= 52698) && (charGBK <= 52979)) {
                                                                                        result = 'W';
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        if ((charGBK >= 52980) && (charGBK <= 53688)) {
                                                                                            result = 'X';
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            if ((charGBK >= 53689) && (charGBK <= 54480)) {
                                                                                                result = 'Y';
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                if ((charGBK >= 54481) && (charGBK <= 55289))
                                                                                                    result = 'Z';
                                                                                                else
                                                                                                    result = (char)(65 + new Random().nextInt(25));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!bUpCase)
            result = Character.toLowerCase(result);
        return result;
    }

    public static Map<String, String> sortMapByKey(Map<String, String> map)
    {
        if ((map == null) || (map.isEmpty())) {
            return null;
        }
        Map sortMap = new TreeMap(new MapKeyComparator());

        sortMap.putAll(map);
        return sortMap;
    }

    public static List MapToList(Map map) {
        if (map == null) return null;
        List lst = new ArrayList();
        for (Iterator i$ = map.keySet().iterator(); i$.hasNext(); ) { Object s = i$.next();
            Map tmpMap = new HashMap();
            tmpMap.put("key", s);
            tmpMap.put("value", map.get(s));
            lst.add(tmpMap);
        }
        return lst;
    }

    public static String EncoderByMd5(String str)
            throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
        MessageDigest messageDigest =MessageDigest.getInstance("MD5");
        // 输入的字符串转换成字节数组
        byte[] inputByteArray = str.getBytes();
        // inputByteArray是输入字符串转换得到的字节数组
        messageDigest.update(inputByteArray);
        // 转换并返回结果，也是字节数组，包含16个元素
        byte[] resultByteArray = messageDigest.digest();
        // 字符数组转换成字符串返回
        return byteArrayToHex(resultByteArray);
    }
    public static String byteArrayToHex(byte[] byteArray) {

        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray =new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    /**
     * 按指定高度 等比例缩放图片
     *
     * @param imageFile
     * @param newPath
     * @param newHeight 新图的宽度
     * @throws IOException
     */
    public static void zoomImageScale(File imageFile, String newPath, int newHeight) throws IOException {

        File folder = new File(newPath.substring(0,newPath.lastIndexOf("\\")));
        if (!folder.exists() || !folder.isDirectory()) folder.mkdirs();

        File  file = new File(newPath);
        if (file.exists()) {
            file.delete();
            //file = new File(path.concat("/" + reNameFile(name)));
        }
        file.createNewFile();

        if(!imageFile.canRead())
            return;
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        if (null == bufferedImage)
            return;

        int originalWidth = bufferedImage.getWidth();
        int originalHeight = bufferedImage.getHeight();
        double scale = (double)originalHeight / (double)newHeight;    // 缩放的比例
        int newWidth =  (int)(originalWidth / scale);
        zoomImageUtils(imageFile, newPath, bufferedImage, newWidth, newHeight);
    }

    private static void zoomImageUtils(File imageFile, String newPath, BufferedImage bufferedImage, int width, int height)
            throws IOException {

        String suffix = StringUtils.substringAfterLast(imageFile.getName(), ".");
        // 处理 png 背景变黑的问题
        if(suffix != null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"))){
            BufferedImage to= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = to.createGraphics();
            to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g2d.dispose();

            g2d = to.createGraphics();
            Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();

            ImageIO.write(to, suffix, new File(newPath));
        }else{
            BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
            Graphics g = newImage.getGraphics();
            g.drawImage(bufferedImage, 0, 0, width, height, null);
            g.dispose();
            ImageIO.write(newImage, suffix, new File(newPath));
        }
    }

}