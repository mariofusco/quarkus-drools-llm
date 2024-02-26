package org.hybridai.mortgage;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

@RegisterAiService
@Singleton
public interface PersonExtractor {

    @UserMessage("Extract information about a person from {text}. When income is null, it is set to 0. The response must contain only the JSON with person's data and without any other sentence.")
    Person extractPersonFrom(String text);
}

