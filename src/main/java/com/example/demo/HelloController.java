package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/hello")
	String hello(){
		System.out.println("Information reached");
		return "Hii my name is yash kambaria";
		
	}
 
}
