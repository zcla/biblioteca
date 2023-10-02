package zcla71.dao.seatable.config.def.column;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import lombok.Data;
import zcla71.seatable.exception.ValidationException;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "column_type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ColumnDefText.class, name = "text"),
    @JsonSubTypes.Type(value = ColumnDefLongText.class, name = "long-text"),
    @JsonSubTypes.Type(value = ColumnDefNumber.class, name = "number"),
    @JsonSubTypes.Type(value = ColumnDefDate.class, name = "date"),
    @JsonSubTypes.Type(value = ColumnDefLink.class, name = "link")
})
public abstract class ColumnDef {
    private String column_name;
    private String column_type;

    public void setColumn_name(String column_name) {
        if (!column_name.matches("^[^.}{`]*$")) {
            throw new ValidationException();
        }
        this.column_name = column_name;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getColumn_type() {
        return column_type;
    }

    public ColumnDef(String column_type) {
        this.column_type = column_type;
    }
}
