package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.repository.FundoImobiliarioRepository;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FundoImobiliarioService implements RestService<FundoImobiliario> {

    private final FundoImobiliarioRepository fundoImobiliarioRepository;
    private final UsuarioRepository usuarioRepository;

    public FundoImobiliarioService(FundoImobiliarioRepository fundoImobiliarioRepository, UsuarioRepository usuarioRepository){
        this.fundoImobiliarioRepository = fundoImobiliarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<FundoImobiliario> obterLista() {
        return fundoImobiliarioRepository.findAll();
    }

    @Override
    public Optional<FundoImobiliario> obter(Long id) {
        return fundoImobiliarioRepository.findFundoImobiliarioById(id);
    }

    @Override
    public ResponseEntity<String> atualizar(Long id, FundoImobiliario fundoImobiliario) {
        Optional<FundoImobiliario> opt = fundoImobiliarioRepository.findFundoImobiliarioById(id);
        if(opt.isPresent()){
            FundoImobiliario f = opt.get();

            if(fundoImobiliario != null){
                f.setNome(fundoImobiliario.getNome());
                f.setPrecoAtual(fundoImobiliario.getPrecoAtual());
                f.setPrecoCompra(fundoImobiliario.getPrecoCompra());
                f.setDataCadastro(fundoImobiliario.getDataCadastro());
                f.setUsuario(fundoImobiliario.getUsuario());
                f.setPrecoMinimo(fundoImobiliario.getPrecoMinimo());
                f.setPrecoMaximo(fundoImobiliario.getPrecoMaximo());

                f.setCodigoFii(fundoImobiliario.getCodigoFii());
                f.setRendimentoMensal(fundoImobiliario.getRendimentoMensal());

                try {
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
    public ResponseEntity<String> cadastrar(FundoImobiliario fundoImobiliario) {
        try{
            fundoImobiliario.setDataCadastro(LocalDateTime.now());

            List<FundoImobiliario> lista = fundoImobiliario.getUsuario().getFundosImobiliariosFavoritos();
            if(lista == null) lista = new ArrayList<>();
            lista.add(fundoImobiliario);
            fundoImobiliario.getUsuario().setFundosImobiliariosFavoritos(lista);

            usuarioRepository.save(fundoImobiliario.getUsuario());
            return ResponseEntity.status(HttpStatus.CREATED).body("Fundo imobiliário cadastrado com sucesso.");
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
                fundoImobiliarioRepository.delete(f);
                return ResponseEntity.status(HttpStatus.OK).body("Fundo imobiliário apagado com sucesso.");
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde apagar o fundo imobiliário.\n" + e.getLocalizedMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O fundo imobiliário não pôde ser apagado.");
    }
}
