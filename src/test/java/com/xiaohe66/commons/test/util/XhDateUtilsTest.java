package com.xiaohe66.commons.test.util;

import com.xiaohe66.common.util.XhDateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author xiaohe
 * @time 2019.08.27 10:12
 * @see XhDateUtils
 */
public class XhDateUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(XhDateUtilsTest.class);

    private static final FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMdd");

    private final Date[] dates;

    private final Date[] firstDates;

    private final Date[] lastDates;

    public XhDateUtilsTest() throws ParseException {
        dates = new Date[]{
                dateFormat.parse("20190122"),
                dateFormat.parse("20190222"),
                dateFormat.parse("20190322"),
                dateFormat.parse("20190422"),
                dateFormat.parse("20190522"),
                dateFormat.parse("20190622"),
                dateFormat.parse("20190722"),
                dateFormat.parse("20190822"),
                dateFormat.parse("20190922"),
                dateFormat.parse("20191022"),
                dateFormat.parse("20191122"),
                dateFormat.parse("20191222"),
                dateFormat.parse("20200222")
        };

        firstDates = new Date[]{
                dateFormat.parse("20190101"),
                dateFormat.parse("20190201"),
                dateFormat.parse("20190301"),
                dateFormat.parse("20190401"),
                dateFormat.parse("20190501"),
                dateFormat.parse("20190601"),
                dateFormat.parse("20190701"),
                dateFormat.parse("20190801"),
                dateFormat.parse("20190901"),
                dateFormat.parse("20191001"),
                dateFormat.parse("20191101"),
                dateFormat.parse("20191201"),
                dateFormat.parse("20200201")
        };

        lastDates = new Date[]{
                dateFormat.parse("20190131"),
                dateFormat.parse("20190228"),
                dateFormat.parse("20190331"),
                dateFormat.parse("20190430"),
                dateFormat.parse("20190531"),
                dateFormat.parse("20190630"),
                dateFormat.parse("20190731"),
                dateFormat.parse("20190831"),
                dateFormat.parse("20190930"),
                dateFormat.parse("20191031"),
                dateFormat.parse("20191130"),
                dateFormat.parse("20191231"),
                dateFormat.parse("20200229")
        };
    }


    @Test
    public void test1() {
        for (int i = 0; i < dates.length; i++) {
            assertEquals(firstDates[i], XhDateUtils.firstDayOfMonth(dates[i]));
        }
    }

    @Test
    public void test2() {
        for (int i = 0; i < dates.length; i++) {
            assertEquals(lastDates[i], XhDateUtils.lastDayOfMonth(dates[i]));
        }
    }

    // 正常2月测试
    @Test
    public void test3() throws ParseException {
        Date beginDate = dateFormat.parse("20190225");
        Date endDate = dateFormat.parse("20190302");
        List<Date> dateList = Arrays.asList(
                beginDate,
                dateFormat.parse("20190226"),
                dateFormat.parse("20190227"),
                dateFormat.parse("20190228"),
                dateFormat.parse("20190301"),
                endDate
        );
        testCreateDateInRange(beginDate, endDate, dateList);
    }

    // 润月2月测试
    @Test
    public void test4() throws ParseException {
        Date beginDate = dateFormat.parse("20200225");
        Date endDate = dateFormat.parse("20200302");
        List<Date> dateList = Arrays.asList(
                beginDate,
                dateFormat.parse("20200226"),
                dateFormat.parse("20200227"),
                dateFormat.parse("20200228"),
                dateFormat.parse("20200229"),
                dateFormat.parse("20200301"),
                endDate
        );
        testCreateDateInRange(beginDate, endDate, dateList);
    }

    // 跨年测试
    @Test
    public void test5() throws ParseException {
        Date beginDate = dateFormat.parse("20191227");
        Date endDate = dateFormat.parse("20200103");
        List<Date> dateList = Arrays.asList(
                beginDate,
                dateFormat.parse("20191228"),
                dateFormat.parse("20191229"),
                dateFormat.parse("20191230"),
                dateFormat.parse("20191231"),
                dateFormat.parse("20200101"),
                dateFormat.parse("20200102"),
                endDate
        );
        testCreateDateInRange(beginDate, endDate, dateList);
    }

    public void testCreateDateInRange(Date beginDate, Date endDate, List<Date> result) {
        List<Date> dateInRange = XhDateUtils.createDateInRange(beginDate, endDate);
        assertEquals(result.size(), dateInRange.size());
        assertArrayEquals(result.toArray(), dateInRange.toArray());
    }

    @Test
    public void testEachMonToCurrent() throws ParseException {

        List<Date> list = new ArrayList<>();

        list.add(dateFormat.parse("20190909"));
        testEachMonToCurrent(list);

        list.add(dateFormat.parse("20190910"));
        testEachMonToCurrent(list);

        list.add(dateFormat.parse("20190911"));
        testEachMonToCurrent(list);

        list.add(dateFormat.parse("20190912"));
        testEachMonToCurrent(list);

        list.add(dateFormat.parse("20190913"));
        testEachMonToCurrent(list);

        list.add(dateFormat.parse("20190914"));
        testEachMonToCurrent(list);

        list.add(dateFormat.parse("20190915"));
        testEachMonToCurrent(list);

    }

    public void testEachMonToCurrent(List<Date> list) {
        List<Date> dateEachMonToCurrent = XhDateUtils.createDateMonToCurrent(list.get(list.size() - 1));
        assertArrayEquals(dateEachMonToCurrent.toArray(), list.toArray());
    }

    @Test
    public void testGetLastWednesday() throws ParseException {

        List<Date> endDate = Arrays.asList(
                XhDateUtils.getLastWednesday(dateFormat.parse("20190916")),
                XhDateUtils.getLastWednesday(dateFormat.parse("20190917")),
                XhDateUtils.getLastWednesday(dateFormat.parse("20190918")),
                XhDateUtils.getLastWednesday(dateFormat.parse("20190919")),
                XhDateUtils.getLastWednesday(dateFormat.parse("20190920")),
                XhDateUtils.getLastWednesday(dateFormat.parse("20190921")),
                XhDateUtils.getLastWednesday(dateFormat.parse("20190922"))
        );
        List<Date> beginDate = Arrays.asList(
                dateFormat.parse("20190911"),
                dateFormat.parse("20190911"),
                dateFormat.parse("20190918"),
                dateFormat.parse("20190918"),
                dateFormat.parse("20190918"),
                dateFormat.parse("20190918"),
                dateFormat.parse("20190918")
        );

        logger.info("beginDate : {}", beginDate.stream().map(dateFormat::format).collect(Collectors.toList()));
        logger.info("endDate : {}", endDate.stream().map(dateFormat::format).collect(Collectors.toList()));

        assertArrayEquals(beginDate.toArray(), endDate.toArray());

    }
}
