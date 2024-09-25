package plasmus777.github.com.projetoAcoesAdatech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.service.AcaoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/acoes")
public class AcaoController {

    private final AcaoService acaoService;

    public AcaoController(AcaoService acaoService){
        this.acaoService = acaoService;
    }

    @GetMapping()
    public List<Acao> obterAcoes() {
        return acaoService.obterLista();
    }

    @GetMapping("/{id}")
    public Acao obterAcao(@PathVariable Long id){
        Optional<Acao> opt = acaoService.obter(id);

        return opt.orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAcao(@PathVariable Long id, @RequestBody Acao novaAcao){
        return acaoService.atualizar(id, novaAcao);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarAcao(@RequestBody Acao acao){
        return acaoService.cadastrar(acao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarAcao(@PathVariable Long id){
        return acaoService.apagar(id);
    }
}