package fr.pagesjaunes.utilisateur.mapper;

import fr.pagesjaunes.utilisateur.ApiUtilisateurApplication;
import fr.pagesjaunes.utilisateur.TestUtils;
import fr.pagesjaunes.utilisateur.modele.TypeEntiteCGU;
import fr.pagesjaunes.utilisateur.modele.TypeEtatCompte;
import fr.pagesjaunes.utilisateur.modele.Utilisateur;
import fr.pagesjaunes.utilisateur.provider.mongo.modele.UserMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest(classes = ApiUtilisateurApplication.class)
@ActiveProfiles("it")
class UserMapperIT {

    @Autowired
    private UserMapper userMapper;

    @Test
    void shouldBuildUtilisateurFromUserMongoWhenGivenStandardFieldsAndForUserActivationTrue() throws ParseException {

        UserMongo userMongo = TestUtils.buildUserMongo();
        Utilisateur utilisateur = userMapper.buildUtilisateurFromUserMongo(userMongo);

        assertUtilisateurFromUserMongo(utilisateur, userMongo);
    }

    @Test
    void shouldBuildUtilisateurFromUserMongoWhenGivenStandardFieldsAndUserActivationInProgress() throws ParseException {

        UserMongo userMongo = TestUtils.buildUserMongo(
                1,
                TypeEtatCompte.EN_ATTENTE_ACTIVATION,
                true,
                true,
                true,
                true,
                true,
                true
        );
        Utilisateur utilisateur = userMapper.buildUtilisateurFromUserMongo(userMongo);

        assertUtilisateurFromUserMongo(utilisateur, userMongo);
    }

    @Test
    void shouldBuildUtilisateurFromUserMongoWhenGivenStandardFieldsAndUserActivationDisabled() throws ParseException {

        UserMongo userMongo = TestUtils.buildUserMongo(
                1,
                TypeEtatCompte.DESACTIVE,
                true,
                true,
                true,
                true,
                true,
                true
        );
        Utilisateur utilisateur = userMapper.buildUtilisateurFromUserMongo(userMongo);

        assertUtilisateurFromUserMongo(
                utilisateur,
                userMongo,
                false,
                true,
                true,
                true,
                true,
                true
        );
    }

    @Test
    void shouldBuildUtilisateurFromUserMongoWhenDataNotGiven() throws ParseException {

        UserMongo userMongo = TestUtils.buildUserMongo(
                1,
                TypeEtatCompte.ACTIVE,
                false,
                false,
                false,
                false,
                false,
                false
        );
        Utilisateur utilisateur = userMapper.buildUtilisateurFromUserMongo(userMongo);

        assertUtilisateurFromUserMongo(utilisateur,
                userMongo,
                true,
                false,
                false,
                false,
                false,
                false
        );
    }

    @Test
    void shouldBuildUtilisateurFromUserMongoWhenDataCommonAndDataPjProfilNotGiven() throws ParseException {

        UserMongo userMongo = TestUtils.buildUserMongo(
                1,
                TypeEtatCompte.ACTIVE,
                true,
                false,
                false,
                false,
                false,
                false
        );
        Utilisateur utilisateur = userMapper.buildUtilisateurFromUserMongo(userMongo);

        assertUtilisateurFromUserMongo(
                utilisateur,
                userMongo,
                true,
                true,
                false,
                false,
                false,
                false
        );
    }

    @Test
    void shouldBuildUtilisateurFromUserMongoWhenDataCommonGivenAndDataPjProfilNotGiven() throws ParseException {

        UserMongo userMongo = TestUtils.buildUserMongo(
                1,
                TypeEtatCompte.ACTIVE,
                true,
                true,
                false,
                false,
                false,
                false
        );
        Utilisateur utilisateur = userMapper.buildUtilisateurFromUserMongo(userMongo);

        assertUtilisateurFromUserMongo(
                utilisateur,
                userMongo,
                true,
                true,
                true,
                false,
                false,
                false
        );
    }

