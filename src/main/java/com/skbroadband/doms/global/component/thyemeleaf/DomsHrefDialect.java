package com.skbroadband.doms.global.component.thyemeleaf;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component
 * @File : DomsHrefDialect
 * @Program :
 * @Date : 2022-12-20
 * @Comment :
 */
public class DomsHrefDialect extends AbstractProcessorDialect {
    public static final String NAME = "ExtraLink";
    public static final String DEFAULT_PREFIX = "th";
    public static final int PROCESSOR_PRECEDENCE = 800;
    private final String charset;

    public DomsHrefDialect(String charset) {
        super("ExtraLink", "cu", 800);
        this.charset = charset;
    }

    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new LinkedHashSet<IProcessor>();
        processors.add(new DomsHrefAttributeTagProcessor(TemplateMode.HTML,
                dialectPrefix, this.charset));
        return processors;
    }
}
