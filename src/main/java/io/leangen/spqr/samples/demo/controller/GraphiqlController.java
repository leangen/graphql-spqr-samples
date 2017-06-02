package io.leangen.spqr.samples.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by milos on 5-10-16.
 */
@RestController
public class GraphiqlController {
    @RequestMapping(value = "/graphiql/domain", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView indexAnnotation() {
        return new ModelAndView("/index-domain");
    }
    
    @RequestMapping(value = "/graphiql", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView indexDomain() {
        return new ModelAndView("/index-annotation");
    }
    
    @RequestMapping(value = "/workspace", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView workspace() {
        return new ModelAndView("/workspace");
    }
}
