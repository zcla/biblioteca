package zcla71.seatable.model.metadata.column;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ColumnLink.class, name = "link"),
    @JsonSubTypes.Type(value = ColumnNumber.class, name = "number"),
    @JsonSubTypes.Type(value = ColumnText.class, name = "text")
})
@Data
public class Column {
}
