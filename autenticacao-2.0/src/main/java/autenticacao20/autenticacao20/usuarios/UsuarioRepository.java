package autenticacao20.autenticacao20.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByUsuario(String usuario);

    Usuario findByUsuario(String usuario);

    @Transactional
    void deleteByUsuario(String usuario);

}
