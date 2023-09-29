package zcla71.seatable.model.metadata;

import java.util.Collection;

import lombok.Data;

@Data
public class ColorBys {
    private String type;
    // type = 'by_repeat_value'
    private String duplicate_column_key;
    private ColorBysRepeatValueSettings repeat_value_settings;
    // type = 'by_rules'
    private Collection<ColorByRule> color_by_rules;
    // ?
    private Collection<String> color_by_duplicate_column_keys;
    private ColorBysRangeSettings range_settings;
}
