package edu.proz.checkers.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Abstract Response class. Responses are sent from the server to the client side.
 * The annotations are necessary for Jackson ObjectMapper to correctly map polymorphic type to actual subclasses.
 * <img src="doc-files/edu.proz.checkers.infrastructure.png" alt="Messages">
 * @author bartlomiej
 *@see Message
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = OpponentMovedResponse.class, name = "OpponentMovedResponse"),
    @JsonSubTypes.Type(value = WaitResponse.class, name = "WaitResponse"),
    @JsonSubTypes.Type(value = StartResponse.class, name = "StartResponse"),
    @JsonSubTypes.Type(value = MoveResponse.class, name = "MoveResponse"),
    @JsonSubTypes.Type(value = StopResponse.class, name = "StopResponse"),
    @JsonSubTypes.Type(value = YouLose.class, name = "YouLose"),
    @JsonSubTypes.Type(value = YouWin.class, name = "YouWin")
    }
)

public abstract class Response extends Message {
	
}
