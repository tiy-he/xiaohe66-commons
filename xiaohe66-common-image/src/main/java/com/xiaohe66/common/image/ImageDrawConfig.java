package com.xiaohe66.common.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.awt.*;

/**
 * @author xiaohe
 * @time 2021.07.22 09:39
 */
@Data
@Builder
public class ImageDrawConfig {

    private Font font;
    private Color fontColor;
    private Side side;
    private int offset;

    @Data
    @AllArgsConstructor
    public static class Side {

        private Color color;
        private int size;

    }

}
