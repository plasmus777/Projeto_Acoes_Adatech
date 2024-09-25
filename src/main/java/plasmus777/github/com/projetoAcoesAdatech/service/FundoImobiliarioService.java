package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.repository.FundoImobiliarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FundoImobiliarioService implements RestService<FundoImobiliario> {

    private final FundoImobiliarioRepository fundoImobiliarioRepository;

    public FundoImobiliarioService(FundoImobiliarioRepository fundoImobiliarioRepository){
        this.fundoImobiliarioRepository = fundoImobiliarioRepository;
    }

    @Override
    public List<FundoImobiliario> obterLista() {
        return fundoImobiliarioRepository.findAll();
    }

    @Override
    public Optional<FundoImobiliario> obter(Long id) {
        return fundoImobiliarioRepository.findById(id);
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
                    return ResponseEntity.status(HttpStatus.CREATED).body("Fundo imobiliário atualizado com sucesso.");
                }catch (Exception e){
                    throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar o fundo imobiliário atualizado.");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O fundo imobiliário não pôde ser atualizado.");
    }

    @Override
    public ResponseEntity<String> cadastrar(FundoImobiliario fundoImobiliario) {
        try{
            fundoImobiliarioRepository.save(fundoImobiliario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Fundo imobiliário cadastrado com sucesso.");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar o fundo imobiliário a ser cadastrado.");
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
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde apagar o fundo imobiliário.");
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O fundo imobiliário não pôde ser apagado.");
    }
}
