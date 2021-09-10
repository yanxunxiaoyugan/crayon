package com.liu.test.controller;

import com.liu.test.DTO.UserDto;
import com.liu.test.service.NotNullService;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotNullController {
    private Integer vlaue;
    @Autowired
    NotNullService notNullService;
    //使用@Notnull @Validated @Valid注解无效 使用lombok的@NotNull有效
    @GetMapping("/not/null")
    public void notNullTest(@RequestParam(value = "id",required = false)Integer id){
        notNullService.notNullTest(null);

    }

    /**
     * 这样使用时可以正常校验的
     * @param userDto
     */
    @GetMapping("/not/null2")
    public void notNullTest2(@Validated @RequestBody UserDto userDto){
        notNullService.notNullTest(1);
    }
}
