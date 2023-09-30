package zcla71.seatable.model.metadata.column;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;
import zcla71.seatable.model.metadata.colorby.ColorBys;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ColumnText.class, name = "text"),
    @JsonSubTypes.Type(value = ColumnLongText.class, name = "long-text"),
    @JsonSubTypes.Type(value = ColumnNumber.class, name = "number"),
    @JsonSubTypes.Type(value = ColumnLink.class, name = "link")
})
@Data
public class Column {
    private String key;
    private String type;
    private String name;
    private Boolean editable;
    private Integer width;
    private Boolean resizable;
    private Boolean draggable;
    private String permission_type;
    private Collection<ForceErrorIfNotNull> permitted_users;
    private String edit_metadata_permission_type;
    private Collection<ForceErrorIfNotNull> edit_metadata_permitted_users;
    private String description;
    private ColorBys colorbys;
}
