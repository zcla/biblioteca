package zcla71.biblioteca.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Livro {
    private String id;
    private String nome;
    private Collection<String> idsAutores;
    private String isbn13;
    private String isbn10;
    private String descricao;
}
