package org.jojoldu.book.springbootaws.web;

import org.jojoldu.book.springbootaws.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // controller를 json을 반환하는 컨트롤러로 만들어줌
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount){
        return new HelloResponseDto(name,amount);
    }

}
