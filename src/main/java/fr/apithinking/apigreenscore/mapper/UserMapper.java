package fr.apithinking.apigreenscore.mapper;

import fr.apithinking.apigreenscore.modele.TypeCivilite;
import fr.apithinking.apigreenscore.modele.Utilisateur;
import fr.apithinking.apigreenscore.provider.mongo.modele.UserMongo;
import org.mapstruct.*;

@Mapper
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    @Mapping(target = "id", source = "idAuthPartner")
//    @Mapping(target = "typeMdp", source = "data.commun.infosMdp.type") // deprecated => voir avec FDE si bien à supprimer
//    @Mapping(target = "dateModifMdp", source = "data.commun.infosMdp.dateModif") // deprecated => voir avec FDE si bien à supprimer
    @Mapping(target = ".", source = "data.commun")
//    @Mapping(target = "commentaireDesactivation", source = "data.commun.commentaireDesactivation")
//    @Mapping(target = "partageDonnees", source = "data.commun.partageDonnees")
//    @Mapping(target = "idStatistique", source = "data.commun.idStatistique")
//    @Mapping(target = "idUtilisateurExterne", source = "data.commun.idUtilisateurExterne")
//    @Mapping(target = "appareilOrigine", source = "data.commun.appareilOrigine")
//    @Mapping(target = "profilPjExiste", source = "data.commun.pagesjaunes.profilPjExiste")
    @Mapping(target = ".", source = "data.pjProfil")
//    @Mapping(target = "civilite", source = "data.pjProfil.civilite")
    @Mapping(target = "dateNaissance", source = "data.pjProfil.dateNaissance", dateFormat = "yyyy-MM-dd")
//    @Mapping(target = "pseudo", source = "data.pjProfil.pseudo")
//    @Mapping(target = "nom", source = "data.pjProfil.nom")
//    @Mapping(target = "prenom", source = "data.pjProfil.prenom")
//    @Mapping(target = "adresse", source = "data.pjProfil.adresse")
//    @Mapping(target = "idLocalite", source = "data.pjProfil.idLocalite")
//    @Mapping(target = "cheminAvatar", source = "data.pjProfil.cheminAvatar")
//    @Mapping(target = "numeroMobile", source = "data.pjProfil.numeroMobile")
    @Mapping(target = "dateCreationProfil", source = "data.pjProfil.dateCreation")
//    @Mapping(target = "inscritNewsletter", source = "data.pjProfil.inscritNewsletter")
//    @Mapping(target = "origineCreation", source = "data.pjProfil.origineCreation")
    @Mapping(target = "active", ignore = true) // géré par le UserMapperDecorator
    @Mapping(target = "metadonnes", ignore = true) // objet non géré dans la BD (TODO DDC : check quand création / mise à jour de données)
    @Mapping(target = "profilPjComplet", ignore = true) // géré par le UserMapperDecorator
    @Mapping(target = "listeCgus", ignore = true) // géré par le UserMapperDecorator
    @Mapping(target = "enModeration", ignore = true) // géré par le UserMapperDecorator
    @Mapping(target = "listeProfilsCompte", ignore = true) // objet non géré dans la BD (TODO DDC : to check)
    Utilisateur buildUtilisateurFromUserMongo(UserMongo source);

    @ValueMappings({
            @ValueMapping(target = "M", source = "M"),
            @ValueMapping(target = "MME", source = "MME"),
            @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.THROW_EXCEPTION)
    })
    TypeCivilite buildTypeCivilite(String civilite);

}
