package zcla71.seatable.model.metadata.colorby;

import java.util.Collection;

import lombok.Data;

@Data
public class ColorBys {
    private String type;
    // type = 'by_repeat_value' (Text, Number)
    private String duplicate_column_key;
    private ColorBysRepeatValueSettings repeat_value_settings;
    // type = 'by_rules' (Text, Number)
    private Collection<ColorByRule> color_by_rules;
    // type = 'by_duplicate_values' (Number)
    private Collection<String> color_by_duplicate_column_keys;
    // type = 'by_numeric_range' (Number)
    private ColorBysRangeSettings range_settings;
}
