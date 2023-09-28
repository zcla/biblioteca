package zcla71.seatable.model.metadata.column;

import java.util.Collection;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;
import zcla71.seatable.model.metadata.ColorBys;
import zcla71.seatable.model.metadata.columndata.ColumnData;

@Data
public class Column {
    // TODO Herança por type
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
    private String description;
    private ColorBys colorbys;
}
