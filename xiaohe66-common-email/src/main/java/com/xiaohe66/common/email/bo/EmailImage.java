package com.xiaohe66.common.email.bo;

import lombok.Data;

import java.io.File;

/**
 * @author xiaohe
 * @time 2020.05.25 14:49
 */
@Data
public class EmailImage {

    private String url;
    private File imageFile;

    public EmailImage(String url, File imageFile) {
        this.url = url;
        this.imageFile = imageFile;
    }

    public EmailImage(String url, String path) {
        this(url, new File(path));
    }

}
