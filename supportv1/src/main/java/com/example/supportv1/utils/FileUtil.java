package com.example.supportv1.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil
{
    private static final String TAG = "FileUtil";

    /**
     * 将数据写入一个文件
     * 
     * @param destFilePath
     *            文件名
     * @param data
     *            字节数组
     * @return boolean
     */
    public static boolean writeFile(String destFilePath, byte[] data)
    {
        FileOutputStream fos = null;
        try
        {
            if (!createFile(destFilePath))
            {
                return false;
            }
            fos = new FileOutputStream(destFilePath);
            fos.write(data);
            fos.flush();
            return true;
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != fos)
                {
                    fos.close();
                    fos = null;
                }
            }
            catch (Exception e)
            {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将数据写入一个文件
     * 
     * @param destFilePath
     *            文件名
     * @param in
     *            输入流
     * @return boolean
     */
    public static boolean writeFile(String destFilePath, InputStream in)
    {
        FileOutputStream fos = null;
        try
        {
            if (!createFile(destFilePath))
            {
                return false;
            }
            fos = new FileOutputStream(destFilePath);
            int readCount = 0;
            int len = 1024;
            byte[] buffer = new byte[len];
            while ((readCount = in.read(buffer)) != -1)
            {
                fos.write(buffer, 0, readCount);
            }
            fos.flush();
            return true;
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != fos)
                {
                    fos.close();
                    fos = null;
                }
                if (null != in)
                {
                    in.close();
                    in = null;
                }
            }
            catch (IOException e)
            {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 读取文件，返回以byte数组形式的数据
     * 
     * @param filePath
     *            文件名
     * @return byte[]
     */
    public static byte[] readFile(String filePath)
    {
        FileInputStream fi = null;
        try
        {
            if (isFileExist(filePath))
            {
                fi = new FileInputStream(filePath);
                return readInputStream(fi);
            }
        }
        catch (FileNotFoundException e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (fi != null)
                {
                    fi.close();
                }
            }
            catch (IOException e)
            {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 从一个数据流里读取数据,返回以byte数组形式的数据
     * 
     * @param in
     *            输入流
     * @return byte[]
     */
    public static byte[] readInputStream(InputStream in)
    {
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1)
            {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException e)
            {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 判断文件是否存在
     * 
     * @param filePath
     *            文件名
     * @return boolean
     */
    public static boolean isFileExist(String filePath)
    {
        try
        {
            File file = new File(filePath);
            return file.exists();
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建文件
     * 
     * @param filePath
     *            文件名
     * @return boolean
     */
    public static boolean createFile(String filePath)
    {
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                if (!file.getParentFile().exists())
                {
                    file.getParentFile().mkdirs();
                }

                return file.createNewFile();
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除一个文件
     * 
     * @param filePath
     *            文件名
     * @return boolean
     */
    public static boolean deleteFile(String filePath)
    {
        try
        {
            File file = new File(filePath);
            if (file.exists())
            {
                return file.delete();
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除dir目录下的所有文件，包括删除文件夹
     * 
     * @param dir
     *            文件夹名称
     * @return boolean
     */
    public static boolean deleteDirectory(File dir)
    {
        try
        {
            if (dir.isDirectory())
            {
                File[] listFiles = dir.listFiles();
                for (int i = 0; i < listFiles.length; i++)
                {
                    deleteDirectory(listFiles[i]);
                }
            }
            dir.delete();
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 将一个文件或文件夹拷贝到另外一个地方
     * 
     * @param sourceFile
     *            源文件
     * @param destFile
     *            目标文件即拷贝到的新路径
     * @param shouldOverlay
     *            是否覆盖：true覆盖 false不覆盖
     * @return boolean
     */
    public static boolean copyFiles(String sourceFile, String destFile, boolean shouldOverlay)
    {
        try
        {
            if (shouldOverlay)
            {
                deleteFile(destFile);
            }
            FileInputStream fis = new FileInputStream(sourceFile);
            writeFile(destFile, fis);
            return true;
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}
