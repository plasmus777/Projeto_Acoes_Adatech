package plasmus777.github.com.projetoAcoesAdatech.model.dto;

import jakarta.validation.constraints.*;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;

import java.util.List;

public class UsuarioDTO {

    @Size(min = 3, max = 20, message = "O nome deve ter entre 3 a 20 caracteres")
    @NotBlank(message = "O nome não pode estar em branco.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial")
    private String senha;

    @NotEmpty(message = "A lista de ações favoritas não pode estar vazia.")
    @Size(max = 10, message = "Você só pode ter até 10 ações favoritas.")
    private List<Acao> acoesFavoritas;

    @NotEmpty(message = "A lista de fundos imobiliários favoritos não pode estar vazia.")
    @Size(max = 5, message = "Você só pode ter até 5 fundos imobiliários favoritos.")
    private List<FundoImobiliario> fundosImobiliariosFavoritos;

    @NotEmpty(message = "A lista de rendas fixas favoritas não pode estar vazia.")
    @Size(max = 5, message = "Você só pode ter até 5 rendas fixas favoritas.")
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

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        usuario.setAcoesFavoritas(this.acoesFavoritas);
        usuario.setFundosImobiliariosFavoritos(this.fundosImobiliariosFavoritos);
        usuario.setRendasFixasFavoritas(this.rendasFixasFavoritas);
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
