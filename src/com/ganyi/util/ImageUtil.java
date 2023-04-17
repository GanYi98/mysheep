package com.ganyi.util;

import java.awt.*;

/**
 * 获得Image对象
 */
public class ImageUtil {

    public static Image get(String fileName) {
        //调用java.awt包中Toolkit工具类来获得图像
        return Toolkit.getDefaultToolkit().getImage("imgs\\" + fileName);
    }
}
