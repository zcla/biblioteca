package zcla71.seatable.model.metadata;

import lombok.Data;

@Data
public class ViewColorByRuleFilter {
    private String column_key;
    private String filter_predicate;
    private String filter_term;
}
