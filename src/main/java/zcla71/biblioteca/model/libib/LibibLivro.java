package zcla71.biblioteca.model.libib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LibibLivro {
    @CsvBindByName
    private String item_type;
    @CsvBindByName
    private String title;
    @CsvBindByName
    private String creators;
    @CsvBindByName
    private String first_name;
    @CsvBindByName
    private String last_name;
    @CsvBindByName
    private String ean_isbn13;
    @CsvBindByName
    private String upc_isbn10;
    @CsvBindByName
    private String description;
    @CsvBindByName
    private String publisher;
    @CsvBindByName
    private String publish_date;
    @CsvBindByName
    private String group;
    @CsvBindByName
    private String tags;
    @CsvBindByName
    private String notes;
    @CsvBindByName
    private String price;
    @CsvBindByName
    private String length;
    @CsvBindByName
    private String number_of_discs;
    @CsvBindByName
    private String number_of_players;
    @CsvBindByName
    private String age_group;
    @CsvBindByName
    private String ensemble;
    @CsvBindByName
    private String aspect_ratio;
    @CsvBindByName
    private String esrb;
    @CsvBindByName
    private String rating;
    @CsvBindByName
    private String review;
    @CsvBindByName
    private String review_date;
    @CsvBindByName
    private String status;
    @CsvBindByName
    private String began;
    @CsvBindByName
    private String completed;
    @CsvBindByName
    private String added;
    @CsvBindByName
    private String copies;

    private Date asDate(String strDate) throws ParseException {
        if (strDate != null) {
            return (new SimpleDateFormat("yyyy-MM-dd")).parse(strDate);
        }
        return null;
    }

    public Date publish_dateAsDate() throws ParseException {
        return asDate(publish_date);
    }
}
