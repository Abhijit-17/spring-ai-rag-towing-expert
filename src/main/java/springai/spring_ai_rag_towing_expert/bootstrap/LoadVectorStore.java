package springai.spring_ai_rag_towing_expert.bootstrap;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import springai.spring_ai_rag_towing_expert.config.VectorStoreProperties;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LoadVectorStore implements CommandLineRunner {

    @Autowired
    VectorStore vectorStore;

    @Autowired
    VectorStoreProperties vectorStoreProperties;

    public LoadVectorStore(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {

        if(vectorStore.similaritySearch("Sportsman").isEmpty()){
            log.info("Loading vector store from: {}", vectorStoreProperties.getVectorStorePath());
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.info("Loading resource: {}", document.getFilename());

                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocuments = textSplitter.apply(documents);
                vectorStore.add(splitDocuments);
                log.info("Loaded {} documents from resource: {}", splitDocuments.size(), document.getFilename());
            });
        } else {
            log.info("Vector store already loaded, skipping.");
        }

    }



}
