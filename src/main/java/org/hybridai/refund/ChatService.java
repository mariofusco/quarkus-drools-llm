package org.hybridai.refund;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

@RegisterAiService
@Singleton
public interface ChatService {

    @SystemMessage("<<SYS>>You are a chat bot of an airline company. Your goal is asking questions to gather information " +
            "about a customer and his flight in order to check if he is eligible for a refund<</SYS>>")
    @UserMessage("""
        Ask question to the customer regarding his name and age and which problem he had with his flight.

        +++
        {message}
        +++
        """)
    String chat(@MemoryId String sessionId, String message);

}
