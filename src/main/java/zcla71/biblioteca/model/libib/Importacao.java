package zcla71.biblioteca.model.libib;

import java.util.Collection;

import lombok.Data;
import zcla71.biblioteca.model.Autor;
import zcla71.biblioteca.model.Editora;
import zcla71.biblioteca.model.Livro;

@Data
public class Importacao {
    private Collection<Autor> autores;
    private Collection<Editora> editoras;
    private Collection<Livro> livros;
}
