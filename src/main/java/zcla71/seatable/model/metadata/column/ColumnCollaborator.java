package zcla71.seatable.model.metadata.column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.seatable.model.metadata.column.data.ColumnDataCollaborator;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnCollaborator extends Column {
    private ColumnDataCollaborator data;
}
