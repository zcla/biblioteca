package zcla71.seatable.model.ddl.columndef.data;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDefDataNumber {
    public enum Format { number, percent, dollar, euro, yuan };
    public enum Decimal { dot, comma };
    public enum Thousands { no, space, comma };

    private Format format;
    private Decimal decimal;
    private Thousands thousands;

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Decimal getDecimal() {
        return decimal;
    }

    public void setDecimal(Decimal decimal) {
        this.decimal = decimal;
    }

    public Thousands getThousands() {
        return thousands;
    }

    public void setThousands(Thousands thousands) {
        this.thousands = thousands;
    }
}
