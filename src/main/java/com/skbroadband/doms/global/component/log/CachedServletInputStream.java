package com.skbroadband.doms.global.component.log;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.log
 * @File : CachedServletInputStream
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
@Slf4j
public class CachedServletInputStream extends ServletInputStream {
    private ByteArrayInputStream cachedInputStream;

    public CachedServletInputStream(byte[] cachedBody) {
        this.cachedInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
        return cachedInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
        return cachedInputStream.read();
    }
}