    @Test
    void shouldBuildUtilisateurFromUserMongoWhenDataCommonNotGivenAndDataPjProfilGiven() throws ParseException {

        UserMongo userMongo = TestUtils.buildUserMongo(
                1,
                TypeEtatCompte.ACTIVE,
                true,
                false,
                true,
                true,
                true,
                true
        );
        Utilisateur utilisateur = userMapper.buildUtilisateurFromUserMongo(userMongo);

        assertUtilisateurFromUserMongo(
                utilisateur,
                userMongo,
                true,
                true,
                false,
                true,
                true,
                true
        );
    }

    @Test
    void shouldBuildUtilisateurFromUserMongoWhenModerationNotGiven() throws ParseException {

        UserMongo userMongo = TestUtils.buildUserMongo(
                1,
                TypeEtatCompte.ACTIVE,
                true,
                true,
                true,
                false,
                false,
                true
        );
        Utilisateur utilisateur = userMapper.buildUtilisateurFromUserMongo(userMongo);

        assertUtilisateurFromUserMongo(
                utilisateur,
                userMongo,
                true,
                true,
                true,
                true,
                false,
                true
        );
    }

    @Test
    void shouldBuildUtilisateurFromUserMongoWhenNeitherPseudoNorAvatarInModeration() throws ParseException {

        UserMongo userMongo = TestUtils.buildUserMongo(
                1,
                TypeEtatCompte.ACTIVE,
                true,
                true,
                true,
                true,
                false,
                true
        );
        Utilisateur utilisateur = userMapper.buildUtilisateurFromUserMongo(userMongo);

        assertUtilisateurFromUserMongo(
                utilisateur,
                userMongo,
                true,
                true,
                true,
                true,
                false,
                true
        );
    }

    @Test
    void shouldBuildUtilisateurFromUserMongoWhenCGUAcceptationDatesNotGiven() throws ParseException {

        UserMongo userMongo = TestUtils.buildUserMongo(
                1,
                TypeEtatCompte.ACTIVE,
                true,
                true,
                true,
                true,
                false,
                false
        );
        Utilisateur utilisateur = userMapper.buildUtilisateurFromUserMongo(userMongo);

        assertUtilisateurFromUserMongo(
                utilisateur,
                userMongo,
                true,
                true,
                true,
                true,
                false,
                false
        );
    }

    private void assertUtilisateurFromUserMongo(Utilisateur utilisateur, UserMongo userMongo) throws ParseException {
        assertUtilisateurFromUserMongo(
                utilisateur,
                userMongo,
                true,
                true,
                true,
                true,
                true,
                true
        );
    }

