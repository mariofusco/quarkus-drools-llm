package org.hybridai.password;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;
import org.hybridai.refund.EmptyChatMemory;

@RegisterAiService(chatMemoryProviderSupplier = EmptyChatMemory.Supplier.class, modelName = "hotmodel")
@Singleton
public interface PasswordGenerator {

    @UserMessage("Create a random word having a strong relation with {text}. Return only one single word and nothing else without any additional note or comment or punctuation.")
    String generatePassword(String text);
}

