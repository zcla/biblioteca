package zcla71.seatable.model.metadata.column.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnDataDate extends ColumnData {
    private String format;
    private Boolean enable_fill_default_value;
    private String default_value; // Date?
    private String default_date_type;
}
