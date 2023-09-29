package zcla71.seatable.model.metadata.column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.seatable.model.metadata.column.data.ColumnDataText;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnText extends Column {
    private ColumnDataText data;
}
