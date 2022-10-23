package autenticacao20.autenticacao20.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {

    boolean existsByUsuarios(String usuario);

    Usuarios findByUsuarios(String usuario);

    @Transactional
    void deleteByUsuarios(String usuario);

}
