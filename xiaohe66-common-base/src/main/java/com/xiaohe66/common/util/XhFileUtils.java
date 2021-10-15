package com.xiaohe66.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author xiaohe
 * @time 2020.04.10 09:50
 */
public class XhFileUtils {

    private XhFileUtils() {

    }

    /**
     * 从classPath读取文件，无法读取test中的文件
     *
     * @param path 路径，例：com/xiaohe66/common/testExcel.xlsx
     * @return 找到返回File，未找到返回null
     */
    public static File readInClasspath(String path) {
        Objects.requireNonNull(path, "path cannot be null");

        ClassLoader classLoader = XhFileUtils.class.getClassLoader();
        URL resource = classLoader.getResource(path);

        if (resource != null) {
            return new File(resource.getFile());
        }

        return null;
    }

}
