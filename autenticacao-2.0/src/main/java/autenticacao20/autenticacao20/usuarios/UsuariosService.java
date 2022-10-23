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
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String conectar(String usuario, String senha) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, senha));
            return jwtTokenProvider.criarToken(usuario, usuariosRepository.findByUsuarios(usuario).getTipoUsuario());
        } catch (AuthenticationException e) {
            throw new CustomException("Usuário ou senha incorretos", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    public String cadastrar(Usuarios usuarios) {
        if (!usuariosRepository.existsByUsuarios(usuarios.getUsuario())) {
            usuarios.setSenha(passwordEncoder.encode(usuarios.getSenha()));
            usuariosRepository.save(usuarios);
            return jwtTokenProvider.criarToken(usuarios.getUsuario(), usuarios.getTipoUsuario());
        } else {
            throw new CustomException("Usuário já existente", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    public void delete(String usuarios) {
        usuariosRepository.deleteByUsuarios(usuarios);
    }
    public Usuarios search(String usuario) {
        Usuarios usuarios = usuariosRepository.findByUsuarios(usuario);
        if (usuarios == null) {
            throw new CustomException("Usuário inexistente", HttpStatus.NOT_FOUND);
        }
        return usuarios;
    }
    public Usuarios meuUsuario(HttpServletRequest req) {
        return usuariosRepository.findByUsuarios(jwtTokenProvider.getUsuarios(jwtTokenProvider.resolveToken(req)));
    }
    public String atualizar(String usuarios) {
        return jwtTokenProvider.criarToken(usuarios, usuariosRepository.findByUsuarios(usuarios).getTipoUsuario());
    }
}
