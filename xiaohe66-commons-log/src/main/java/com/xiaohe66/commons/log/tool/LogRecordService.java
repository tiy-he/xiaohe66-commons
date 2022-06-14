package com.xiaohe66.commons.log.tool;

import com.xiaohe66.commons.log.context.LogContext;
import com.xiaohe66.commons.log.context.LogRecordAccount;

/**
 * @author xiaohe
 * @since 2022.05.20 17:15
 */
public interface LogRecordService {

    void saveLog(LogContext logContext);

    LogRecordAccount getCurrentAccount();

}
