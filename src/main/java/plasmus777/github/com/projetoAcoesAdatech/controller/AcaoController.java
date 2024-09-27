package plasmus777.github.com.projetoAcoesAdatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.dto.AcaoDTO;
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
    public List<AcaoDTO> obterAcoes() {
        return acaoService.obterLista();
    }

    @GetMapping("/id/{id}")
    public AcaoDTO obterAcao(@PathVariable Long id){
        Optional<AcaoDTO> opt = acaoService.obter(id);

        return opt.orElse(null);
    }

    @GetMapping("/codigo/{codigoNegociacao}")
    public AcaoDTO obterAcaoPorCodigo(@PathVariable String codigoNegociacao){
        Optional<AcaoDTO> opt = acaoService.obterPorCodigoNegociacao(codigoNegociacao);

        return opt.orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAcao(@PathVariable Long id, @Valid @RequestBody AcaoDTO novaAcao){
        return acaoService.atualizar(id, novaAcao);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarAcao(@Valid @RequestBody AcaoDTO acao){
        return acaoService.cadastrar(acao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarAcao(@PathVariable Long id){
        return acaoService.apagar(id);
    }
}