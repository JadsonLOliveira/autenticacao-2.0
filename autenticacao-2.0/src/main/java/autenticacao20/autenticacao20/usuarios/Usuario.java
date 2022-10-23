package autenticacao20.autenticacao20.usuarios;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 255, message = "Minimo 4 caracteres para o Usuário")
    @Column(unique = true, nullable = false)
    private String usuario;

    @Size(min = 8, message = "Minimo 6 caracteres para Senha")
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    List<TipoUsuario> tipoUsuario;
}
