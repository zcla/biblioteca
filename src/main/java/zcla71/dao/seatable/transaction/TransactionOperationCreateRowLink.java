package zcla71.dao.seatable.transaction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import zcla71.seatable.SeaTableApi;
import zcla71.seatable.model.param.CreateRowLinkParam;
import zcla71.seatable.model.result.CreateRowLinkResult;

public class TransactionOperationCreateRowLink extends TransactionOperation {
    private CreateRowLinkParam param;
    @SuppressWarnings("unused")
    private CreateRowLinkResult result;

    public TransactionOperationCreateRowLink(CreateRowLinkParam param) {
        this.param = param;
    }

    @Override
    public void execute(SeaTableApi api) throws IOException {
        result = api.createRowLink(param);
    }

    @Override
    public Map<String, String> getIdMap() {
        // Não cria id
        return new HashMap<>();
    }

    @Override
    public void applyIdMap(Map<String, String> idMap) {
        param.setTable_row_id(idMap.get(param.getTable_row_id()));
        param.setOther_table_row_id(idMap.get(param.getOther_table_row_id()));
    }
}
