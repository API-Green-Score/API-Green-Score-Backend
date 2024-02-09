package fr.pagesjaunes.utilisateur.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Objet cgu de l'API utilisateur.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cgu {

    @JsonProperty("entite")
    private TypeEntiteCGU entiteCgu;

    @JsonProperty("date")
    private Date dateCgu;

}
