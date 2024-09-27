package plasmus777.github.com.projetoAcoesAdatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.dto.RendaFixaDTO;
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
    public List<RendaFixaDTO> obterRendasFixas() {
        return rendaFixaService.obterLista();
    }

    @GetMapping("/id/{id}")
    public RendaFixaDTO obterRendaFixa(@PathVariable Long id){
        Optional<RendaFixaDTO> opt = rendaFixaService.obter(id);

        return opt.orElse(null);
    }

    @GetMapping("/codigo/{codigoFii}")
    public RendaFixaDTO obterRendaFixaPorCodigo(@PathVariable String codigo){
        Optional<RendaFixaDTO> opt = rendaFixaService.obterPorCodigo(codigo);

        return opt.orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarRendaFixa(@PathVariable Long id, @Valid @RequestBody RendaFixaDTO novaRendaFixa){
        return rendaFixaService.atualizar(id, novaRendaFixa);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarRendaFixa(@Valid @RequestBody RendaFixaDTO rendaFixa){
        return rendaFixaService.cadastrar(rendaFixa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarRendaFixa(@PathVariable Long id){
        return rendaFixaService.apagar(id);
    }
}
