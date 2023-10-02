package zcla71.seatable.model.metadata.column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.seatable.model.metadata.column.data.ColumnDataDate;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnDate extends Column {
    private ColumnDataDate data;
    private ColumnDateEditor editor;
    private ColumnDateFormatter formatter;
}
