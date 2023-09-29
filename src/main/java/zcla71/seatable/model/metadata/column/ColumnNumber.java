package zcla71.seatable.model.metadata.column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.seatable.model.metadata.column.data.ColumnDataNumber;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnNumber extends Column {
    private ColumnDataNumber data;
    private ColumnNumberEditor editor;
    private ColumnNumberFormatter formatter;
}
