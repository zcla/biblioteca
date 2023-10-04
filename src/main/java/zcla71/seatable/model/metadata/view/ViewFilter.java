package zcla71.seatable.model.metadata.view;

import lombok.Data;

@Data
public class ViewFilter {
    private String column_key;
    private String filter_predicate;
    private String filter_term;
}
