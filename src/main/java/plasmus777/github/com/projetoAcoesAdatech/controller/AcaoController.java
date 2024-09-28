package plasmus777.github.com.projetoAcoesAdatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.dto.AcaoDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.AcaoService;

import java.util.List;
import java.util.Optional;

@Tag(name = "AcaoController", description = "Controller para gerenciar ações na aplicação")
@RestController
@RequestMapping("api/v1/acoes")
public class AcaoController {

    private final AcaoService acaoService;

    public AcaoController(AcaoService acaoService){
        this.acaoService = acaoService;
    }

    @Operation(
            summary = "Retorna todas as ações cadastradas",
            description = "Retorna uma lista de todas as ações cadastradas por usuários no sistema.")
    @GetMapping()
    public List<AcaoDTO> obterAcoes() {
        return acaoService.obterLista();
    }

    @Operation(
            summary = "Retorna uma ação específica cadastrada",
            description = "Busca por uma ação através de um identificador especificado e a retorna caso encontrada no sistema.")
    @GetMapping("/id/{id}")
    public AcaoDTO obterAcao(@PathVariable Long id){
        Optional<AcaoDTO> opt = acaoService.obter(id);

        return opt.orElse(null);
    }

    @Operation(
            summary = "Retorna uma ação específica cadastrada",
            description = "Busca por uma ação através de um código de negociação especificado e a retorna caso encontrada no sistema.")
    @GetMapping("/codigo/{codigoNegociacao}")
    public AcaoDTO obterAcaoPorCodigo(@PathVariable String codigoNegociacao){
        Optional<AcaoDTO> opt = acaoService.obterPorCodigoNegociacao(codigoNegociacao);

        return opt.orElse(null);
    }

    @Operation(
            summary = "Atualiza uma ação específica cadastrada",
            description = "Atualiza uma ação através de um identificador especificado e retorna uma resposta.")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAcao(@PathVariable Long id, @Valid @RequestBody AcaoDTO novaAcao){
        return acaoService.atualizar(id, novaAcao);
    }

    @Operation(
            summary = "Cadastra uma ação no sistema",
            description = "Cadastra uma ação no sistema e retorna uma resposta.")
    @PostMapping
    public ResponseEntity<String> cadastrarAcao(@Valid @RequestBody AcaoDTO acao){
        return acaoService.cadastrar(acao);
    }

    @Operation(
            summary = "Apaga uma ação específica cadastrada",
            description = "Apaga uma ação do sistema através de um identificador especificado e retorna uma resposta.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarAcao(@PathVariable Long id){
        return acaoService.apagar(id);
    }
}