    private void assertUtilisateurFromUserMongo(
            Utilisateur utilisateur,
            UserMongo userMongo,
            boolean isActive,
            boolean isData,
            boolean isDataCommon,
            boolean isDataPjPofil,
            boolean isModerationData,
            boolean isCGUAcceptationDates
    ) throws ParseException {
        // root
        Assertions.assertEquals(userMongo.getIdAuthPartner(), utilisateur.getId());
        Assertions.assertEquals(userMongo.getEmail(), utilisateur.getEmail());
        Assertions.assertEquals(isActive, utilisateur.getActive());
        Assertions.assertEquals(userMongo.getEtatCompte(), utilisateur.getEtatCompte());
        Assertions.assertEquals(userMongo.getDateCreationCompte(), utilisateur.getDateCreationCompte());
        Assertions.assertEquals(false, utilisateur.isProfilPjComplet());
        Assertions.assertIterableEquals(userMongo.getReseauSociauxAssocies(), utilisateur.getReseauSociauxAssocies());

        // registration
        Assertions.assertNotNull(utilisateur.getRegistration());
        Assertions.assertEquals(userMongo.getRegistration().getRegistrationToken(), utilisateur.getRegistration().getRegistrationToken());
        Assertions.assertEquals(userMongo.getRegistration().getRegistrationStatus(), utilisateur.getRegistration().getRegistrationStatus());
        Assertions.assertEquals(userMongo.getLastLoginCompte(), utilisateur.getLastLoginCompte());

        // data commun
        if (isData) {

            if (isDataCommon) {
//        Assertions.assertEquals(true, utilisateur.isProfilPjExiste()); // TODO DDC true car notion de profil supprimé (vu avec BRI)
                Assertions.assertNull(utilisateur.getListeProfilsCompte()); // TODO DDC - voir avec François si on dégage cette liste ou pas
                Assertions.assertEquals(userMongo.getData().getCommun().getAppareilOrigine(), utilisateur.getAppareilOrigine());
                Assertions.assertEquals(userMongo.getData().getCommun().getCommentaireDesactivation(), utilisateur.getCommentaireDesactivation());
                Assertions.assertEquals(userMongo.getData().getCommun().getPartageDonnees(), utilisateur.getPartageDonnees());
                Assertions.assertEquals(userMongo.getData().getCommun().getIdStatistique(), utilisateur.getIdStatistique());
                Assertions.assertEquals(userMongo.getData().getCommun().getIdUtilisateurExterne(), utilisateur.getIdUtilisateurExterne());
            }

            if (isDataPjPofil) {
                // data ProfilPJ
                Assertions.assertEquals(userMongo.getData().getPjProfil().getPseudo(), utilisateur.getPseudo());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getNom(), utilisateur.getNom());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getPrenom(), utilisateur.getPrenom());
                Assertions.assertNotNull(userMongo.getData().getPjProfil().getDateNaissance());
                Assertions.assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse(userMongo.getData().getPjProfil().getDateNaissance()), utilisateur.getDateNaissance());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getCivilite(), utilisateur.getCivilite().name());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getAdresse(), utilisateur.getAdresse());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getIdLocalite(), utilisateur.getIdLocalite());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getCheminAvatar(), utilisateur.getCheminAvatar());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getNumeroMobile(), utilisateur.getNumeroMobile());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getInscritNewsletter(), utilisateur.getInscritNewsletter());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getDateCreation(), utilisateur.getDateCreationProfil());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getOrigineCreation(), utilisateur.getOrigineCreation());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getVilleForcee(), utilisateur.getVilleForcee());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getOrigineCreationPseudo(), utilisateur.getOrigineCreationPseudo());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getSystemeOrigine(), utilisateur.getSystemeOrigine());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getCodeOrigineSollicitation(), utilisateur.getCodeOrigineSollicitation());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getNumeroMobileVerifie(), utilisateur.getNumeroMobileVerifie());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getParcoursOrigineCreation(), utilisateur.getParcoursOrigineCreation());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getLastActiviteCompte(), utilisateur.getLastActiviteCompte());
                Assertions.assertEquals(userMongo.getData().getPjProfil().getLastActiviteCompte(), utilisateur.getLastActiviteCompte());

                // moderation
                if (isModerationData) {
                    Assertions.assertEquals(true, utilisateur.getEnModeration());
                    Assertions.assertNotNull(utilisateur.getModeration());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getDateMiseModeration(), utilisateur.getModeration().getDateMiseModeration());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getDateDerniereModeration(), utilisateur.getModeration().getDateDerniereModeration());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getModerateur(), utilisateur.getModeration().getModerateur());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getPseudoEnModeration(), utilisateur.getModeration().getPseudoEnModeration());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getPseudoRaisonRefus(), utilisateur.getModeration().getPseudoRaisonRefus());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getPseudoPrecedent(), utilisateur.getModeration().getPseudoPrecedent());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getPseudoAModerer(), utilisateur.getModeration().getPseudoAModerer());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getAvatarEnModeration(), utilisateur.getModeration().getAvatarEnModeration());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getAvatarRaisonRefus(), utilisateur.getModeration().getAvatarRaisonRefus());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getCheminAvatarPrecedent(), utilisateur.getModeration().getCheminAvatarPrecedent());
                    Assertions.assertEquals(userMongo.getData().getPjProfil().getModeration().getCheminAvatarAModerer(), utilisateur.getModeration().getCheminAvatarAModerer());
                } else {
                    Assertions.assertEquals(false, utilisateur.getEnModeration());
                }
            }

            // liste CGUs
            if (isDataCommon) {
                Assertions.assertNotNull(utilisateur.getListeCgus());
                if (isCGUAcceptationDates) {
                    if (isDataPjPofil) {
                        Assertions.assertEquals(2, utilisateur.getListeCgus().size());
                        Assertions.assertNotNull(utilisateur.getListeCgus().get(0));
                        Assertions.assertEquals(TypeEntiteCGU.GLOBALES, utilisateur.getListeCgus().get(0).getEntiteCgu());
                        Assertions.assertEquals(userMongo.getData().getCommun().getDateAcceptationCgus(), utilisateur.getListeCgus().get(0).getDateCgu());
                        Assertions.assertNotNull(utilisateur.getListeCgus().get(1));
                        Assertions.assertEquals(TypeEntiteCGU.PJ, utilisateur.getListeCgus().get(1).getEntiteCgu());
                        Assertions.assertEquals(userMongo.getData().getPjProfil().getDateAcceptationCgus(), utilisateur.getListeCgus().get(1).getDateCgu());
                    } else {
                        Assertions.assertEquals(1, utilisateur.getListeCgus().size());
                        Assertions.assertNotNull(utilisateur.getListeCgus().get(0));
                        Assertions.assertEquals(TypeEntiteCGU.GLOBALES, utilisateur.getListeCgus().get(0).getEntiteCgu());
                        Assertions.assertEquals(userMongo.getData().getCommun().getDateAcceptationCgus(), utilisateur.getListeCgus().get(0).getDateCgu());
                    }
                } else {
                    Assertions.assertEquals(0, utilisateur.getListeCgus().size());
                }
            } else {
                if (isDataPjPofil) {
                    if (isCGUAcceptationDates) {
                        Assertions.assertNotNull(utilisateur.getListeCgus());
                        Assertions.assertEquals(1, utilisateur.getListeCgus().size());
                        Assertions.assertNotNull(utilisateur.getListeCgus().get(0));
                        Assertions.assertEquals(TypeEntiteCGU.PJ, utilisateur.getListeCgus().get(0).getEntiteCgu());
                        Assertions.assertEquals(userMongo.getData().getPjProfil().getDateAcceptationCgus(), utilisateur.getListeCgus().get(0).getDateCgu());
                    } else {
                        Assertions.assertEquals(0, utilisateur.getListeCgus().size());
                    }
                } else {
                    Assertions.assertNull(utilisateur.getListeCgus());
                }
            }

        }

        if (!isData) {

            Assertions.assertNull(utilisateur.getListeProfilsCompte()); // TODO DDC - voir avec François si on dégage cette liste ou pas
            Assertions.assertNull(utilisateur.getAppareilOrigine());
            Assertions.assertNull(utilisateur.getCommentaireDesactivation());
            Assertions.assertNull(utilisateur.getPartageDonnees());
            Assertions.assertNull(utilisateur.getIdStatistique());
            Assertions.assertNull(utilisateur.getIdUtilisateurExterne());

            // data ProfilPJ
            Assertions.assertNull(utilisateur.getPseudo());
            Assertions.assertNull(utilisateur.getNom());
            Assertions.assertNull(utilisateur.getPrenom());
            Assertions.assertNull(utilisateur.getDateNaissance());
            Assertions.assertNull(utilisateur.getCivilite());
            Assertions.assertNull(utilisateur.getAdresse());
            Assertions.assertNull(utilisateur.getIdLocalite());
            Assertions.assertNull(utilisateur.getCheminAvatar());
            Assertions.assertNull(utilisateur.getNumeroMobile());
            Assertions.assertNull(utilisateur.getInscritNewsletter());
            Assertions.assertNull(utilisateur.getDateCreationProfil());
            Assertions.assertNull(utilisateur.getOrigineCreation());
            Assertions.assertNull(utilisateur.getVilleForcee());
            Assertions.assertNull(utilisateur.getOrigineCreationPseudo());
            Assertions.assertNull(utilisateur.getSystemeOrigine());
            Assertions.assertNull(utilisateur.getCodeOrigineSollicitation());
            Assertions.assertNull(utilisateur.getNumeroMobileVerifie());
            Assertions.assertNull(utilisateur.getParcoursOrigineCreation());
            Assertions.assertNull(utilisateur.getLastActiviteCompte());
            Assertions.assertNull(utilisateur.getLastActiviteCompte());

            Assertions.assertNull(utilisateur.getListeCgus());
            Assertions.assertNull(utilisateur.getModeration());
            Assertions.assertNull(utilisateur.getEnModeration());
        }
    }

}
