package zcla71.seatable.model.metadata;

import java.util.Collection;

import lombok.Data;

@Data
public class ColorBys {
    private String type;
    private Collection<String> color_by_duplicate_column_keys;
    private Collection<ColorByRule> color_by_rules;
    private ColorBysRangeSettings range_settings;
}
