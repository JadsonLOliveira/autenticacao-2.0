package autenticacao20.autenticacao20.usuarios;

import autenticacao20.autenticacao20.usuarios.dto.UsuariosDataDTO;
import autenticacao20.autenticacao20.usuarios.dto.UsuariosResponseDTO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/usuarios")
@Api(tags = "usuarios")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosService usuariosService;
    private final ModelMapper modelMapper;

    @PostMapping("/conectar")
    @ApiOperation(value = "${UsuariosController.conectar}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Erro ao conectar-se"), //
            @ApiResponse(code = 422, message = "Invalido o Usuário ou Senha indicados")})
    public String login(//
                        @ApiParam("Usuario") @RequestParam String usuario, //
                        @ApiParam("Senha") @RequestParam String senha) {
        return usuariosService.conectar(usuario, senha);
    }
    @PostMapping("/cadastrar")
    @ApiOperation(value = "${UsuariosController.cadastrar}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Erro ao conectar-se"), //
            @ApiResponse(code = 403, message = "Acesso negado"), //
            @ApiResponse(code = 422, message = "Usuário em uso")})
    public String cadastrar(@ApiParam("Usuaário Cadastrado") @RequestBody UsuariosDataDTO usuario) {
        return usuariosService.cadastrar(modelMapper.map(usuario, Usuarios.class));
    }

    @DeleteMapping(value = "/{usuario}")
    @PreAuthorize("hasRole('Administrador')")
    @ApiOperation(value = "${UsuariosController.delete}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Erro ao conectar-se"), //
            @ApiResponse(code = 403, message = "Acesso negado"), //
            @ApiResponse(code = 404, message = "Usuário inexistente"), //
            @ApiResponse(code = 500, message = "Expirou ou está invalido seu Token")})
    public String delete(@ApiParam("Usuario") @PathVariable String usuario) {
        usuariosService.delete(usuario);
        return usuario;
    }
    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('Administrador')")
    @ApiOperation(value = "${UsuariosController.search}", response = UsuariosResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Erro ao conectar-se"), //
            @ApiResponse(code = 403, message = "Acesso negado"), //
            @ApiResponse(code = 404, message = "Usuário inexistente"), //
            @ApiResponse(code = 500, message = "Expirou ou está invalido seu Token")})
    public UsuariosResponseDTO search(@ApiParam("Usuario") @PathVariable String usuario) {
        return modelMapper.map(usuariosService.search(usuario), UsuariosResponseDTO.class);
    }

    @GetMapping(value = "/meuUsuario")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    @ApiOperation(value = "${UsuariosController.me}", response = UsuariosResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Erro ao conectar-se"), //
            @ApiResponse(code = 403, message = "Acesso negado"), //
            @ApiResponse(code = 500, message = "Expirou ou está invalido seu Token")})
    public UsuariosResponseDTO meuUsuario(HttpServletRequest req) {
        return modelMapper.map(usuariosService.meuUsuario(req), UsuariosResponseDTO.class);
    }
    @GetMapping("/atualizar")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public String atualizar(HttpServletRequest req) {
        return usuariosService.atualizar(req.getRemoteUser());
    }
}
