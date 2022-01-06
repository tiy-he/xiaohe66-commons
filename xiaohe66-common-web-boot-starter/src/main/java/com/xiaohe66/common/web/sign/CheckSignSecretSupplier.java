package com.xiaohe66.common.web.sign;

/**
 * @author xiaohe
 * @since 2022.01.06 11:51
 */
public interface CheckSignSecretSupplier {

    String getSecret(long appId);

}
