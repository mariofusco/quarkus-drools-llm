package org.hybridai.refund.aiservices;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;
import org.hybridai.llmutil.StatefulChat;

@RegisterAiService(chatMemoryProviderSupplier = StatefulChat.MemorySupplier.class)
@Singleton
public interface FlightChatService {

    @SystemMessage("""
            You are a chat bot of an airline company. Your goal is asking questions to gather information " +
            about the customer's flight and which problems he had with it
            """)
    @UserMessage("""
        Ask question to the customer regarding the number of the flight and its eventual delay.

        +++
        {message}
        +++
        """)
    String chat(@MemoryId String sessionId, String message);

}
