package com.xiaohe66.common.ppt.template;

import com.xiaohe66.common.ppt.PptResolver;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xiaohe
 * @time 2021.06.11 14:00
 */
public class PptTemplatePage {

    @Getter
    final XSLFSlide slide;

    @Getter
    final XSLFSlideLayout layout;

    /**
     * 若一个文本框里面存在多个 ${}, 则一个文本框会存保存多次
     */
    final Map<String, List<AbstractPptTemplateItem>> shapeMap = new HashMap<>();

    public PptTemplatePage(XSLFSlide slide) {
        this.slide = slide;
        this.layout = slide.getSlideLayout();

        // 处理形状类型
        List<XSLFShape> shapeList = new ArrayList<>(slide.getShapes());
        for (XSLFShape shape : shapeList) {

            String shapeName = shape.getShapeName();

            if (shapeName.contains("矩形")) {
                addImage(shape);

            }
        }

        // 母版中的占位符
        XSLFTextShape[] placeholders = slide.getPlaceholders();

        // 处理文本类型
        List<XSLFShape> shapes = slide.getShapes();
        for (int i = 0; i < shapes.size(); i++) {

            XSLFShape shape = shapes.get(i);


            // XSLFTextBox : 文本框
            if (ArrayUtils.contains(placeholders, shape) || shape instanceof XSLFTextBox) {

                // XSLFTextBox 是 XSLFTextShape 的子类
                addText((XSLFTextShape) shape, i);
            }
        }

        // 3. 重排序
        for (Map.Entry<String, List<AbstractPptTemplateItem>> entry : shapeMap.entrySet()) {
            List<AbstractPptTemplateItem> list = entry.getValue();
            list.sort(null);
        }

    }

    protected void addText(XSLFTextShape textShape, int index) {

        String text = textShape.getText();
        String[] nameArr = StringUtils.substringsBetween(text, "${", "}");

        if (nameArr == null) {
            return;
        }

        for (String name : nameArr) {

            if (StringUtils.isNotBlank(name)) {

                PptTemplateText templateText = new PptTemplateText(textShape, name, index);

                List<AbstractPptTemplateItem> list = shapeMap.computeIfAbsent(name, key -> new ArrayList<>());
                list.add(templateText);
            }
        }
    }

    protected void addImage(XSLFShape shape) {
        String text = PptResolver.getShapeText(shape);

        String name = StringUtils.substringBetween(text, "${", "}");
        if (StringUtils.isNotBlank(name)) {

            PptTemplateImage templateShape = new PptTemplateImage(shape, name);
            slide.removeShape(shape);

            List<AbstractPptTemplateItem> list = shapeMap.computeIfAbsent(templateShape.getName(), key -> new ArrayList<>());
            list.add(templateShape);
        }
    }

    public List<AbstractPptTemplateItem> get(String name) {
        return shapeMap.get(name);
    }

    public Set<Map.Entry<String, List<AbstractPptTemplateItem>>> entrySet() {
        return shapeMap.entrySet();
    }
}
