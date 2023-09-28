package zcla71.seatable.model.result;

import java.util.Collection;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;

// MUITO parecida com zcla71.seatable.model.metadata.Column, mas dá problema se usar herança.
@Data
public class InsertColumnResult implements Result {
    private String key;
    private String type;
    private String name;
    private Boolean editable;
    private Integer width;
    private Boolean resizable;
    private Boolean draggable;
    private InsertColumnResultData data;
    private String permission_type;
    private Collection<ForceErrorIfNotNull> permitted_users;
    private String edit_metadata_permission_type;
    private Collection<ForceErrorIfNotNull> edit_metadata_permitted_users;
    private String description;
}
