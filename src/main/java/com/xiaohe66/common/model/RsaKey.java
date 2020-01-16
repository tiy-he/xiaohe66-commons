package com.xiaohe66.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaohe
 * @time 2020.01.16 15:54
 */
@Getter
@AllArgsConstructor
public class RsaKey {

    private String privateKey;
    private String publicKey;

}
