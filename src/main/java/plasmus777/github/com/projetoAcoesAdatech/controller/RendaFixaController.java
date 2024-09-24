package plasmus777.github.com.projetoAcoesAdatech.controller;

import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;
import plasmus777.github.com.projetoAcoesAdatech.service.RestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/rendasfixas")
public class RendaFixaController {

    private final RestService<RendaFixa> rendaFixaService;

    public RendaFixaController(RestService<RendaFixa> rendaFixaService){
        this.rendaFixaService = rendaFixaService;
    }

    @GetMapping()
    public List<RendaFixa> obterRendasFixas() {
        return rendaFixaService.obterLista();
    }

    @GetMapping("/{id}")
    public RendaFixa obterRendaFixa(@PathVariable Long id){
        Optional<RendaFixa> opt = rendaFixaService.obter(id);

        return opt.orElse(null);
    }

    @PutMapping("/{id}")
    public void atualizarRendaFixa(@PathVariable Long id, @RequestBody RendaFixa novaRendaFixa){
        rendaFixaService.atualizar(id, novaRendaFixa);
    }

    @PostMapping
    public void cadastrarRendaFixa(@RequestBody RendaFixa RendaFixa){
        rendaFixaService.cadastrar(RendaFixa);
    }

    @DeleteMapping("/{id}")
    public void apagarRendaFixa(@PathVariable Long id){
        rendaFixaService.apagar(id);
    }
}
