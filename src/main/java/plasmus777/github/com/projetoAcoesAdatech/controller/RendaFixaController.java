package plasmus777.github.com.projetoAcoesAdatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.dto.RendaFixaDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.RendaFixaService;

import java.util.List;
import java.util.Optional;

@Tag(name = "RendaFixaController", description = "Controller para gerenciar rendas fixas na aplicação")
@RestController
@RequestMapping("api/v1/rendasfixas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RendaFixaController {

    private final RendaFixaService rendaFixaService;

    public RendaFixaController(RendaFixaService rendaFixaService){
        this.rendaFixaService = rendaFixaService;
    }

    @Operation(
            summary = "Retorna todas as rendas fixas cadastradas",
            description = "Retorna uma lista de todas as rendas fixas cadastradas por usuários no sistema.")
    @GetMapping()
    public List<RendaFixaDTO> obterRendasFixas() {
        return rendaFixaService.obterLista();
    }

    @Operation(
            summary = "Retorna uma renda fixa específica cadastrada",
            description = "Busca por uma renda fixa através de um identificador especificado e a retorna caso encontrada no sistema.")
    @GetMapping("/id/{id}")
    public RendaFixaDTO obterRendaFixa(@PathVariable Long id){
        Optional<RendaFixaDTO> opt = rendaFixaService.obter(id);

        return opt.orElse(null);
    }

    @Operation(
            summary = "Retorna uma renda fixa específica cadastrada",
            description = "Busca por uma renda fixa através de um código especificado e a retorna caso encontrada no sistema.")
    @GetMapping("/codigo/{codigo}")
    public RendaFixaDTO obterRendaFixaPorCodigo(@PathVariable String codigo){
        Optional<RendaFixaDTO> opt = rendaFixaService.obterPorCodigo(codigo);

        return opt.orElse(null);
    }

    @Operation(
            summary = "Atualiza uma renda fixa específica cadastrada",
            description = "Atualiza uma renda fixa através de um identificador especificado e retorna uma resposta.")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarRendaFixa(@PathVariable Long id, @Valid @RequestBody RendaFixaDTO novaRendaFixa){
        return rendaFixaService.atualizar(id, novaRendaFixa);
    }

    @Operation(
            summary = "Cadastra uma renda fixa no sistema",
            description = "Cadastra uma renda fixa no sistema e retorna uma resposta.")
    @PostMapping
    public ResponseEntity<String> cadastrarRendaFixa(@Valid @RequestBody RendaFixaDTO rendaFixa){
        return rendaFixaService.cadastrar(rendaFixa);
    }

    @Operation(
            summary = "Apaga uma renda fixa específica cadastrada",
            description = "Apaga uma renda fixa do sistema através de um identificador especificado e retorna uma resposta.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarRendaFixa(@PathVariable Long id){
        return rendaFixaService.apagar(id);
    }
}
