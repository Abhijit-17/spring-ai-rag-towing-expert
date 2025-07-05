package springai.spring_ai_rag_towing_expert.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import springai.spring_ai_rag_towing_expert.records.Answer;
import springai.spring_ai_rag_towing_expert.records.Question;
import springai.spring_ai_rag_towing_expert.services.OpenAIService;



@RequestMapping("/questions")
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    //constructor
    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/healthz")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/ask")
    public Answer getAnswer(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }
    
    
}
