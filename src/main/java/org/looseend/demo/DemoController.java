package org.looseend.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jsinglet on 18/05/2017.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping
    public String hello() {
        return "Hello Hand";
    }
}
