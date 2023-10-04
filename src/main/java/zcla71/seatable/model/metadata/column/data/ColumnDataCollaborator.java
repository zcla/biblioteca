package zcla71.seatable.model.metadata.column.data;

import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnDataCollaborator extends ColumnData {
    private Boolean enable_send_notification;
    private Boolean enable_fill_default_value;
    private Collection<String> default_value;
    private String default_collaborator_type;
}
