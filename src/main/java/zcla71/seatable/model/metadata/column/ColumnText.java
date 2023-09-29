package zcla71.seatable.model.metadata.column;

import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.biblioteca.model.ForceErrorIfNotNull;
import zcla71.seatable.model.metadata.ColorBys;
import zcla71.seatable.model.metadata.column.data.ColumnDataText;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnText extends Column {
    private String key;
    private String type;
    private String name;
    private Boolean editable;
    private Integer width;
    private Boolean resizable;
    private Boolean draggable;
    private ColumnDataText data;
    private String permission_type;
    private Collection<ForceErrorIfNotNull> permitted_users;
    private String edit_metadata_permission_type;
    private Collection<ForceErrorIfNotNull> edit_metadata_permitted_users;
    private String description;
    private ColorBys colorbys;
}
