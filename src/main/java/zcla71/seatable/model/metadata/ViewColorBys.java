package zcla71.seatable.model.metadata;

import java.util.Collection;

import lombok.Data;

@Data
public class ViewColorBys {
    private String type;
    private Collection<String> color_by_duplicate_column_keys;
    private Collection<ViewColorByRule> color_by_rules;
}
