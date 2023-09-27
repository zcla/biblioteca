package zcla71.seatable.model.metadata;

import java.util.Collection;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;

@Data
public class Column {
    private String key;
    private String type;
    private String name;
    private Boolean editable;
    private Integer width;
    private Boolean resizable;
    private Boolean draggable;
    private ColumnData data;
    private String permission_type;
    private Collection<ForceErrorIfNotNull> permitted_users;
    private String edit_metadata_permission_type;
    private Collection<ForceErrorIfNotNull> edit_metadata_permitted_users;
    private ForceErrorIfNotNull description;
}
