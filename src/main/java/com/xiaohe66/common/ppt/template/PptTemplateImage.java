package com.xiaohe66.common.ppt.template;

import org.apache.poi.xslf.usermodel.XSLFShape;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * @author xiaohe
 * @time 2021.06.11 14:00
 */
public class PptTemplateImage extends PptTemplateItem implements Comparable<PptTemplateImage> {

    final XSLFShape shape;
    final Rectangle2D anchor;
    final int x;
    final int y;

    public PptTemplateImage(XSLFShape shape, String name) {
        super(name);
        this.shape = shape;

        this.anchor = shape.getAnchor();

        this.x = (int) anchor.getX();
        this.y = (int) anchor.getY();
    }

    public XSLFShape getShape() {
        return shape;
    }

    public Rectangle2D getAnchor() {
        return anchor;
    }

    @Override
    public Type getType() {
        return Type.IMG;
    }

    @Override
    public int compareTo(@NotNull PptTemplateImage o) {
        return o.x == this.x ?
                o.y - this.y :
                o.x - this.x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PptTemplateImage that = (PptTemplateImage) o;

        if (x != that.x) {
            return false;
        }

        if (y != that.y) {
            return false;
        }

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }
}
