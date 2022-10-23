package autenticacao20.autenticacao20;

import autenticacao20.autenticacao20.usuarios.TipoUsuario;
import autenticacao20.autenticacao20.usuarios.Usuarios;
import autenticacao20.autenticacao20.usuarios.UsuariosService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

	final UsuariosService usuariosService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
		Usuarios administrador = new Usuarios();
		administrador.setUsuario("admin");
		administrador.setSenha("admin");
//		admin.setEmail("admin@email.com");
		administrador.setTipoUsuario(new ArrayList<TipoUsuario>(Arrays.asList(TipoUsuario.Administrador)));

		usuariosService.cadastrar(administrador);

		Usuarios cliente = new Usuarios();
		cliente.setUsuario("client");
		cliente.setSenha("client");
//		client.setEmail("client@email.com");
		cliente.setTipoUsuario(new ArrayList<TipoUsuario>(Arrays.asList(TipoUsuario.Cliente)));

		usuariosService.cadastrar(cliente);
	}

}
