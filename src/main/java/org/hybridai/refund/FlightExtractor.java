package org.hybridai.refund;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

@RegisterAiService(chatMemoryProviderSupplier = EmptyChatMemory.Supplier.class)
@Singleton
public interface FlightExtractor {

    @UserMessage("Extract information about a flight from {text}. The response must contain only the JSON with flight's data and without any other sentence.")
    Flight extractFlightFrom(String text);
}

