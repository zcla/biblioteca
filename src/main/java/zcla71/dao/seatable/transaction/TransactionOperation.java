package zcla71.dao.seatable.transaction;

import java.io.IOException;

import zcla71.seatable.SeaTableApi;

public interface TransactionOperation {
    public abstract void execute(SeaTableApi api) throws IOException;
}
