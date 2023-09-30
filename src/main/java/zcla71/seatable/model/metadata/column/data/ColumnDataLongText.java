package zcla71.seatable.model.metadata.column.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnDataLongText extends ColumnData {
    private Boolean enable_fill_default_value;
    private ColumnDataLongTextDefaultValue default_value;
}
