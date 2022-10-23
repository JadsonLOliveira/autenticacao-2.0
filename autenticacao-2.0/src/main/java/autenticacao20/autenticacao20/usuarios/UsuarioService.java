package autenticacao20.autenticacao20.usuarios;

import autenticacao20.autenticacao20.exception.CustomException;
import autenticacao20.autenticacao20.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String conectar(String usuario, String senha) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, senha));
            return jwtTokenProvider.criarToken(usuario, usuarioRepository.findByUsuario(usuario).getTipoUsuario());
        } catch (AuthenticationException e) {
            throw new CustomException("Usuário ou senha incorretos", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    public String cadastrar(Usuario usuario) {
        if (!usuarioRepository.existsByUsuario(usuario.getUsuario())) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            usuarioRepository.save(usuario);
            return jwtTokenProvider.criarToken(usuario.getUsuario(), usuario.getTipoUsuario());
        } else {
            throw new CustomException("Usuário já existente", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    public void delete(String usuario) {
        usuarioRepository.deleteByUsuario(usuario);
    }
    public Usuario search(String usuario) {
        Usuario usuarios = usuarioRepository.findByUsuario(usuario);
        if (usuarios == null) {
            throw new CustomException("Usuário inexistente", HttpStatus.NOT_FOUND);
        }
        return usuarios;
    }
    public Usuario meuUsuario(HttpServletRequest req) {
        return usuarioRepository.findByUsuario(jwtTokenProvider.getUsuario(jwtTokenProvider.resolveToken(req)));
    }
    public String atualizar(String usuario) {
        return jwtTokenProvider.criarToken(usuario, usuarioRepository.findByUsuario(usuario).getTipoUsuario());
    }
}
