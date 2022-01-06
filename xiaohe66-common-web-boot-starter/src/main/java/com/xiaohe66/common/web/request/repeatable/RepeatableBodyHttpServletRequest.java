package com.xiaohe66.common.web.request.repeatable;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author xiaohe
 * @since 2022.01.05 15:50
 */
public class RepeatableBodyHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] bytes;

    public RepeatableBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        read();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        read();

        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        return new BufferedReader(new InputStreamReader(is));
    }


    protected void read() throws IOException {
        if (bytes == null) {
            // TODO : 不清楚是否会有线程安全问题
            bytes = IOUtils.toByteArray(getRequest().getInputStream());
        }
    }
}
