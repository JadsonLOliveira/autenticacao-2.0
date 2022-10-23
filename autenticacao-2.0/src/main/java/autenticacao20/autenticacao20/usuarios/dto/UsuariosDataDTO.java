package autenticacao20.autenticacao20.usuarios.dto;

import autenticacao20.autenticacao20.usuarios.TipoUsuario;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class UsuariosDataDTO {
    @ApiModelProperty(position = 0)
    private String usuario;
    @ApiModelProperty(position = 1)
    private String senha;
    @ApiModelProperty(position = 2)
    List<TipoUsuario> tipoUsuario;
}
