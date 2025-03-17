package com.skbroadband.doms.global.component.log;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.log
 * @File : CachedHttpServletRequest
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
public class CachedHttpServletRequest extends HttpServletRequestWrapper {
    private final byte[] cachedPayload;
    private final Map<String, String[]> parameters = new HashMap<>();
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public CachedHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);

        this.parameters.putAll(request.getParameterMap());

        InputStream requestInputStream = request.getInputStream();
        this.cachedPayload = IOUtils.toByteArray(requestInputStream);

//        String collect = this.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

//        if (request.getContentType() != null && request.getContentType().contains(
//                MediaType.MULTIPART_FORM_DATA_VALUE)) { // 파일 업로드시 로깅제외
//            return;
//        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedServletInputStream(this.cachedPayload);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedPayload);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8));
    }

    @Override
    public String getParameter(String name) {
        String[] parameterValues = getParameterValues(name);
        if (parameterValues != null && parameterValues.length > 0) {
            return parameterValues[0];
        } else {
            return null;
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(parameters);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(parameters.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] paremeterValues = null;
        String[] values = parameters.get(name);

        if (values != null) {
            paremeterValues = new String[values.length];
            System.arraycopy(values, 0, paremeterValues, 0, values.length);
        }

        return paremeterValues;
    }

    public void setParameter(String name, String value) {
        String[] param = {value};
        setParameter(name, param);
    }

    public void setParameter(String name, String[] values) {
        parameters.put(name, values);
    }

    public Map<String, String> getAllHeaders() {
        final Map<String, String> headers = new HashMap<>();
        Collections.list(getHeaderNames()).forEach(it -> headers.put(it, getHeader(it)));
        return  headers;
    }

    public Map<String, Object> getAllParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        Collections.list(getParameterNames()).forEach(it -> parameters.put(it, getParameter(it)));
        return  parameters;
    }
}
