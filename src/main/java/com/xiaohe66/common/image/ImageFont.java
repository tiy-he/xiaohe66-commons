package com.xiaohe66.common.image;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author xiaohe
 * @time 2021.07.22 10:02
 */
@Data
@AllArgsConstructor
public class ImageFont {

    private int x;
    private int y;
    /**
     * 旋转角度
     */
    private int rotate;
    private List<ImageFontText> fontTextList;

}
