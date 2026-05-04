package bartzmoveis.apigetitem.dto;

import lombok.Data;

@Data
public class CorDTO {
    
    //(name = "SIGLA_COR")
    private String siglaCor;

    //(name = "DESCRICAO")
    private String descricao;

    public CorDTO(){}

    public CorDTO(String siglaCor, String descricao){
        this.siglaCor = siglaCor;
        this.descricao = descricao;
    }
}
