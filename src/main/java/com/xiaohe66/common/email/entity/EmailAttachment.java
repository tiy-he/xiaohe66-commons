package com.xiaohe66.common.email.entity;

import lombok.Data;

import java.io.File;

/**
 * 表示邮件中的一个附件
 *
 * @author xiaohe
 * @time 2020.03.05 11:18
 */
@Data
public class EmailAttachment {

    /**
     * 附件的名称，这个名称会显示在邮件中
     */
    private final String fileName;

    private final File file;

    public EmailAttachment(String fileName, File file) {
        this.fileName = fileName;
        this.file = file;
    }

    public EmailAttachment(String fileName, String path) {
        this(fileName, new File(path));
    }
}
