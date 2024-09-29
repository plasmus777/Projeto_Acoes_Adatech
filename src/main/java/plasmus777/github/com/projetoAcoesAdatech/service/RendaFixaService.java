package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;
import plasmus777.github.com.projetoAcoesAdatech.dto.RendaFixaDTO;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.repository.RendaFixaRepository;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RendaFixaService implements RestService<RendaFixaDTO>{

    private final RendaFixaRepository rendaFixaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FinnhubClient finnhubClient;

    public RendaFixaService(RendaFixaRepository rendaFixaRepository, UsuarioRepository usuarioRepository, FinnhubClient finnhubClient){
        this.rendaFixaRepository = rendaFixaRepository;
        this.usuarioRepository = usuarioRepository;
        this.finnhubClient = finnhubClient;
    }

    @Override
    public List<RendaFixaDTO> obterLista() {
        return rendaFixaRepository
                .findAll()
                .stream()
                .map(RendaFixaDTO::fromEntity)
                .toList();
    }

    @Override
    public Optional<RendaFixaDTO> obter(Long id) {
        return rendaFixaRepository.findRendaFixaById(id).map(RendaFixaDTO::fromEntity);
    }

    public Optional<RendaFixaDTO> obterPorCodigo(String codigo){
        return rendaFixaRepository.findRendaFixaByCodigo(codigo).map(RendaFixaDTO::fromEntity);
    }

    @Override
    public ResponseEntity<String> atualizar(Long id, RendaFixaDTO rendaFixa) {
        Optional<RendaFixa> opt = rendaFixaRepository.findRendaFixaById(id);
        if(opt.isPresent()){
            RendaFixa r = opt.get();

            if(rendaFixa != null){
                try {
                    if(finnhubClient.buscarInformacoesAtivo(rendaFixa.getCodigo()).getPrecoAtual().compareTo(BigDecimal.ZERO) == 0)
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A renda fixa não pode ser atualizada com um código de um ativo inexistente.");

                    r.setNome(rendaFixa.getNome());
                    r.setPrecoAtual(rendaFixa.getPrecoAtual());
                    r.setPrecoCompra(rendaFixa.getPrecoCompra());
                    r.setDataCadastro(rendaFixa.getDataCadastro());
                    Optional<Usuario> optUsuario = usuarioRepository.findUsuarioByEmail(rendaFixa.getUsuarioEmail());
                    optUsuario.ifPresent(r::setUsuario);
                    r.setPrecoMinimo(rendaFixa.getPrecoMinimo());
                    r.setPrecoMaximo(rendaFixa.getPrecoMaximo());

                    r.setTaxaRetorno(rendaFixa.getTaxaRetorno());
                    r.setDataVencimento(rendaFixa.getDataVencimento());

                    rendaFixaRepository.save(r);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Renda fixa atualizada com sucesso.");
                }catch (Exception e){
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde salvar a renda fixa atualizada.\n" + e.getLocalizedMessage());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("A renda fixa não pôde ser atualizada.");
    }

    @Override
    public ResponseEntity<String> cadastrar(RendaFixaDTO rendaFixa) {
        try{
            AcaoApi api = finnhubClient.buscarInformacoesAtivo(rendaFixa.getCodigo());
            if(api.getPrecoAtual().compareTo(BigDecimal.ZERO) == 0)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A renda fixa não pode ser cadastrada com um código de um ativo inexistente.");

            rendaFixa.setPrecoAtual(api.getPrecoAtual());
            rendaFixa.setDataCadastro(LocalDateTime.now());

            Optional<Usuario> optUsuario = usuarioRepository.findUsuarioByEmail(rendaFixa.getUsuarioEmail());
            if(optUsuario.isPresent()){
                Usuario u = optUsuario.get();
                List<RendaFixa> lista = u.getRendasFixasFavoritas();
                if(lista == null) lista = new ArrayList<>();
                RendaFixa r = rendaFixa.toEntity();
                r.setUsuario(u);
                lista.add(r);
                u.setRendasFixasFavoritas(lista);

                usuarioRepository.save(u);
                return ResponseEntity.status(HttpStatus.CREATED).body("Renda fixa cadastrada com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há um usuário com o e-mail registrado pela renda fixa.");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde salvar a renda fixa a ser cadastrada.\n" + e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<String> apagar(Long id) {
        Optional<RendaFixa> opt = rendaFixaRepository.findRendaFixaById(id);
        if(opt.isPresent()){
            RendaFixa r = opt.get();
            try{
                Usuario u = r.getUsuario();
                u.getRendasFixasFavoritas().remove(r);
                rendaFixaRepository.delete(r);
                usuarioRepository.save(u);
                return ResponseEntity.status(HttpStatus.OK).body("Renda fixa apagada com sucesso.");
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde apagar a renda fixa.\n" + e.getLocalizedMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("A renda fixa não pôde ser apagado.");
    }
}
