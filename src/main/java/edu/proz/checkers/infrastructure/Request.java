package edu.proz.checkers.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Abstract Request class. Requests are sent from the client to server side.
 * The annotations are necessary for Jackson ObjectMapper to correctly map polymorphic type to actual subclasses.
 * <img src="doc-files/edu.proz.checkers.infrastructure.png" alt="Messages">
 * @author bartlomiej
 * @see Message
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Move.class, name = "Move"),
    @JsonSubTypes.Type(value = GetOpponentEvent.class, name = "GetOpponentEvent"),
    @JsonSubTypes.Type(value = Start.class, name = "Start"),
    @JsonSubTypes.Type(value = Stop.class, name = "Stop")

    }
)

public abstract class Request extends Message {
	
}
