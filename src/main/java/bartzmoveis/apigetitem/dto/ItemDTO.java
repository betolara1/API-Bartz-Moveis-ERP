package bartzmoveis.apigetitem.dto;

import lombok.Data;

@Data
public class ItemDTO {
    
    //(name = "ITEM")
    private String codeItem;
    
    //(name = "DESCRICAO")
    private String description;
    
    //(name = "REF_COMERCIAL")
    private String refComercial;

    public ItemDTO(){}

    public ItemDTO(String codeItem, String description, String refComercial) {
        this.codeItem = codeItem;
        this.description = description;
        this.refComercial = refComercial;
    }
}
