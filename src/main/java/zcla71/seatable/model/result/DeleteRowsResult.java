package zcla71.seatable.model.result;

import lombok.Data;

@Data
public class DeleteRowsResult implements Result {
    private Integer deleted_rows;
}
