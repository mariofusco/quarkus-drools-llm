package org.hybridai.refund.aiservices;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;
import org.hybridai.llmutil.StatefulChat;

@RegisterAiService(chatMemoryProviderSupplier = StatefulChat.MemorySupplier.class)
@Singleton
public interface CustomerChatService {

    @SystemMessage("""
            You are a chat bot of an airline company. Your goal is asking questions to gather information
            about a customer
            """)
    @UserMessage("""
        Ask question to the customer regarding his name and age.

        +++
        {message}
        +++
        """)
    String chat(@MemoryId String sessionId, String message);

}
