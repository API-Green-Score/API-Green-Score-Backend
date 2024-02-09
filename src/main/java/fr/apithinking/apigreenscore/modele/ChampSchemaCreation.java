package fr.apithinking.apigreenscore.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChampSchemaCreation {

    @JsonProperty("identifiant")
    private String identifiant;

    @JsonProperty("libelle")
    private String libelle;

    @JsonProperty("obligatoire")
    private Boolean obligatoire;

    @JsonProperty("type")
    private String type;

    @JsonProperty("regex")
    private String regex;

    @JsonProperty("libelle_regex")
    private String libelleRegex;

    @JsonProperty("valeur")
    private String valeur;

    @JsonProperty("origine")
    private TypeChampOrigine origine;

    @JsonProperty("masque")
    private Boolean masque;

}
