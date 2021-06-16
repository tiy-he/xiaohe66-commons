package com.xiaohe66.common.ppt.template;

import org.apache.poi.ooxml.util.PackageHelper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xiaohe
 * @time 2021.06.11 13:59
 */
public class PptTemplate implements Closeable {

    protected List<PptTemplatePage> templatePageList = Collections.emptyList();

    protected final OPCPackage opcPackage;
    protected final XMLSlideShow template;

    public PptTemplate(File file) throws IOException {

        try (FileInputStream inputStream = new FileInputStream(file)) {

            this.opcPackage = PackageHelper.open(inputStream);
            template = new XMLSlideShow(opcPackage);

            initPages();
        }
    }

    public PptTemplate(InputStream inputStream) throws IOException {

        this.opcPackage = PackageHelper.open(inputStream);
        template = new XMLSlideShow(opcPackage);

        initPages();
    }

    protected void initPages() {

        List<XSLFSlide> slideList = template.getSlides();
        templatePageList = new ArrayList<>(slideList.size());

        for (XSLFSlide slide : slideList) {
            templatePageList.add(new PptTemplatePage(slide));
        }
    }

    public List<PptTemplatePage> getTemplatePageList() {
        return templatePageList;
    }

    public XMLSlideShow copyPpt() {
        return new Ppt(opcPackage);
    }

    @Override
    public void close() throws IOException {
        // 实际上关闭的是 opcPackage 的资源
        template.close();
    }

    private class Ppt extends XMLSlideShow {

        private Ppt(OPCPackage pkg) {
            super(pkg);
        }

        @Override
        public void close() throws IOException {
            throw new IllegalStateException("请勿关闭当前流，请销毁 PptTemplate 时调用 PptTemplate#close() 进行统一关闭");
        }
    }
}
