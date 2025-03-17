package com.skbroadband.doms.global.component.log;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.log
 * @File : CachedHttpServletResponse
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
public class CachedHttpServletResponse extends HttpServletResponseWrapper {
    private ServletOutputStream outputStream;
    private PrintWriter writer;
    private CachedServletOutputStream copier;
    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response The response to be wrapped
     * @throws IllegalArgumentException if the response is null
     */
    public CachedHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (outputStream == null) {
            outputStream = getResponse().getOutputStream();
            copier = new CachedServletOutputStream(outputStream);
        }

        return copier;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            copier = new CachedServletOutputStream(getResponse().getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
        }

        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        }
        else if (outputStream != null) {
            copier.flush();
        }
    }

    public byte[] getContentAsByteArray() {
        if (copier != null) {
            return copier.getCopy();
        }
        else {
            return new byte[0];
        }
    }

    public Map<String, String> getAllHeaders() {
        final Map<String, String> headers = new HashMap<>();
        getHeaderNames().forEach(it -> headers.put(it, getHeader(it)));
        return  headers;
    }
}
