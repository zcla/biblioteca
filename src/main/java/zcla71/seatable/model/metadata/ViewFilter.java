package zcla71.seatable.model.metadata;

public class ViewFilter {
    private String column_key;
    private String filter_predicate;
    private String filter_term;

    public String getColumn_key() {
        return column_key;
    }

    public void setColumn_key(String column_key) {
        this.column_key = column_key;
    }

    public String getFilter_predicate() {
        return filter_predicate;
    }

    public void setFilter_predicate(String filter_predicate) {
        this.filter_predicate = filter_predicate;
    }

    public String getFilter_term() {
        return filter_term;
    }

    public void setFilter_term(String filter_term) {
        this.filter_term = filter_term;
    }
}
