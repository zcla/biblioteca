package zcla71.dao.seatable.transaction;

import java.io.IOException;
import java.util.Map;

import zcla71.seatable.SeaTableApi;

public abstract class TransactionOperation {
    public abstract void execute(SeaTableApi api) throws IOException;

    public abstract Map<String, String> getIdMap();

    public abstract void applyIdMap(Map<String, String> idMap);
}
