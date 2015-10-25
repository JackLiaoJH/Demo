package com.liaojh.demo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.PortUnreachableException;
import java.net.URL;

/**
 * @author LiaoJH
 * @DATE 15/10/13
 * @VERSION 1.0
 * @DESC TODO
 */
public class HttpUtil {

    public static final int HTTP_OK = 200;


    public static InputStream getInputStringFromUrl(String urlString) {
        HttpURLConnection urlConnection = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HTTP_OK) {
                return urlConnection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//                urlConnection = null;
//            }
//        }
        return null;
    }

    public static boolean writeToDiskCache(InputStream in, OutputStream out) {
//        BufferedOutputStream bos = null;
//        BufferedInputStream bin = null;
//        try {
//            bin = new BufferedInputStream(in, IOUtils.BUFF_SIZE);
//            bos = new BufferedOutputStream(out, IOUtils.BUFF_SIZE);
//            int b;
//            while ((b = bin.read()) != -1) {
//                bos.write(b);
//            }
//
//            return BitmapFactory.decodeStream(in);
//        } catch (final IOException e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.close(bos);
//            IOUtils.close(bin);
//        }
//        return null;
        return IOUtils.inputStringToOutputStream(in,out);
    }


    public static boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
