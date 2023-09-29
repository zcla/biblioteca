package zcla71.seatable.model.metadata.column.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnDataText extends ColumnData {
    private Boolean enable_fill_default_value; // também em ColumnDataNumber
    private Boolean enable_check_format; // também em ColumnDataNumber
    private String format_specification_value;
    private String default_value; // diferente em ColumnDataNumber (Integer)
    private String format_check_type;
}
