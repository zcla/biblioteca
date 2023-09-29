package zcla71.seatable.model.metadata.column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.seatable.model.metadata.column.data.ColumnDataLink;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnLink extends Column {
    private ColumnDataLink data;
}
