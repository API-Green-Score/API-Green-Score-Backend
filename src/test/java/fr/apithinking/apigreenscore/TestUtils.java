package fr.apithinking.apigreenscore;

import fr.apithinking.apigreenscore.modele.*;
import fr.apithinking.apigreenscore.provider.mongo.modele.DataMongo;
import fr.apithinking.apigreenscore.provider.mongo.modele.RegistrationMongo;
import fr.apithinking.apigreenscore.provider.mongo.modele.UserMongo;
import fr.apithinking.apigreenscore.provider.mongo.modele.commun.CommunMongo;
import fr.apithinking.apigreenscore.provider.mongo.modele.pjprofil.ModerationMongo;
import fr.apithinking.apigreenscore.provider.mongo.modele.pjprofil.PjProfilMongo;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class TestUtils {

    public static UserMongo buildUserMongo() {
        return buildUserMongo(1);
    }

    public static UserMongo buildUserMongo(int index) {
        return buildUserMongo(index, TypeEtatCompte.ACTIVE, true, true, true, true, true, true);
    }

    public static UserMongo buildUserMongo(
            int index,
            TypeEtatCompte etatCompte,
            boolean withData,
            boolean withDataCommon,
            boolean withDataPjProfil,
            boolean withModerationData,
            boolean isPseudoOrAvatarInModeration,
            boolean isCGUAcceptationDates
            ) {
        UserMongo.UserMongoBuilder userMongoBuilder = UserMongo.builder()
//            .id("ID" + index)
                .dateCreationCompte(new Date())
                .email("EMAIL" + index)
                .etatCompte(etatCompte)
                .idAuthPartner("id_utilisateur" + index)
                .lastLoginCompte(new Date())
                .lastUpdatedTimestamp(Long.valueOf(new Date().getTime()))
                .registration(RegistrationMongo.builder()
                        .registrationStatus(TypeRegistrationStatus.OK)
                        .registrationToken("TOKEN" + index)
                        .build())
                .reseauSocialAssocie(OrigineCreation.GOOGLE)
                .reseauSocialAssocie(OrigineCreation.FACEBOOK);

        if (withData) {
            DataMongo.DataMongoBuilder dataMongoBuilder = DataMongo.builder();
            if (withDataCommon) {
                CommunMongo.CommunMongoBuilder commonBuilder = CommunMongo.builder()
                        .appareilOrigine(AppareilOrigine.Fixe)
                        .commentaireDesactivation("COMMENTAIRE_DESACTIVATION" + index)
                        .idStatistique("ID_STATISTIQUE" + index)
                        .idUtilisateurExterne("ID_UTILISATEUR_EXTERNE" + index)
                        .newsletter(true)
                        .partageDonnees(true)
//                                .clicrdv(ClicRdvMongo.builder()
//                                        .profilExiste(true)
//                                        .build())
//                                .chronoresto(ChronoRestoMongo.builder()
//                                        .profilExiste(true)
//                                        .build())
//                                .hamak(HamakMongo.builder()
//                                        .profilExiste(true)
//                                        .build())
                ;

                if (isCGUAcceptationDates) {
                    commonBuilder.dateAcceptationCgus(new Date());
                }

                dataMongoBuilder.commun(commonBuilder.build());
            }

            if (withDataPjProfil) {

                PjProfilMongo.PjProfilMongoBuilder pjProfilBuilder = PjProfilMongo.builder()
                        .adresse("ADRESSE" + index)
                        .civilite("MME")
                        .cheminAvatar("CHEMIN_AVATAR" + index)
                        .codeOrigineSollicitation("CODE_ORIGINE_SOLICITATION")
                        .dateCreation(new Date())
                        .dateNaissance("2024-01-31")
                        .idLocalite("ID_LOCALITE" + index)
                        .inscritNewsletter(true)
                        .lastActiviteCompte(new Date())
                        .nom("NOM" + index)
                        .numeroMobile("NUMERO_MOBILE")
                        .numeroMobileVerifie(true)
                        .origineCreation(OrigineCreation.SITE)
                        .origineCreationPseudo(OrigineCreationPseudo.AUTO)
                        .parcoursOrigineCreation("PARCOURS_ORIGINE_CREATION")
                        .prenom("PRENOM" + index)
                        .pseudo("PSEUDO" + index)
                        .systemeOrigine(SystemeOrigine.BROWSER)
                        .villeForcee(true);

                if (isCGUAcceptationDates) {
                    pjProfilBuilder.dateAcceptationCgus(new Date());
                }

                if (withModerationData) {
                    pjProfilBuilder.moderation(ModerationMongo.builder()
                            .avatarEnModeration(isPseudoOrAvatarInModeration)
                            .avatarRaisonRefus("AVATAR_RAISON_REFUS")
                            .cheminAvatarPrecedent("CHEMIN_AVATAR_PRECEDENT" + index)
                            .dateDerniereModeration(new Date())
                            .dateMiseModeration(new Date())
                            .moderateur("MODERATEUR" + index)
                            .pseudoAModerer("PSEUDO_A_MODERER" + index)
                            .pseudoEnModeration(isPseudoOrAvatarInModeration)
                            .pseudoPrecedent("PSEUDO_PRECEDENT" + index)
                            .pseudoRaisonRefus("PSEUDO_RAISON_REFUS")
                            .cheminAvatarAModerer("CHEMIN_AVATAR_A_MODERER" + index)
                            .build());
                }

                dataMongoBuilder.pjProfil(pjProfilBuilder.build()).build();
            }

            userMongoBuilder.data(dataMongoBuilder.build());

        }

        return userMongoBuilder.build();
    }

}
