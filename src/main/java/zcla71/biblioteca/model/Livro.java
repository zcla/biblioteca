package zcla71.biblioteca.model;

import java.util.Collection;
import java.util.Date;

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
    private Collection<String> idsEditoras;
    private Date dataPublicacao;
    private String idGrupo;
    private Collection<String> idsTags;
    private String notas;
    private Integer paginas;
    // private String status;
    // private Date began;
    // private Date completed;
    // private Date added;
    private String edicao;
}
