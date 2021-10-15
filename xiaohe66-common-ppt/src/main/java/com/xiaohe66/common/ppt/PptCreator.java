package com.xiaohe66.common.ppt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaohe66.common.ppt.data.PptData;
import com.xiaohe66.common.ppt.data.PptPageData;
import com.xiaohe66.common.ppt.data.PptPageItemData;
import com.xiaohe66.common.ppt.template.AbstractPptTemplateItem;
import com.xiaohe66.common.ppt.template.PptTemplate;
import com.xiaohe66.common.ppt.template.PptTemplateImage;
import com.xiaohe66.common.ppt.template.PptTemplatePage;
import com.xiaohe66.common.ppt.template.PptTemplateText;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xddf.usermodel.text.XDDFTextBody;
import org.apache.poi.xddf.usermodel.text.XDDFTextParagraph;
import org.apache.poi.xddf.usermodel.text.XDDFTextRun;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author xiaohe
 * @time 2021.06.11 16:16
 */
@Slf4j
public class PptCreator {

    private PptTemplate template;

    public PptCreator(PptTemplate template) {
        this.template = template;
    }

    public void generateToOutputStream(OutputStream outputStream, String json) throws IOException, JsonProcessingException {

        generate(json).write(outputStream);
    }

    public void generateToOutputStream(OutputStream outputStream, PptData data) throws IOException {

        generate(data).write(outputStream);
    }

    public XMLSlideShow generate(String json) throws IOException, JsonProcessingException {
        return generate(PptResolver.parsePptData(json));
    }

    public XMLSlideShow generate(PptData data) throws IOException {
        List<PptPageData> dataList = data.getPageDataList();
        List<PptTemplatePage> pageList = template.getTemplatePageList();

        XMLSlideShow ppt = template.copyPpt();

        for (int i = 0; i < pageList.size(); i++) {

            if (i < dataList.size()) {

                PptPageData pageData = dataList.get(i);
                PptTemplatePage page = pageList.get(i);

                copyPage(ppt, pageData, page);
            }

            // note : 删除PPT的模板页，删除第0页后，第1页就会变成第0页。
            ppt.removeSlide(0);
        }
        return ppt;
    }

    protected void copyPage(XMLSlideShow ppt, PptPageData pageData, PptTemplatePage page) throws IOException {

        for (PptPageItemData itemData : pageData) {

            XSLFSlide slide = ppt.createSlide(page.getLayout());
            slide.importContent(page.getSlide());

            replaceItem(ppt, slide, itemData, page);
        }
    }

    protected void replaceItem(XMLSlideShow ppt, XSLFSlide slide, PptPageItemData itemData, PptTemplatePage page) throws IOException {

        for (Map.Entry<String, List<String>> entry : itemData.entrySet()) {

            List<AbstractPptTemplateItem> itemList = page.get(entry.getKey());

            if (itemList == null) {
                log.warn("模板中不存在指定的key : {}", entry.getKey());
                continue;
            }

            List<String> valueList = entry.getValue();

            for (int i = 0; i < valueList.size(); i++) {

                if (i == itemList.size()) {
                    break;
                }

                String value = valueList.get(i);
                AbstractPptTemplateItem item = itemList.get(i);

                if (item.getType() == AbstractPptTemplateItem.Type.TEXT) {

                    replaceText(slide, (PptTemplateText) item, value);
                } else {

                    replaceImage(ppt, slide, (PptTemplateImage) item, value);
                }
            }
        }
    }

    protected void replaceText(XSLFSlide slide, PptTemplateText templateText, String value) {

        List<XSLFShape> shapes = slide.getShapes();
        XSLFShape shape = shapes.get(templateText.getIndex());

        if (!(shape instanceof XSLFTextShape)) {
            // note : 在读取模板时，仅会读取文本框类型的。因此不会进入到该if中
            log.error("被替换的不是文本框类型，不应该出现错误。需检查编码");
            return;
        }

        XSLFTextShape textShape = (XSLFTextShape) shape;

        String replaceName = templateText.getReplaceName();

        XDDFTextBody textBody = textShape.getTextBody();

        List<XDDFTextParagraph> textParagraphList = textBody.getParagraphs();

        for (XDDFTextParagraph textParagraph : textParagraphList) {

            // 一个 XDDFTextRun 表示文本框中每一段不同格式的文字，若存在混合文字的情况，则会有多个该对象
            List<XDDFTextRun> textRuns = textParagraph.getTextRuns();

            for (XDDFTextRun textRun : textRuns) {

                String originText = textRun.getText();

                if (originText.contains(replaceName)) {

                    String text = StringUtils.replaceOnce(originText, replaceName, value);
                    textRun.setText(text);
                    return;
                }
            }
        }

        String originText = textShape.getText();
        if (originText.contains(replaceName)) {

            // 当不存在多个拆分格式时，尝试替换完整的
            String text = StringUtils.replaceOnce(originText, replaceName, value);
            textBody.setText(text);
        }
    }

    protected void replaceImage(XMLSlideShow ppt, XSLFSlide slide, PptTemplateImage templateImage, String value) throws IOException {

        if (StringUtils.isBlank(value)) {
            return;
        }

        URL url = new URL(value);

        try (InputStream inputStream = url.openStream()) {

            // 在 ppt 创建一个图片。但这个图片还未被使用
            XSLFPictureData pictureData = ppt.addPicture(inputStream, PictureData.PictureType.PNG);

            // 将图片加入到指定页中
            XSLFPictureShape picture = slide.createPicture(pictureData);

            // 设置图片的位置、宽高
            picture.setAnchor(templateImage.getAnchor());

        }

    }
}
