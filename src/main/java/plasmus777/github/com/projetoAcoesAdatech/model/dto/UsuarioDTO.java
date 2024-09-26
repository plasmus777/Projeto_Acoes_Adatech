package plasmus777.github.com.projetoAcoesAdatech.model.dto;

import jakarta.validation.constraints.*;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDTO  implements DTO<UsuarioDTO, Usuario>{

    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres")
    @NotBlank(message = "O nome não pode estar em branco.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial")
    private String senha;

    @Size(max = 1000, message = "Você pode favoritar no máximo 1000 ações.")
    private List<Acao> acoesFavoritas;

    @Size(max = 1000, message = "Você pode favoritar no máximo 1000 fundos imobiliários.")
    private List<FundoImobiliario> fundosImobiliariosFavoritos;

    @Size(max = 1000, message = "Você pode favoritar no máximo 1000 rendas fixas.")
    private List<RendaFixa> rendasFixasFavoritas;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Acao> getAcoesFavoritas() {
        return acoesFavoritas;
    }

    public void setAcoesFavoritas(List<Acao> acoesFavoritas) {
        this.acoesFavoritas = acoesFavoritas;
    }

    public List<FundoImobiliario> getFundosImobiliariosFavoritos() {
        return fundosImobiliariosFavoritos;
    }

    public void setFundosImobiliariosFavoritos(List<FundoImobiliario> fundosImobiliariosFavoritos) {
        this.fundosImobiliariosFavoritos = fundosImobiliariosFavoritos;
    }

    public List<RendaFixa> getRendasFixasFavoritas() {
        return rendasFixasFavoritas;
    }

    public void setRendasFixasFavoritas(List<RendaFixa> rendasFixasFavoritas) {
        this.rendasFixasFavoritas = rendasFixasFavoritas;
    }


    @Override
    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);

        if (this.acoesFavoritas != null) {
            usuario.setAcoesFavoritas(this.acoesFavoritas.stream()
                    .distinct()
                    .limit(1000)
                    .collect(Collectors.toList()));
        }

        if (this.fundosImobiliariosFavoritos != null) {
            usuario.setFundosImobiliariosFavoritos(this.fundosImobiliariosFavoritos.stream()
                    .distinct()
                    .limit(1000)
                    .collect(Collectors.toList()));
        }

        if (this.rendasFixasFavoritas != null) {
            usuario.setRendasFixasFavoritas(this.rendasFixasFavoritas.stream()
                    .distinct()
                    .limit(1000)
                    .collect(Collectors.toList()));
        }

        return usuario;
    }

    public UsuarioDTO fromEntity(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha());
        dto.setAcoesFavoritas(usuario.getAcoesFavoritas());
        dto.setFundosImobiliariosFavoritos(usuario.getFundosImobiliariosFavoritos());
        dto.setRendasFixasFavoritas(usuario.getRendasFixasFavoritas());
        return dto;
    }

}
