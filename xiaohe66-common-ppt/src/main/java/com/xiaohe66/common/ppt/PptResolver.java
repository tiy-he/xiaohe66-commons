package com.xiaohe66.common.ppt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaohe66.common.ppt.data.PptData;
import com.xiaohe66.common.ppt.data.PptPageData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlStringImpl;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * @author xiaohe
 * @time 2021.06.11 17:41
 */
@Slf4j
public class PptResolver {

    private static final String DML_NS = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final String PML_NS = "http://schemas.openxmlformats.org/presentationml/2006/main";

    private static final QName BODY_QNAME = new QName(PML_NS, "txBody");
    private static final QName P_QNAME = new QName(DML_NS, "p");
    private static final QName R_QNAME = new QName(DML_NS, "r");
    private static final QName TEXT_QNAME = new QName(DML_NS, "t");

    private static final ObjectMapper mapper = new ObjectMapper();

    private PptResolver() {

    }

    public static String getShapeText(XSLFShape shape) {

        if (shape instanceof XSLFAutoShape) {
            return ((XSLFAutoShape) shape).getText();

        }

        XmlObject xmlObject = shape.getXmlObject();

        XmlObject[] bodyArr = xmlObject.selectChildren(BODY_QNAME);
        XmlObject[] pArr = bodyArr[0].selectChildren(P_QNAME);
        XmlObject[] rArr = pArr[0].selectChildren(R_QNAME);
        XmlObject[] textArr = rArr[0].selectChildren(TEXT_QNAME);
        return ((XmlStringImpl) textArr[0]).getStringValue();
    }

    public static PptData parsePptData(String json) throws JsonProcessingException {

        try {
            List<PptPageData> pageDataList = mapper.readValue(json, new TypeReference<List<PptPageData>>() {
            });

            log.info("json结果 : {}", pageDataList);

            return new PptData(pageDataList);

        } catch (JsonProcessingException e) {
            log.error("json解析失败 : {}", json);
            throw e;
        }
    }

}
