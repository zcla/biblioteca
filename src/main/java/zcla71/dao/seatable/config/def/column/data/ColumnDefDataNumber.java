package zcla71.dao.seatable.config.def.column.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDefDataNumber {
    public enum Format { number, percent, dollar, euro, yuan };
    public enum Decimal { dot, comma };
    public enum Thousands { no, space, comma };

    private Format format;
    private Decimal decimal;
    private Thousands thousands;
}
