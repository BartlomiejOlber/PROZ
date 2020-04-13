package edu.proz.checkers.infrastructure;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Move.class, name = "Move"),
    @JsonSubTypes.Type(value = MoveResponse.class, name = "MoveResponse"),
    @JsonSubTypes.Type(value = Start.class, name = "Start"),
    @JsonSubTypes.Type(value = StartResponse.class, name = "StartResponse"),
    @JsonSubTypes.Type(value = Stop.class, name = "Stop"),
    @JsonSubTypes.Type(value = StopResponse.class, name = "StopResponse"),
    }
)
public abstract class Message {

}
