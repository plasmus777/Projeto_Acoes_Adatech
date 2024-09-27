package plasmus777.github.com.projetoAcoesAdatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.dto.FundoImobiliarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.FundoImobiliarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/fundosimobiliarios")
public class FundoImobiliarioController {

    private final FundoImobiliarioService fundoImobiliarioService;

    public FundoImobiliarioController(FundoImobiliarioService fundoImobiliarioService){
        this.fundoImobiliarioService = fundoImobiliarioService;
    }

    @GetMapping()
    public List<FundoImobiliarioDTO> obterFundosImobiliarios() {
        return fundoImobiliarioService.obterLista();
    }

    @GetMapping("/id/{id}")
    public FundoImobiliarioDTO obterFundoImobiliario(@PathVariable Long id){
        Optional<FundoImobiliarioDTO> opt = fundoImobiliarioService.obter(id);

        return opt.orElse(null);
    }

    @GetMapping("/codigo/{codigoFii}")
    public FundoImobiliarioDTO obterFundoImobiliarioPorCodigo(@PathVariable String codigoFii){
        Optional<FundoImobiliarioDTO> opt = fundoImobiliarioService.obterPorCodigoFii(codigoFii);

        return opt.orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarFundoImobiliario(@PathVariable Long id, @Valid @RequestBody FundoImobiliarioDTO novoFundoImobiliario){
        return fundoImobiliarioService.atualizar(id, novoFundoImobiliario);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarFundoImobiliario(@Valid @RequestBody FundoImobiliarioDTO fundoImobiliario){
        return fundoImobiliarioService.cadastrar(fundoImobiliario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarFundoImobiliario(@PathVariable Long id){
        return fundoImobiliarioService.apagar(id);
    }
}