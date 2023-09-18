package zcla71.biblioteca.model.libib;

import java.util.Collection;

import zcla71.biblioteca.model.Autor;
import zcla71.biblioteca.model.Livro;

public class Importacao {
    private Collection<Autor> autores;
    private Collection<Livro> livros;

    public Collection<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Collection<Autor> autores) {
        this.autores = autores;
    }

    public Collection<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Collection<Livro> livros) {
        this.livros = livros;
    }
}
