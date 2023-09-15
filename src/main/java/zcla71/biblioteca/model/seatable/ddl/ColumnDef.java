package zcla71.biblioteca.model.seatable.ddl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "column_type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ColumnDefText.class, name = "text"),
    @JsonSubTypes.Type(value = ColumnDefNumber.class, name = "number")
})
public abstract class ColumnDef {
    private String column_name;
    private String column_type;

    public String getColumn_name() {
        return column_name;
    }

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
