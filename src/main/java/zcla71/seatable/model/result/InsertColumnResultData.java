package zcla71.seatable.model.result;

import lombok.Data;

// MUITO parecida com zcla71.seatable.model.metadata.ColumnData, mas dá problema se usar herança.
@Data
public class InsertColumnResultData {
    private String display_column_key;
}
