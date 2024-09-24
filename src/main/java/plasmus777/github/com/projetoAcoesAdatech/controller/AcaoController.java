package plasmus777.github.com.projetoAcoesAdatech.controller;

import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.service.RestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/acoes")
public class AcaoController {

    private final RestService<Acao> acaoService;

    public AcaoController(RestService<Acao> acaoService){
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
    public void atualizarAcao(@PathVariable Long id, @RequestBody Acao novaAcao){
        acaoService.atualizar(id, novaAcao);
    }

    @PostMapping
    public void cadastrarAcao(@RequestBody Acao acao){
        acaoService.cadastrar(acao);
    }

    @DeleteMapping("/{id}")
    public void apagarAcao(@PathVariable Long id){
        acaoService.apagar(id);
    }
}