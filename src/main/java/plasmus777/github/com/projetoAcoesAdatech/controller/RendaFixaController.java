package plasmus777.github.com.projetoAcoesAdatech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;
import plasmus777.github.com.projetoAcoesAdatech.service.RendaFixaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/rendasfixas")
public class RendaFixaController {

    private final RendaFixaService rendaFixaService;

    public RendaFixaController(RendaFixaService rendaFixaService){
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
    public ResponseEntity<String> atualizarRendaFixa(@PathVariable Long id, @RequestBody RendaFixa novaRendaFixa){
        return rendaFixaService.atualizar(id, novaRendaFixa);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarRendaFixa(@RequestBody RendaFixa RendaFixa){
        return rendaFixaService.cadastrar(RendaFixa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarRendaFixa(@PathVariable Long id){
        return rendaFixaService.apagar(id);
    }
}
