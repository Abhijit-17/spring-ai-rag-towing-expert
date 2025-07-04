package springai.spring_ai_rag_towing_expert.services;

import springai.spring_ai_rag_towing_expert.records.Answer;
import springai.spring_ai_rag_towing_expert.records.Question;

public interface OpenAIService {

    Answer getAnswer(Question question);

}
