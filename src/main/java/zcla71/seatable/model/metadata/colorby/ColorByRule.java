package zcla71.seatable.model.metadata.colorby;

import java.util.Collection;

import lombok.Data;

@Data
public class ColorByRule {
    private String color;
    private Collection<ColorByRuleFilter> filters;
    private String filter_conjunction;
}
