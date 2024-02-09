package org.mfusco;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

@RegisterAiService
@Singleton
public interface ChatService {

    @SystemMessage("<<SYS>>You are a chat bot.<</SYS>>")
    @UserMessage("""
        Answer the user request. The answer must be relevant to the question.
        When you don't know, respond that you don't know.

        +++
        {message}
        +++
        """)
    String chat(@MemoryId String sessionId, String message);

}
