package org.springframework.social.line.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.springframework.social.line.api.impl.json.TextMessageMixin;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = As.PROPERTY,
		property = "type"
)
@JsonSubTypes({
		@JsonSubTypes.Type(TextMessageMixin.class),
})
public interface Message {

}
