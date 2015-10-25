package com.liaojh.demo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author LiaoJH
 * @DATE 15/10/13
 * @VERSION 1.0
 * @DESC TODO
 */
public class IOUtils {
    /**
     * 默认缓冲大小
     */
    public static final int BUFF_SIZE = 8 * 1024;

    /**
     * InputString --->OutputStream
     *
     * @param in
     * @param out
     * @return
     */
    public static boolean inputStringToOutputStream(InputStream in, OutputStream out) {
        BufferedOutputStream bos = null;
        BufferedInputStream bin = null;
        try {
            bin = new BufferedInputStream(in, BUFF_SIZE);
            bos = new BufferedOutputStream(out, BUFF_SIZE);
            int b;
            while ((b = bin.read()) != -1) {
                bos.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
//            close(bos);
//            close(bin);
        }
        return false;
    }


    /**
     * 关流
     * @param os
     */
    public static void close(Closeable os) {
        try {
            if (os != null) {
                os.close();
                os = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
