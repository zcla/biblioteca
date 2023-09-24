package zcla71.seatable.model.param;

import zcla71.seatable.model.metadata.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class DeleteTableParam {
    @Getter @Setter
    private String table_name;

    public DeleteTableParam(Table table) {
        this.table_name = table.getName();
    }
}
