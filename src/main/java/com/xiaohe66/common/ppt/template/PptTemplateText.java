package com.xiaohe66.common.ppt.template;

import org.apache.poi.xslf.usermodel.XSLFTextShape;

/**
 * 文本框类型
 *
 * @author xiaohe
 * @time 2021.06.11 16:48
 */
public class PptTemplateText extends PptTemplateItem {

    final XSLFTextShape shape;
    final String text;
    final int index;

    public PptTemplateText(XSLFTextShape shape, String name, int index) {
        super(name);
        this.shape = shape;
        this.index = index;
        this.text = shape.getText();
    }

    public XSLFTextShape getShape() {
        return shape;
    }

    public String getText() {
        return text;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Type getType() {
        return Type.TEXT;
    }
}
