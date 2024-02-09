package fr.apithinking.apigreenscore.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jbpirauxnewpj
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemaCreation {

    @JsonProperty("champs")
    private List<ChampSchemaCreation> listeChamps;

    @JsonProperty("url_cgu_globales")
    private String urlCguGlobales;

    @JsonProperty("url_cgu_pj")
    private String urlCguPj;

}
