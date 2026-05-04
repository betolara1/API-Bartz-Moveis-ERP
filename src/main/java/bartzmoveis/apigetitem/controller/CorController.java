package bartzmoveis.apigetitem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bartzmoveis.apigetitem.dto.CorDTO;
import bartzmoveis.apigetitem.service.CorService;

@RestController
@RequestMapping("/cor")
@CrossOrigin(origins = {"http://192.168.1.10:50000", "http://localhost:5173", "file://"})
public class CorController {
    
    private CorService service;
    private CorController(CorService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CorDTO>> listAll(){

        List<CorDTO> listCor = service.listAll();

        if(listCor.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listCor);
    }

    @GetMapping("/cor")
    public ResponseEntity<List<CorDTO>> findBySiglaCor(@RequestParam("code") String query){
        List<CorDTO> results = service.findBySiglaCor(query);
        if(results.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping("/descricao")
    public ResponseEntity<List<CorDTO>> searchByDescricao(@RequestParam("desc") String query){
        List<CorDTO> results = service.findByDescricao(query);
        if(results.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }
}
