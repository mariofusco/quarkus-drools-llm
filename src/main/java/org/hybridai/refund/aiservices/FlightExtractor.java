package org.hybridai.refund.aiservices;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;
import org.hybridai.llmutil.StatelessChat;
import org.hybridai.refund.model.Flight;

@RegisterAiService(chatMemoryProviderSupplier = StatelessChat.MemorySupplier.class)
@Singleton
public interface FlightExtractor {

    @UserMessage("Extract information about a flight from this text '{text}'. The response must contain only the JSON with flight's data and without any other sentence.")
    Flight extractData(String text);
}

