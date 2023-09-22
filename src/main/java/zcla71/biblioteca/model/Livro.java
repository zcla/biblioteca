package zcla71.biblioteca.model;

import java.util.Collection;

public class Livro {
    private String id;
    private String nome;
    private Collection<String> idsAutores;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Collection<String> getIdsAutores() {
        return idsAutores;
    }

    public void setIdsAutores(Collection<String> idsAutores) {
        this.idsAutores = idsAutores;
    }
}
