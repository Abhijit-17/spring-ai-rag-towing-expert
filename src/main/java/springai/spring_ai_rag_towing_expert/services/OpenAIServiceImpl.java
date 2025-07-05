package springai.spring_ai_rag_towing_expert.services;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import springai.spring_ai_rag_towing_expert.records.Answer;
import springai.spring_ai_rag_towing_expert.records.Question;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;
    private final VectorStore vectorStore;

    //using rag prompt template to create the user message prompt
    @Value("classpath:templates/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    //using system message prompt template to create the system message
    @Value("classpath:templates/system-message.st")
    private Resource systemMessageTemplate;


    @Override
    public Answer getAnswer(Question question) {

        // Perform a similarity search in the vector store
        // to find relevant documents based on the question.
        // The topK parameter specifies how many documents to retrieve.
        // In this case, we are retrieving the top 4 documents.

        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                .query(question.question())
                .topK(4)
                .build());
        
        
        // Extract the text content from the retrieved documents.
        // This is necessary to prepare the content for the prompt.
        // The getText() method is used to get the text content of each document.
        // The result is a list of strings containing the text of each document.

        List<String> documentContents = documents.stream()
                .map(Document::getText)
                .toList();
        
        // Creating system prompt template and system message
        PromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemMessageTemplate);
        Message systemMessage = systemPromptTemplate.createMessage();

        // Creating user prompt template and user message
        PromptTemplate userPromptTemplate = new PromptTemplate(ragPromptTemplate);
        Message userMessage = userPromptTemplate.createMessage(Map.of(
                "input", question.question(),
                "documents", String.join("\n", documentContents) // Join the document contents into a single string
        ));

        // create a list of messages and promt to send to the model
        List<Message> messages = List.of(systemMessage, userMessage);
        Prompt prompt  = new Prompt(messages);

        // Call the chat model with the prompt and messages
        ChatResponse chatResponse = chatModel.call(prompt);

        // return the answer from the chat response
        return new Answer(chatResponse.getResult().getOutput().getText());

        
    }   

}
