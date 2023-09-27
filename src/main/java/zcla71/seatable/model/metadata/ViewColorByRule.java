package zcla71.seatable.model.metadata;

import java.util.Collection;

import lombok.Data;

@Data
public class ViewColorByRule {
    private String color;
    private Collection<ViewColorByRuleFilter> filters;
    private String filter_conjunction;
}
