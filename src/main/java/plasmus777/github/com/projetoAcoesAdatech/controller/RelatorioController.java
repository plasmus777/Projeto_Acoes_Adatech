package plasmus777.github.com.projetoAcoesAdatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.Relatorio;
import plasmus777.github.com.projetoAcoesAdatech.service.RelatorioService;

import java.util.Optional;

@Tag(name = "RelatorioController", description = "Controller para gerar relatórios de ativos pela aplicação")
@RestController
@RequestMapping("/api/v1/relatorios")
@CrossOrigin(origins = {"http://localhost:5173", "https://projeto-acoes-adatech-react.vercel.app/"})
public class RelatorioController {
    private RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService){
        this.relatorioService = relatorioService;
    }

    @Operation(
            summary = "Retorna um relatório de um ativo financeiro",
            description = "Busca por um ativo financeiro através das APIs externas utilizando um código de identificação, e retorna um novo relatório gerado caso o ativo com o código definido seja encontrado pelo sistema.")
    @GetMapping("/{codigo}")
    public Relatorio obterRelatorio(@PathVariable String codigo){
        Optional<Relatorio> opt = relatorioService.gerarRelatorio(codigo);

        if(opt.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foram encontrados ativos financeiros com código \"" + codigo + "\".");
        else return opt.orElse(null);
    }
}
