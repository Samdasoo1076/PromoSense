package com.skbroadband.doms.global.component.thyemeleaf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.engine.EngineEventUtils;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.naming.SpringContextVariableNames;
import org.thymeleaf.standard.expression.AssignationSequence;
import org.thymeleaf.standard.expression.LinkExpression;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.util.StringUtils;
import org.unbescape.html.HtmlEscape;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component
 * @File : DomsHrefAttributeTagProcessor
 * @Program :
 * @Date : 2022-12-20
 * @Comment :
 */
@Slf4j
// TODO: 미완성
public class DomsHrefAttributeTagProcessor extends AbstractAttributeTagProcessor {
    public static final int ATTR_PRECEDENCE = 1300;
    public static final String ATTR_NAME = "href";
    private static final char PARAMS_START_CHAR = '(';
    private static final char PARAMS_END_CHAR = ')';
    private static final char EXPRESSION_END_CHAR = '}';
    private static final char PARAMS_DELIMITER = ',';
    private final String charset;

    public DomsHrefAttributeTagProcessor(TemplateMode templateMode,
                                         String dialectPrefix,
                                         String charset) {
        super(templateMode, dialectPrefix,  null, false, ATTR_NAME, true, ATTR_PRECEDENCE, true);
        this.charset = charset;
    }

    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {
        final RequestContext requestContext = (RequestContext)context.getVariable(SpringContextVariableNames.SPRING_REQUEST_CONTEXT);
        final LinkExpression linkExpression;
        final Object expressionResult;

        if (StringUtils.isEmptyOrWhitespace(attributeValue)) {
            expressionResult = null;
        } else {
            // Get Attribute expression
            linkExpression = (LinkExpression) StandardExpressions.getExpressionParser(context.getConfiguration()).parseExpression(context, attributeValue);

            if (linkExpression == null) {
                expressionResult = null;
            } else {
                if (requestContext.getQueryString() == null) {
                    expressionResult = linkExpression.execute(context);
                } else {
                    // Append whole request parameters to attributeValue
                    URI uri = null;
                    MultiValueMap<String, String> nvp = null;

                    try {
                        uri = new URI(requestContext.getRequestUri() + "?" + requestContext.getQueryString());

                        UriComponents uriComponents = UriComponentsBuilder.fromUri(uri).build();

                        nvp = uriComponents.getQueryParams();//URLEncodedUtils.parse(uri, Charset.forName(charset));
                    } catch (URISyntaxException e) {
                        log.error("Passed URI has not valid syntax : " + uri, e);
                    }

                    Map<String, List<String>> temp = new HashMap<>();

                    // Exclude duplication query string
                    AssignationSequence assignationSequence = linkExpression.getParameters();
                    if (assignationSequence != null) {
                        List<String> keys = assignationSequence.getAssignations().stream()
                                .map(assignation -> assignation.getLeft().getStringRepresentation())
                                .collect(Collectors.toList());

                        //                                .map(entry -> {
                        //                                    Map<String, List> map = new HashMap<>();
                        //                                    map.put(entry.getKey(), entry.getValue());
                        //                                    return map;
                        //                                })

                        for (Map.Entry<String, List<String>> entry : nvp.entrySet()) {
                            if (!keys.contains(entry.getKey())) {
                                temp.put(entry.getKey(), entry.getValue());
                            }
                        }

//                        for (Assignation assignation : assignationSequence) {
//                            String key = assignation.getLeft().getStringRepresentation();
//                            nvp.removeIf(e -> assignation.getLeft().getStringRepresentation().equals(e.getName()));
//                        }
                    }

                    final String parameters = temp.entrySet().stream()
                            .flatMap(nv -> nv.getValue().stream()
                                    .map(o -> {
                                        try {
                                            return nv.getKey() + "=${'" + (o==null?"": URLDecoder.decode(o, charset)) + "'}";
                                        } catch (UnsupportedEncodingException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                            ))
                            .collect(Collectors.joining(","));

                    final StringBuilder sb = new StringBuilder();

                    if (linkExpression.hasParameters()) {
                        // Manipulate expression string with request parameters
                        final int lastIndex = attributeValue.lastIndexOf(PARAMS_END_CHAR);

                        sb.append(attributeValue, 0, lastIndex)
                                .append(PARAMS_DELIMITER)
                                .append(parameters)
                                .append(attributeValue.substring(lastIndex));

                    } else {
                        sb.append(attributeValue, 0, attributeValue.lastIndexOf(EXPRESSION_END_CHAR))
                                .append(PARAMS_START_CHAR)
                                .append(parameters)
                                .append(PARAMS_END_CHAR)
                                .append(EXPRESSION_END_CHAR);
                    }

                    attributeValue = sb.toString();

                    expressionResult = EngineEventUtils.computeAttributeExpression(context, tag, attributeName, attributeValue).execute(context);
                }
            }
        }

        structureHandler.setAttribute("href", HtmlEscape.escapeHtml4Xml(expressionResult == null ? null : expressionResult.toString()));
    }
}