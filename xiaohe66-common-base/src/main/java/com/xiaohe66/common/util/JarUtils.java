package com.xiaohe66.common.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author xiaohe
 * @time 2020.06.23 11:42
 */
public class JarUtils {

    private JarUtils() {

    }

    public static InputStream read(String path) {
        return JarUtils.class.getClassLoader().getResourceAsStream(path);
    }

    public static String readAsString(String path) throws IOException {
        return IOUtils.toString(read(path), StandardCharsets.UTF_8);
    }

    /**
     * 从jar包中读取所有文件（不包括文件夹）的路径
     *
     * @param jarPath jar包的绝对路径
     * @return List<String>
     * @throws IOException IOException
     */
    public static List<String> readFilePath(String jarPath) throws IOException {

        try (JarFile jarFile = new JarFile(jarPath)) {

            List<String> pathList = new ArrayList<>();

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (!jarEntry.isDirectory()) {
                    pathList.add(jarEntry.getName());
                }
            }

            return pathList;
        }
    }
}
