package springai.spring_ai_rag_towing_expert.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/questions")
@RestController
public class QuestionController {

    @GetMapping("/healthz")
    public String getMethodName() {
        return "OK";
    }
    
}
