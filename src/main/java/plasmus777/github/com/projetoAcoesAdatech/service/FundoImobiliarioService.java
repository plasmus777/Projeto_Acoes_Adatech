package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;
import plasmus777.github.com.projetoAcoesAdatech.dto.FundoImobiliarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.repository.FundoImobiliarioRepository;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FundoImobiliarioService implements RestService<FundoImobiliarioDTO> {

    private final FundoImobiliarioRepository fundoImobiliarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final FinnhubClient finnhubClient;

    public FundoImobiliarioService(FundoImobiliarioRepository fundoImobiliarioRepository, UsuarioRepository usuarioRepository, FinnhubClient finnhubClient){
        this.fundoImobiliarioRepository = fundoImobiliarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.finnhubClient = finnhubClient;
    }

    @Override
    public List<FundoImobiliarioDTO> obterLista() {
        return fundoImobiliarioRepository
                .findAll()
                .stream()
                .map(FundoImobiliarioDTO::fromEntity)
                .toList();
    }

    @Override
    public Optional<FundoImobiliarioDTO> obter(Long id) {
        return fundoImobiliarioRepository.findFundoImobiliarioById(id).map(FundoImobiliarioDTO::fromEntity);
    }

    public Optional<FundoImobiliarioDTO> obterPorCodigoFii(String codigoFii){
        return fundoImobiliarioRepository.findFundoImobiliarioByCodigoFii(codigoFii).map(FundoImobiliarioDTO::fromEntity);
    }

    @Override
    public ResponseEntity<String> atualizar(Long id, FundoImobiliarioDTO fundoImobiliario) {
        Optional<FundoImobiliario> opt = fundoImobiliarioRepository.findFundoImobiliarioById(id);
        if(opt.isPresent()){
            FundoImobiliario f = opt.get();

            if(fundoImobiliario != null){
                try {
                    if(finnhubClient.buscarInformacoesAtivo(fundoImobiliario.getCodigoFii()).getPrecoAtual().compareTo(BigDecimal.ZERO) == 0)
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O fundo imobiliário não pode ser atualizado com um código de um ativo inexistente.");

                    f.setNome(fundoImobiliario.getNome());
                    f.setPrecoAtual(fundoImobiliario.getPrecoAtual());
                    f.setPrecoCompra(fundoImobiliario.getPrecoCompra());
                    f.setDataCadastro(fundoImobiliario.getDataCadastro());
                    Optional<Usuario> optUsuario = usuarioRepository.findUsuarioByEmail(fundoImobiliario.getUsuarioEmail());
                    optUsuario.ifPresent(f::setUsuario);
                    f.setPrecoMinimo(fundoImobiliario.getPrecoMinimo());
                    f.setPrecoMaximo(fundoImobiliario.getPrecoMaximo());

                    f.setCodigoFii(fundoImobiliario.getCodigoFii());
                    f.setRendimentoMensal(fundoImobiliario.getRendimentoMensal());

                    fundoImobiliarioRepository.save(f);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Fundo Imobiliário atualizado com sucesso.");
                }catch (Exception e){
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde salvar o fundo imobiliário atualizado.\n" + e.getLocalizedMessage());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O fundo imobiliário não pôde ser atualizado.");
    }

    @Override
    public ResponseEntity<String> cadastrar(FundoImobiliarioDTO fundoImobiliario) {
        try{
            AcaoApi api = finnhubClient.buscarInformacoesAtivo(fundoImobiliario.getCodigoFii());
            if(api.getPrecoAtual().compareTo(BigDecimal.ZERO) == 0)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O fundo imobiliário não pode ser cadastrado com um código de um ativo inexistente.");

            fundoImobiliario.setPrecoAtual(api.getPrecoAtual());
            fundoImobiliario.setDataCadastro(LocalDateTime.now());

            Optional<Usuario> optUsuario = usuarioRepository.findUsuarioByEmail(fundoImobiliario.getUsuarioEmail());
            if(optUsuario.isPresent()) {
                Usuario u = optUsuario.get();
                List<FundoImobiliario> lista = u.getFundosImobiliariosFavoritos();
                if (lista == null) lista = new ArrayList<>();
                FundoImobiliario f = fundoImobiliario.toEntity();
                f.setUsuario(u);
                lista.add(f);
                u.setFundosImobiliariosFavoritos(lista);

                usuarioRepository.save(u);
                return ResponseEntity.status(HttpStatus.CREATED).body("Fundo imobiliário cadastrado com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há um usuário com o e-mail registrado pelo fundo imobiliário.");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde salvar o fundo imobiliário a ser cadastrado.\n" + e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<String> apagar(Long id) {
        Optional<FundoImobiliario> opt = fundoImobiliarioRepository.findFundoImobiliarioById(id);
        if(opt.isPresent()){
            FundoImobiliario f = opt.get();
            try{
                Usuario u = f.getUsuario();
                u.getFundosImobiliariosFavoritos().remove(f);
                fundoImobiliarioRepository.delete(f);
                usuarioRepository.save(u);
                return ResponseEntity.status(HttpStatus.OK).body("Fundo imobiliário apagado com sucesso.");
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde apagar o fundo imobiliário.\n" + e.getLocalizedMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O fundo imobiliário não pôde ser apagado.");
    }
}
