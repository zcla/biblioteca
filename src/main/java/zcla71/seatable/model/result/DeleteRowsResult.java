package zcla71.seatable.model.result;

public class DeleteRowsResult implements Result {
    private Integer deleted_rows;

    public Integer getDeleted_rows() {
        return deleted_rows;
    }

    public void setDeleted_rows(Integer deleted_rows) {
        this.deleted_rows = deleted_rows;
    }
}
