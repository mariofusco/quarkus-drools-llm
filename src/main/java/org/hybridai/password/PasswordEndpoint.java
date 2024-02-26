package org.hybridai.password;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.hybridai.Reply;
import org.hybridai.refund.RefundChatbotEndpoint;
import org.jboss.logging.Logger;

@Path("/hybridai")
public class PasswordEndpoint {

    private static final Logger LOG = Logger.getLogger(RefundChatbotEndpoint.class);
    
    @Inject
    PasswordGenerator passwordGenerator;

    @Inject
    DroolsPasswordRewriter passwordRewriter;

    @POST
    @Path("password")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Reply password(String message) {
        long start = System.currentTimeMillis();
        String password = passwordGenerator.generatePassword(message).trim();
        int length = 0;
        while (length < password.length()-1 && Character.isLetter(password.charAt(++length)));
        password = password.substring(0, length);
        long llmResponse = System.currentTimeMillis();
        LOG.info( "LLM processing time: " + (llmResponse - start) + " msecs" );
        LOG.info("Generated word: " + password);
        String response = passwordRewriter.rewritePassword(password);
        long rulesResponse = System.currentTimeMillis();
        LOG.info( "Rule engine processing time: " + (rulesResponse - llmResponse) + " msecs" );
        return new Reply(response);
    }
}
