package zcla71.biblioteca.model.libib;

import java.util.Collection;

import lombok.Data;
import zcla71.biblioteca.model.Autor;
import zcla71.biblioteca.model.Editora;
import zcla71.biblioteca.model.Grupo;
import zcla71.biblioteca.model.Livro;
import zcla71.biblioteca.model.Tag;

@Data
public class Importacao {
    private Collection<Autor> autores;
    private Collection<Editora> editoras;
    private Collection<Grupo> grupos;
    private Collection<Livro> livros;
    private Collection<Tag> tags;
}
