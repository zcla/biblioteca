package zcla71.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindByName;

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

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreators() {
        return creators;
    }

    public void setCreators(String creators) {
        this.creators = creators;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEan_isbn13() {
        return ean_isbn13;
    }

    public void setEan_isbn13(String ean_isbn13) {
        this.ean_isbn13 = ean_isbn13;
    }

    public String getUpc_isbn10() {
        return upc_isbn10;
    }

    public void setUpc_isbn10(String upc_isbn10) {
        this.upc_isbn10 = upc_isbn10;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getNumber_of_discs() {
        return number_of_discs;
    }

    public void setNumber_of_discs(String number_of_discs) {
        this.number_of_discs = number_of_discs;
    }

    public String getNumber_of_players() {
        return number_of_players;
    }

    public void setNumber_of_players(String number_of_players) {
        this.number_of_players = number_of_players;
    }

    public String getAge_group() {
        return age_group;
    }

    public void setAge_group(String age_group) {
        this.age_group = age_group;
    }

    public String getEnsemble() {
        return ensemble;
    }

    public void setEnsemble(String ensemble) {
        this.ensemble = ensemble;
    }

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getEsrb() {
        return esrb;
    }

    public void setEsrb(String esrb) {
        this.esrb = esrb;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBegan() {
        return began;
    }

    public void setBegan(String began) {
        this.began = began;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }
}
