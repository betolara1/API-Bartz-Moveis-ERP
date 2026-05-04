package bartzmoveis.apigetitem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bartzmoveis.apigetitem.dto.ItemDTO;
import bartzmoveis.apigetitem.service.ItemService;

// Esta classe é o controlador REST para a entidade Item, responsável por expor os 
// endpoints da API e lidar com as requisições HTTP
@RestController

// O @RequestMapping define a rota base para todos os endpoints deste
// controlador, ou seja,
// todos os endpoints começarão com /item
@RequestMapping("/item")

// O @CrossOrigin é usado para permitir requisições de origens específicas, como
// o frontend
// rodando em localhost ou um arquivo local
@CrossOrigin(origins = { "http://192.168.1.10:50000", "http://localhost:5173", "file://" })
public class ItemController {

    private ItemService service;
    private ItemController (ItemService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> listAll() {
        List<ItemDTO> listItem = service.listAll();

        if(listItem.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listItem);
    }


    // URL: /item/search-code?code=10.01
    @GetMapping("/codigo")
    public ResponseEntity<List<ItemDTO>> searchByCode(@RequestParam("code") String query) {
        List<ItemDTO> results = service.findByCode(query);

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não achar nada
        }
        return ResponseEntity.ok(results);
    }

    // URL: /item/search-desc?desc=branco
    @GetMapping("/descricao")
    public ResponseEntity<List<ItemDTO>> searchByDescription(@RequestParam("desc") String query) {
        List<ItemDTO> results = service.findByDescription(query);

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }
}