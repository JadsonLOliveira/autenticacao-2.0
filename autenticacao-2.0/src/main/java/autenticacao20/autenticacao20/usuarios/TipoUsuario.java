package autenticacao20.autenticacao20.usuarios;

import org.springframework.security.core.GrantedAuthority;

public enum TipoUsuario implements GrantedAuthority {

    Administrador,
    Cliente;
    public String getAuthority(){
        return name();
    }
}


