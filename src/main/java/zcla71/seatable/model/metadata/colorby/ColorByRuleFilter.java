package zcla71.seatable.model.metadata.colorby;

import lombok.Data;

@Data
public class ColorByRuleFilter {
    private String column_key;
    private String filter_predicate;
    private Object filter_term; // Depende do tipo da coluna
    private String filter_term_modifier; // Só para Date?
}
