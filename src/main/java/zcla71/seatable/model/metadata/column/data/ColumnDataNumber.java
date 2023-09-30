package zcla71.seatable.model.metadata.column.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnDataNumber extends ColumnData {
    private String format;
    private Integer precision;
    private Boolean enable_precision;
    private Boolean enable_fill_default_value;
    private Boolean enable_check_format;
    private String decimal;
    private String thousands;
    private Integer default_value;
    private String currency_symbol;
    private String currency_symbol_position;
    private Integer format_min_value;
    private Integer format_max_value;
}
