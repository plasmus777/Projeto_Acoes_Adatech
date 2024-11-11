package plasmus777.github.com.projetoAcoesAdatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.dto.FundoImobiliarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.FundoImobiliarioService;

import java.util.List;
import java.util.Optional;

@Tag(name = "FundoImobiliarioController", description = "Controller para gerenciar fundos imobiliários na aplicação")
@RestController
@RequestMapping("api/v1/fundosimobiliarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FundoImobiliarioController {

    private final FundoImobiliarioService fundoImobiliarioService;

    public FundoImobiliarioController(FundoImobiliarioService fundoImobiliarioService){
        this.fundoImobiliarioService = fundoImobiliarioService;
    }

    @Operation(
            summary = "Retorna todos os fundos imobiliários cadastrados",
            description = "Retorna uma lista de todos os fundos imobiliários cadastrados por usuários no sistema.")
    @GetMapping()
    public List<FundoImobiliarioDTO> obterFundosImobiliarios() {
        return fundoImobiliarioService.obterLista();
    }

    @Operation(
            summary = "Retorna um fundo imobiliário específico cadastrado",
            description = "Busca por um fundo imobiliário através de um identificador especificado e o retorna caso encontrado no sistema.")
    @GetMapping("/id/{id}")
    public FundoImobiliarioDTO obterFundoImobiliario(@PathVariable Long id){
        Optional<FundoImobiliarioDTO> opt = fundoImobiliarioService.obter(id);

        return opt.orElse(null);
    }

    @Operation(
            summary = "Retorna um fundo imobiliário específico cadastrado",
            description = "Busca por um fundo imobiliário através de um código de FII especificado e o retorna caso encontrado no sistema.")
    @GetMapping("/codigo/{codigoFii}")
    public FundoImobiliarioDTO obterFundoImobiliarioPorCodigo(@PathVariable String codigoFii){
        Optional<FundoImobiliarioDTO> opt = fundoImobiliarioService.obterPorCodigoFii(codigoFii);

        return opt.orElse(null);
    }

    @Operation(
            summary = "Atualiza um fundo imobiliário específico cadastrado",
            description = "Atualiza um fundo imobiliário através de um identificador especificado e retorna uma resposta.")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarFundoImobiliario(@PathVariable Long id, @Valid @RequestBody FundoImobiliarioDTO novoFundoImobiliario){
        return fundoImobiliarioService.atualizar(id, novoFundoImobiliario);
    }

    @Operation(
            summary = "Cadastra um fundo imobiliário no sistema",
            description = "Cadastra um fundo imobiliário no sistema e retorna uma resposta.")
    @PostMapping
    public ResponseEntity<String> cadastrarFundoImobiliario(@Valid @RequestBody FundoImobiliarioDTO fundoImobiliario){
        return fundoImobiliarioService.cadastrar(fundoImobiliario);
    }

    @Operation(
            summary = "Apaga um fundo imobiliário específico cadastrado",
            description = "Apaga um fundo imobiliário do sistema através de um identificador especificado e retorna uma resposta.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarFundoImobiliario(@PathVariable Long id){
        return fundoImobiliarioService.apagar(id);
    }
}