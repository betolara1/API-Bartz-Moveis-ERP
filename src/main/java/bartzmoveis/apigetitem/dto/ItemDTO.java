package bartzmoveis.apigetitem.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ItemDTO {
    
    @Column(name = "ITEM")
    private String codeItem;
    
    @Column(name = "DESCRICAO")
    private String description;
    
    @Column(name = "REF_COMERCIAL")
    private String refComercial;

    public ItemDTO(){}

    public ItemDTO(String codeItem, String description, String refComercial) {
        this.codeItem = codeItem;
        this.description = description;
        this.refComercial = refComercial;
    }
}
