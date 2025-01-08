package org.hybridai.password;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;
import org.hybridai.llmutil.StatelessChat;

@RegisterAiService(chatMemoryProviderSupplier = StatelessChat.MemorySupplier.class)
@Singleton
public interface PasswordGenerator {

    @UserMessage("Create a random word having a strong relation with this text '{message}'. Return only one single word and nothing else without any additional note or comment or punctuation.")
    String generatePassword(String message);
}

