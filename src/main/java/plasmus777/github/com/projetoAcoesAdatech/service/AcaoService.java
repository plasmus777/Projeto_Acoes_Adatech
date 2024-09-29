package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.dto.AcaoDTO;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.repository.AcaoRepository;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AcaoService implements RestService<AcaoDTO> {

    private final AcaoRepository acaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FinnhubClient finnhubClient;

    public AcaoService(AcaoRepository acaoRepository, UsuarioRepository usuarioRepository, FinnhubClient finnhubClient){
        this.acaoRepository = acaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.finnhubClient = finnhubClient;
    }

    @Override
    public List<AcaoDTO> obterLista() {
        return acaoRepository
                .findAll()
                .stream()
                .map(AcaoDTO::fromEntity)
                .toList();
    }

    @Override
    public Optional<AcaoDTO> obter(Long id) {
        return acaoRepository.findAcaoById(id).map(AcaoDTO::fromEntity);
    }

    public Optional<AcaoDTO> obterPorCodigoNegociacao(String codigoNegociacao) {
        return acaoRepository.findAcaoByCodigoNegociacao(codigoNegociacao).map(AcaoDTO::fromEntity);
    }

    @Override
    public ResponseEntity<String> atualizar(Long id, AcaoDTO acao) {
        Optional<Acao> opt = acaoRepository.findAcaoById(id);
        if(opt.isPresent()){
            Acao a = opt.get();

            if(acao != null){
                try {
                    if(finnhubClient.buscarInformacoesAtivo(acao.getCodigoNegociacao()).getPrecoAtual().compareTo(BigDecimal.ZERO) == 0)
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A ação não pode ser atualizada com um código de um ativo inexistente.");

                    a.setNome(acao.getNome());
                    a.setPrecoAtual(acao.getPrecoAtual());
                    a.setPrecoCompra(acao.getPrecoCompra());
                    a.setDataCadastro(acao.getDataCadastro());
                    Optional<Usuario> optUsuario = usuarioRepository.findUsuarioByEmail(acao.getUsuarioEmail());
                    optUsuario.ifPresent(a::setUsuario);
                    a.setPrecoMinimo(acao.getPrecoMinimo());
                    a.setPrecoMaximo(acao.getPrecoMaximo());

                    a.setCodigoNegociacao(acao.getCodigoNegociacao());
                    a.setQuantidade(acao.getQuantidade());

                    acaoRepository.save(a);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Ação atualizada com sucesso.");
                }catch (Exception e){
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde salvar a ação atualizada.\n" + e.getLocalizedMessage());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("A ação não pôde ser atualizada.");
    }

    @Override
    public ResponseEntity<String> cadastrar(AcaoDTO acao) {
        try{
            AcaoApi api = finnhubClient.buscarInformacoesAtivo(acao.getCodigoNegociacao());
            if(api.getPrecoAtual().compareTo(BigDecimal.ZERO) == 0)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A ação não pode ser cadastrada com um código de um ativo inexistente.");

            acao.setPrecoAtual(api.getPrecoAtual());
            acao.setDataCadastro(LocalDateTime.now());

            Optional<Usuario> optUsuario = usuarioRepository.findUsuarioByEmail(acao.getUsuarioEmail());
            if(optUsuario.isPresent()){
                Usuario u = optUsuario.get();
                List<Acao> lista = u.getAcoesFavoritas();
                if(lista == null) lista = new ArrayList<>();
                Acao a = acao.toEntity();
                a.setUsuario(u);
                lista.add(a);
                u.setAcoesFavoritas(lista);

                usuarioRepository.save(u);
                return ResponseEntity.status(HttpStatus.CREATED).body("Ação cadastrada com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há um usuário com o e-mail registrado pela ação.");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde salvar a ação a ser cadastrada.\n" + e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<String> apagar(Long id) {
        Optional<Acao> opt = acaoRepository.findAcaoById(id);
        if(opt.isPresent()){
            Acao a = opt.get();
            try{
                Usuario u = a.getUsuario();
                u.getAcoesFavoritas().remove(a);
                acaoRepository.delete(a);
                usuarioRepository.save(u);
                return ResponseEntity.status(HttpStatus.OK).body("Ação apagada com sucesso.");
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde apagar a ação.\n" + e.getLocalizedMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("A ação não pôde ser apagada.");
    }
}
