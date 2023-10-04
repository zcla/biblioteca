package zcla71.seatable.model.metadata.view;

import java.util.Collection;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;
import zcla71.seatable.model.metadata.colorby.ColorBys;

@Data
public class View {
    private String _id;
    private String name;
    private String type;
    private ForceErrorIfNotNull private_for;
    private Boolean is_locked;
    private String row_height;
    private String filter_conjunction;
    private Collection<ViewFilter> filters;
    private Collection<ViewSort> sorts;
    private Collection<ViewGroupBy> groupbys;
    private ColorBys colorbys;
    private Collection<String> hidden_columns;
    private Collection<ForceErrorIfNotNull> rows;
    private ForceErrorIfNotNull formula_rows;
    private ForceErrorIfNotNull link_rows;
    private ForceErrorIfNotNull summaries;
    private ForceErrorIfNotNull colors;
    private ForceErrorIfNotNull column_colors;
    private Collection<ForceErrorIfNotNull> groups;
}
