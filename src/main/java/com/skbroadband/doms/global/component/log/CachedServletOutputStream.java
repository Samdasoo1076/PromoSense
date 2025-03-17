package com.skbroadband.doms.global.component.log;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.log
 * @File : CachedServletOutputStream
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
public class CachedServletOutputStream extends ServletOutputStream {
    private final OutputStream outputStream;
    private final ByteArrayOutputStream copy;

    public CachedServletOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream();
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        copy.write(b);
    }

    public byte[] getCopy() {
        return copy.toByteArray();
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}
