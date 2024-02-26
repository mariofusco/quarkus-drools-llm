package org.hybridai.password;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

@RegisterAiService
@Singleton
public interface PasswordGenerator {

    @UserMessage("Provide a random word having a strong relation with {text}. Return only one single word and nothing else without any additional note or comment or punctuation.")
    String generatePassword(String text);
}

