package org.hybridai.refund.aiservices;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

@RegisterAiService(tools = ExplanationTool.class)
@Singleton
public interface AirlineChatService {

    @SystemMessage("""
            You are a chatbot of an airline company. You reply to customer's questions and provide explanations to him.
            If the customer asks for an explanation of his refund then reply exactly with what returned by calling getRefundExplanation once and only once.
            """)
    @UserMessage("""
            {question}.
            The sessionId is {sessionId}.
            """)
    String chat(String sessionId, String question);
}
