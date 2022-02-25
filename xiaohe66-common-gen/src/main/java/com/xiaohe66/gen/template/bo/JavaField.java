package com.xiaohe66.gen.template.bo;

import com.xiaohe66.gen.reader.bo.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author xiaohe
 * @since 2022.02.24 17:45
 */
@Data
public class JavaField {

    private JavaType type;
    private String name;
    private TableField tableField;

    @AllArgsConstructor
    public enum JavaType {
        /**
         * java类型
         */
        INTEGER("Integer"),
        LONG("Long"),
        STRING("String"),
        BOOLEAN("Boolean"),
        LOCAL_DATE("LocalDate"),
        LOCAL_DATE_TIME("LocalDateTime"),
        BIG_DECIMAL("BigDecimal"),
        ;

        @Getter
        private final String value;

        public static JavaType parse(String type) {
            switch (type) {
                case "int":
                case "tinyint":
                    return INTEGER;
                case "bigint":
                    return LONG;
                case "date":
                    return LOCAL_DATE;
                case "datetime":
                case "timestamp":
                    return LOCAL_DATE_TIME;
                case "decimal":
                case "float":
                case "double":
                    return BIG_DECIMAL;
                default:
                    return STRING;
            }
        }
    }
}
