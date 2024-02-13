package org.mfusco;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/chatbot")
public class ChatBotResource {

    /*
     http://localhost:8080/chatbot/session/1/person/Mario%20the%20firstborn%20of%20the%20Fusco%20family%20is%20born%20the%2018th%20day%20of%20March%201974%20Nowadays%20he%20works%20as%20software%20engineer%20and%20earns%20a%20quarter%20million%20a%20year

     http://localhost:8080/chatbot/session/1/mortgage

     http://localhost:8080/chatbot/session/2/person/Sofia%20the%20daughter%20of%20Mario%20Fusco%20is%20born%20on%20the%2026th%20day%20of%20the%20ninth%20month%20of%202011%20She%20is%20a%20very%20smart%20student
    */

    @Inject
    ChatService chat;

    @Inject
    PersonExtractor personExtractor;

    @Inject
    DroolsMortgageCalculator mortgageCalculator;

    @Inject
    ChatMemoryBean chatMemoryBean;

    @GET
    @Path("session/{sessionId}/message/{message}")
    @Produces(MediaType.TEXT_PLAIN)
    public String chat(String sessionId, String message) {
        return chat.chat(sessionId, message);
    }

    @GET
    @Path("session/{sessionId}/person/{message}")
    @Produces(MediaType.TEXT_PLAIN)
    public String person(String sessionId, String message) {
        Person person = personExtractor.extractPersonFrom(message);
        chatMemoryBean.registerPerson(sessionId, person);
        return person.toString();
    }

    @GET
    @Path("session/{sessionId}/mortgage")
    @Produces(MediaType.TEXT_PLAIN)
    public String person(String sessionId) {
        return mortgageCalculator.grantMortgage(chatMemoryBean.getPerson(sessionId));
    }

    @POST
    @Path("mortgage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Reply mortgage(String message) {
        long start = System.currentTimeMillis();
        Person person = personExtractor.extractPersonFrom(message);
        long llmResponse = System.currentTimeMillis();
        System.out.println( "LLM processing time: " + (llmResponse - start) + " msecs" );
        String response = mortgageCalculator.grantMortgage(person);
        long rulesResponse = System.currentTimeMillis();
        System.out.println( "Rule engine processing time: " + (rulesResponse - llmResponse) + " msecs" );
        return new Reply(response);
    }

    public static class Reply implements java.io.Serializable {
        private final String reply;

        public Reply(String message) {
            this.reply = message;
        }

        public String getReply() {
            return reply;
        }
    }
}
