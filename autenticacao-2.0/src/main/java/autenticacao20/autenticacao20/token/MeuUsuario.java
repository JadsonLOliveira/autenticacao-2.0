package autenticacao20.autenticacao20.token;

import autenticacao20.autenticacao20.usuarios.Usuarios;
import autenticacao20.autenticacao20.usuarios.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeuUsuario implements UserDetailsService {

    private final UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        final Usuarios usuarios = usuariosRepository.findByUsuarios(usuario);

        if (usuarios == null) {
            throw new UsernameNotFoundException("O usuário '" + usuario + "' não existe");
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(usuario)//
                .password(usuarios.getSenha())//
                .authorities(usuarios.getTipoUsuario())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

}
