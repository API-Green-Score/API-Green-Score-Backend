package fr.apithinking.apigreenscore.su.impl;

import fr.apithinking.apigreenscore.modele.Utilisateur;
import fr.apithinking.apigreenscore.sm.SMUtilisateur;
import fr.apithinking.apigreenscore.su.SUUtilisateur;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utilisateurs")
@Tag(name = "Utilisateur")
@Slf4j
public class SUUtilisateurImpl implements SUUtilisateur {

    private static final String PARAM_ID_UTILISATEUR = "id_utilisateur";

    private static final String PARAM_ID_EXT_UTILISATEUR = "id_externe_utilisateur";

    private static final String PARAM_EMAIL = "email";

    private SMUtilisateur smUtilisateur;

    public SUUtilisateurImpl(
            SMUtilisateur smUtilisateur) {
        this.smUtilisateur = smUtilisateur;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = "/{" + PARAM_ID_UTILISATEUR + "}")
    @Operation(description = "Récupère le compte d'un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class)))
    })
    public Utilisateur getUser(
            @Parameter(description = "Identifiant de l'utilisateur") @PathVariable(PARAM_ID_UTILISATEUR) final String pIdUtilisateur) {

        LOGGER.info("PAS utilisation du MOCK UTILISATEUR (clé FF_TECH_MOCK_USER NON active)");
//        try {
        final Utilisateur utilisateur = smUtilisateur.getUser(pIdUtilisateur);
////            if (utilisateur != null) { // TODO DDC : check si utilisateur non trouvé (donc null) alors le handler d'exception a récupéré la main + ajout commentaire ici expliquant cela

        // TODO DDC : check si avec BRI / FDE si besoin des types de profils car il n'y a plus de Schéma
        // à voir si possible de simplifier en mettant juste un ENUM (CONTRIB / SANTE / ... ) ???
//        utilisateur.setListeProfilsCompte(smUtilisateur.recupererListeTypeProfil(utilisateur));

//            }

        return utilisateur;

//        } catch (UtilisateurInexistantException e) { // TODO DDC : bien checker ce que fait le handler d'exception => renvoie d'un pb au lieu d'un user null => changement de fonctionnel ! à tester
//            return null;
//        }
    }

    @Override
    @GetMapping(path = "/by_external_id-{" + PARAM_ID_EXT_UTILISATEUR + "}")
    @Operation(description = "Récupère le compte d'un utilisateur avec son identifiant externe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class)))
    })
    // TODO DDC : voir avec FDE = pourquoi pas de cache ?
    public Utilisateur getUserByExternalId(
            @Parameter(description = "Identifiant externe de l'utilisateur") @PathVariable(PARAM_ID_EXT_UTILISATEUR) final String pExternalUserId) {
        return smUtilisateur.getUserByExternalId(pExternalUserId);
    }

    @Override
    @GetMapping(path = "/by_email-{" + PARAM_EMAIL + "}")
    @Operation(description = "Récupère le compte d'un utilisateur depuis un email")
    @ApiResponses({ // TODO DDC : à valider avec FDE => ajout SWAGGER qui n'existait pas dans v1
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class)))
    })
    // TODO DDC : voir avec FDE = pourquoi pas de cache ?
    public Utilisateur getUserByEmail(
            @Parameter(description = "Email de l'utilisateur") @PathVariable(PARAM_EMAIL) final String pEmail
            // TODO DDC : voir avec FDE : suppression champ mot de passe car inutilisé dans v1
//            ,
//            @ApiParam(value = QueryParamUtils.SWAGGER_PARAM_MDP, example = "azertyuiop88*") @QueryParam(PARAM_MDP) final String pMotDePasse
    ) {
//        try {
        Utilisateur utilisateur = smUtilisateur.getUserByEmail(pEmail);

        // TODO DDC : check si avec BRI / FDE si besoin des types de profils car il n'y a plus de Schéma
        // à voir si possible de simplifier en mettant juste un ENUM (CONTRIB / SANTE / ... ) ???
//        if (utilisateur != null) {
//            utilisateur.setListeProfilsCompte(smUtilisateur.recupererListeTypeProfil(utilisateur));
//        }

        return utilisateur;
//        } catch (UtilisateurInexistantException e) { // TODO DDC : bien checker ce que fait le handler d'exception => renvoie d'un pb au lieu d'un user null => changement de fonctionnel ! à tester
//            return null;
//        }
    }

}
