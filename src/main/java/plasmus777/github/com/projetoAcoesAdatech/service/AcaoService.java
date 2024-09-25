package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.repository.AcaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AcaoService implements RestService<Acao> {

    private final AcaoRepository acaoRepository;

    public AcaoService(AcaoRepository acaoRepository){
        this.acaoRepository = acaoRepository;
    }

    @Override
    public List<Acao> obterLista() {
        return acaoRepository.findAll();
    }

    @Override
    public Optional<Acao> obter(Long id) {
        return acaoRepository.findAcaoById(id);
    }

    @Override
    public ResponseEntity<String> atualizar(Long id, Acao acao) {
        Optional<Acao> opt = acaoRepository.findAcaoById(id);
        if(opt.isPresent()){
            Acao a = opt.get();

            if(acao != null){
                a.setNome(acao.getNome());
                a.setPrecoAtual(acao.getPrecoAtual());
                a.setPrecoCompra(acao.getPrecoCompra());
                a.setDataCadastro(acao.getDataCadastro());
                a.setUsuario(acao.getUsuario());
                a.setPrecoMinimo(acao.getPrecoMinimo());
                a.setPrecoMaximo(acao.getPrecoMaximo());

                a.setCodigoNegociacao(acao.getCodigoNegociacao());
                a.setQuantidade(acao.getQuantidade());

                try {
                    acaoRepository.save(a);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Ação atualizada com sucesso.");
                }catch (Exception e){
                    throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar a ação atualizada.");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("A ação não pôde ser atualizada.");
    }

    @Override
    public ResponseEntity<String> cadastrar(Acao acao) {
        try{
            acaoRepository.save(acao);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ação cadastrada com sucesso.");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar a ação a ser cadastrada.");
        }
    }

    @Override
    public ResponseEntity<String> apagar(Long id) {
        Optional<Acao> opt = acaoRepository.findAcaoById(id);
        if(opt.isPresent()){
            Acao a = opt.get();
            try{
                acaoRepository.delete(a);
                return ResponseEntity.status(HttpStatus.OK).body("Ação apagada com sucesso.");
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde apagar a ação.");
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("A ação não pôde ser apagada.");
    }
}
