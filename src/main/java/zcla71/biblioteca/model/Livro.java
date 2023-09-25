package zcla71.biblioteca.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Livro {
    @Getter @Setter
    private String id;
    @Getter @Setter
    private String nome;
    @Getter @Setter
    private Collection<String> idsAutores;
    @Getter @Setter
    private String isbn13;
    @Getter @Setter
    private String isbn10;
}
