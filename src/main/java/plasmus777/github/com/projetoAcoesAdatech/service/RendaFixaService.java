package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;
import plasmus777.github.com.projetoAcoesAdatech.repository.RendaFixaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RendaFixaService implements RestService<RendaFixa>{

    private final RendaFixaRepository rendaFixaRepository;

    public RendaFixaService(RendaFixaRepository rendaFixaRepository){
        this.rendaFixaRepository = rendaFixaRepository;
    }

    @Override
    public List<RendaFixa> obterLista() {
        return rendaFixaRepository.findAll();
    }

    @Override
    public Optional<RendaFixa> obter(Long id) {
        return rendaFixaRepository.findById(id);
    }

    @Override
    public ResponseEntity<String> atualizar(Long id, RendaFixa rendaFixa) {
        Optional<RendaFixa> opt = rendaFixaRepository.findRendaFixaById(id);
        if(opt.isPresent()){
            RendaFixa r = opt.get();

            if(rendaFixa != null){
                r.setNome(rendaFixa.getNome());
                r.setPrecoAtual(rendaFixa.getPrecoAtual());
                r.setPrecoCompra(rendaFixa.getPrecoCompra());
                r.setDataCadastro(rendaFixa.getDataCadastro());
                r.setUsuario(rendaFixa.getUsuario());
                r.setPrecoMinimo(rendaFixa.getPrecoMinimo());
                r.setPrecoMaximo(rendaFixa.getPrecoMaximo());

                r.setTaxaRetorno(rendaFixa.getTaxaRetorno());
                r.setDataVencimento(rendaFixa.getDataVencimento());

                try {
                    rendaFixaRepository.save(r);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Renda fixa atualizada com sucesso.");
                }catch (Exception e){
                    throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar a renda fixa atualizada.");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("A renda fixa não pôde ser atualizada.");
    }

    @Override
    public ResponseEntity<String> cadastrar(RendaFixa rendaFixa) {
        try{
            rendaFixaRepository.save(rendaFixa);
            return ResponseEntity.status(HttpStatus.CREATED).body("Renda fixa cadastrada com sucesso.");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar a renda fixa a ser cadastrada.");
        }
    }

    @Override
    public ResponseEntity<String> apagar(Long id) {
        Optional<RendaFixa> opt = rendaFixaRepository.findRendaFixaById(id);
        if(opt.isPresent()){
            RendaFixa r = opt.get();
            try{
                rendaFixaRepository.delete(r);
                return ResponseEntity.status(HttpStatus.OK).body("Renda fixa apagada com sucesso.");
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde apagar a renda fixa.");
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("A renda fixa não pôde ser apagada.");
    }
}
