package zcla71.seatable.model.metadata.column.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnDataText extends ColumnData {
    private Boolean enable_fill_default_value;
    private Boolean enable_check_format;
    private String format_specification_value;
    private String default_value;
    private String format_check_type;
}
