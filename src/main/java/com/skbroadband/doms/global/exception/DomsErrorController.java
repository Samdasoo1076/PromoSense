package com.skbroadband.doms.global.exception;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.exception
 * @File : DomsErrorController
 * @Program :
 * @Date : 2023-01-17
 * @Comment :
 */
@Controller
public class DomsErrorController extends BasicErrorController {

    public DomsErrorController(ErrorAttributes errorAttributes,
                                 ServerProperties serverProperties,
                                 List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus hs = getStatus(request);

        String statusCode = request.getParameter("code");
        if(StringUtils.hasText(statusCode)) {
            hs = HttpStatus.resolve(Integer.parseInt(statusCode));
        }

        ModelAndView mv = new ModelAndView();
        switch (hs){
            case BAD_REQUEST:
                mv.setViewName("/errors/400");
                break;
            case FORBIDDEN:
                mv.setViewName("/errors/403");
                break;
            case NOT_FOUND:
                mv.setViewName("/errors/404");
                break;
            default:
                mv.setViewName("/errors/500");
                break;
        }

        return mv;
    }
}
