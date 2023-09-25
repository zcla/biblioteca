package zcla71.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Autor {
    @Getter @Setter
    private String id;
    @Getter @Setter
    private String nome;
}
