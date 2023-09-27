package zcla71.dao.seatable.transaction;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zcla71.seatable.SeaTableApi;
import zcla71.seatable.model.param.AppendRowsParam;
import zcla71.seatable.model.param.ListRowsSqlParam;
import zcla71.seatable.model.result.AppendRowsResult;
import zcla71.seatable.model.result.ListRowsSqlResult;

@NoArgsConstructor
public class TransactionOperationAppendRows implements TransactionOperationGeneratesId {
    @Getter @Setter
    private AppendRowsParam param;
    @Getter
    private AppendRowsResult result;
    private SeaTableApi api;

    public TransactionOperationAppendRows(AppendRowsParam param, SeaTableApi api) {
        this.param = param;
        this.api = api;
    }

    @Override
    public void execute(SeaTableApi api) throws IOException {
        this.result = api.appendRows(param);
    }

    @Override
    public Map<String, String> getIdMap() {
        // 23/09/2023 Esse método funciona, mas o SeaTable responde com no máximo 100 linhas. Na documentação diz que são 10000.
        // Dependendo do caso pode ser mais efetivo trazer logo tudo, usando listRows, que (teoricamente) traz 1000 de cada vez.
        try {
            Collection<Object> faltam = param.getRows().stream().map(r -> r.get("id")).collect(Collectors.toList());
            Map<String, String> result = new HashMap<>();
            while (faltam.size() > 0) {
                String sql = "select id, _id from " + param.getTable_name() + " where id in (";
                String separator = "";
                for (Object obj : faltam) {
                    sql += separator + "\"" + (String) obj + "\"";
                    separator = ", ";
                }
                sql += ")";
                ListRowsSqlParam lrsp = new ListRowsSqlParam(sql, true);
                ListRowsSqlResult lrsr = api.listRowsSql(lrsp);
                for (Map<String, Object> r : lrsr.getResults()) {
                    result.put((String) r.get("id"), (String) r.get("_id"));
                }
                faltam = faltam.stream().filter(f -> !result.keySet().contains(f)).toList();
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
