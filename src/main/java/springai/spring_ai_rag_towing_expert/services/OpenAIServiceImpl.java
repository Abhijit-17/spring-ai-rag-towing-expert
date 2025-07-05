package springai.spring_ai_rag_towing_expert.services;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import springai.spring_ai_rag_towing_expert.records.Answer;
import springai.spring_ai_rag_towing_expert.records.Question;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;
    private final VectorStore vectorStore;

    @Override
    public Answer getAnswer(Question question) {
        // Logic to call OpenAI API and return the answer
        return new Answer("This is a dummy answer for: " + question.question());
    }   

}
