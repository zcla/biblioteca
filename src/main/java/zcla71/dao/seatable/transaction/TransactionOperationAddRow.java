package zcla71.dao.seatable.transaction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import zcla71.seatable.SeaTableApi;
import zcla71.seatable.model.param.AddRowParam;
import zcla71.seatable.model.result.AddRowResult;

public class TransactionOperationAddRow extends TransactionOperation {
    private AddRowParam param;
    private AddRowResult result;

    public TransactionOperationAddRow(AddRowParam param) {
        this.param = param;
    }

    @Override
    public void execute(SeaTableApi api) throws IOException {
        this.result = api.addRow(param);
    }

    @Override
    public Map<String, String> getIdMap() {
        Map<String, String> result = new HashMap<>();
        result.put((String) param.getRow().get("id"), this.result.get("_id"));
        return result;
    }

    @Override
    public void applyIdMap(Map<String, String> idMap) {
        // Não precisa
    }
}
