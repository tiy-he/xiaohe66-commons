package com.xiaohe66.common.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author xiaohe
 * @time 2021.07.19 22:22
 */
public class ZipUtils {

    private ZipUtils() {

    }

    public static void zip(String sourcePath, String targetPath) throws IOException {

        File targetFile = new File(targetPath);
        File sourceFile = new File(sourcePath);

        zip(sourceFile, targetFile);
    }

    public static void zip(File sourceFile, File targetFile) throws IOException {

        try (FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {

            zipDirectory(sourceFile, zipOutputStream, sourceFile.getName());
        }
    }

    public static void zip(File file, OutputStream outputStream) throws IOException {

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {

            zipDirectory(file, zipOutputStream, file.getName());
        }
    }

    private static void zipDirectory(File sourceFile, ZipOutputStream zipOutputStream, String name) throws IOException {

        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zipOutputStream.putNextEntry(new ZipEntry(name));

            try (FileInputStream inputStream = new FileInputStream(sourceFile)) {
                IOUtils.copy(inputStream, zipOutputStream);
            }
            zipOutputStream.closeEntry();

        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles != null && listFiles.length != 0) {

                for (File file : listFiles) {
                    // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                    // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                    zipDirectory(file, zipOutputStream, name + "/" + file.getName());
                }
            }
        }
    }

    public static void unZip(String sourcePath, String targetPath) throws IOException {
        File file = new File(sourcePath);
        unZip(file, targetPath);
    }

    public static void unZip(InputStream inputStream, String targetPath) throws IOException {

        Path tmpPath = Files.createTempFile("zipUtilsFile", ".zip");
        File file = tmpPath.toFile();

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            IOUtils.copy(inputStream, fileOutputStream);

            unZip(file, targetPath);

        } finally {
            Files.delete(tmpPath);
        }
    }

    public static void unZip(File sourceFile, String targetPath) throws IOException {

        try (ZipFile zipFile = new ZipFile(sourceFile);
             FileInputStream inputStream = new FileInputStream(sourceFile);
             ZipInputStream zipInputStream = new ZipInputStream(inputStream);) {

            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {

                File outFile = new File(targetPath + File.separator + entry.getName());

                File absoluteFile = outFile.getParentFile();
                if (!absoluteFile.exists() && !absoluteFile.mkdirs()) {
                    throw new IOException("cannot create directory : " + absoluteFile.getPath());
                }

                if (!outFile.createNewFile()) {
                    throw new IOException("cannot create file : " + outFile.getPath());
                }

                try (FileOutputStream fileOutputStream = new FileOutputStream(outFile)) {

                    InputStream zipFileInputStream = zipFile.getInputStream(entry);

                    IOUtils.copy(zipFileInputStream, fileOutputStream);
                }
            }
        }
    }
}
