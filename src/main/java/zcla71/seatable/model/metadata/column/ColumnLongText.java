package zcla71.seatable.model.metadata.column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.seatable.model.metadata.column.data.ColumnDataLongText;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnLongText extends Column {
    private ColumnDataLongText data;
}
