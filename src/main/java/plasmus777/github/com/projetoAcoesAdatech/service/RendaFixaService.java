package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;
import plasmus777.github.com.projetoAcoesAdatech.repository.RendaFixaRepository;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RendaFixaService implements RestService<RendaFixa>{

    private final RendaFixaRepository rendaFixaRepository;
    private final UsuarioRepository usuarioRepository;

    public RendaFixaService(RendaFixaRepository rendaFixaRepository, UsuarioRepository usuarioRepository){
        this.rendaFixaRepository = rendaFixaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<RendaFixa> obterLista() {
        return rendaFixaRepository.findAll();
    }

    @Override
    public Optional<RendaFixa> obter(Long id) {
        return rendaFixaRepository.findRendaFixaById(id);
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
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde salvar a renda fixa atualizada.\n" + e.getLocalizedMessage());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("A renda fixa não pôde ser atualizada.");
    }

    @Override
    public ResponseEntity<String> cadastrar(RendaFixa rendaFixa) {
        try{
            rendaFixa.setDataCadastro(LocalDateTime.now());

            List<RendaFixa> lista = rendaFixa.getUsuario().getRendasFixasFavoritas();
            if(lista == null) lista = new ArrayList<>();
            lista.add(rendaFixa);
            rendaFixa.getUsuario().setRendasFixasFavoritas(lista);

            usuarioRepository.save(rendaFixa.getUsuario());
            return ResponseEntity.status(HttpStatus.CREATED).body("Renda fixa cadastrada com sucesso.");
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
                rendaFixaRepository.delete(r);
                return ResponseEntity.status(HttpStatus.OK).body("Renda fixa apagada com sucesso.");
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O repositório não pôde apagar a renda fixa.\n" + e.getLocalizedMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("A renda fixa não pôde ser apagado.");
    }
}
