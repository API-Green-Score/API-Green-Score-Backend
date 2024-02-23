package fr.apithinking.apigreenscore.sb;

import fr.apithinking.apigreenscore.TestUtils;
import fr.apithinking.apigreenscore.exception.NotFoundUserException;
import fr.apithinking.apigreenscore.mapper.UserMapper;
import fr.apithinking.apigreenscore.modele.Utilisateur;
import fr.apithinking.apigreenscore.provider.mongo.UserRepository;
import fr.apithinking.apigreenscore.provider.mongo.modele.UserMongo;
import fr.apithinking.apigreenscore.sb.impl.SBUserImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("ut")
class SBUserTest {

    private SBUser sbUser;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UserMapper userMapperMock;

    @BeforeEach
    public void init() {
        sbUser = new SBUserImpl(userRepositoryMock, userMapperMock);
    }

    @AfterEach
    public void reset() {
        Mockito.reset(userRepositoryMock, userMapperMock);
    }

    @Test
    void shouldGetUtilisateurFromIdAuthPartner() {
        String idAuthPartnerParam = "ID_AUTH_PARTNER";
        UserMongo userMongoMock = TestUtils.buildUserMongo();
        Utilisateur utilisateurMock = Utilisateur.builder()
                .id("ID")
                .adresse("ADRESSE")
                .email("EMAIL")
                .nom("NOM")
                .prenom("PRENOM")
                .build();

        ArgumentCaptor<UserMongo> userMongoCapture = ArgumentCaptor.forClass(UserMongo.class);

        Mockito.doReturn(Optional.of(userMongoMock)).when(userRepositoryMock).findByIdAuthPartner(idAuthPartnerParam);
        Mockito.doReturn(utilisateurMock).when(userMapperMock).buildUtilisateurFromUserMongo(userMongoCapture.capture());

        Utilisateur utilisateur = sbUser.getUser(idAuthPartnerParam);

        Mockito.verify(userRepositoryMock).findByIdAuthPartner(Mockito.anyString());
        Mockito.verify(userMapperMock).buildUtilisateurFromUserMongo(Mockito.any(UserMongo.class));
        Mockito.verifyNoMoreInteractions(userRepositoryMock, userMapperMock);

        Assertions.assertNotNull(userMongoCapture.getValue());
        Assertions.assertEquals(userMongoMock, userMongoCapture.getValue());
        Assertions.assertNotNull(utilisateur);
        Assertions.assertEquals(utilisateurMock, utilisateur);

    }

    @Test
    void shouldGetNotFoundExceptionFromInexistantIdAuthPartner() {
        String idAuthPartnerParam = "ID_AUTH_PARTNER";

        Mockito.doReturn(Optional.empty()).when(userRepositoryMock).findByIdAuthPartner(idAuthPartnerParam);

        try {
            sbUser.getUser(idAuthPartnerParam);
            Assertions.fail("Should throw NotFoundUserException");
        } catch (NotFoundUserException e) {
            Assertions.assertNotNull(e);
            Assertions.assertEquals("L'utilisateur avec l'id '" + idAuthPartnerParam + "' n'a pas été trouvé en base.", e.getMessage());
        }

        Mockito.verify(userRepositoryMock).findByIdAuthPartner(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(userRepositoryMock, userMapperMock);

    }

    @Test
    void shouldGetUtilisateurFromExternalUserId() {
        String idUserExt = "ID_UTILISATEUR_EXTERNE";
        UserMongo userMongoMock = TestUtils.buildUserMongo();
        Utilisateur utilisateurMock = Utilisateur.builder()
                .id("ID")
                .adresse("ADRESSE")
                .email("EMAIL")
                .nom("NOM")
                .prenom("PRENOM")
                .build();

        ArgumentCaptor<UserMongo> userMongoCapture = ArgumentCaptor.forClass(UserMongo.class);

        Mockito.doReturn(Optional.of(userMongoMock)).when(userRepositoryMock).findByExternalUserId(idUserExt);
        Mockito.doReturn(utilisateurMock).when(userMapperMock).buildUtilisateurFromUserMongo(userMongoCapture.capture());

        Utilisateur utilisateur = sbUser.getUserByExternalId(idUserExt);

        Mockito.verify(userRepositoryMock).findByExternalUserId(Mockito.anyString());
        Mockito.verify(userMapperMock).buildUtilisateurFromUserMongo(Mockito.any(UserMongo.class));
        Mockito.verifyNoMoreInteractions(userRepositoryMock, userMapperMock);

        Assertions.assertNotNull(userMongoCapture.getValue());
        Assertions.assertEquals(userMongoMock, userMongoCapture.getValue());
        Assertions.assertNotNull(utilisateur);
        Assertions.assertEquals(utilisateurMock, utilisateur);

    }

    @Test
    void shouldGetNotFoundExceptionFromInexistantExternalUserId() {
        String idUserExt = "ID_UTILISATEUR_EXTERNE";

        Mockito.doReturn(Optional.empty()).when(userRepositoryMock).findByExternalUserId(idUserExt);

        try {
            sbUser.getUserByExternalId(idUserExt);
            Assertions.fail("Should throw NotFoundUserException");
        } catch (NotFoundUserException e) {
            Assertions.assertNotNull(e);
            Assertions.assertEquals("L'utilisateur avec l'id externe '" + idUserExt + "' n'a pas été trouvé en base.", e.getMessage());
        }

        Mockito.verify(userRepositoryMock).findByExternalUserId(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(userRepositoryMock, userMapperMock);

    }

    @Test
    void shouldGetUtilisateurFromEmail() {
        String email = "EMAIL";
        UserMongo userMongoMock = TestUtils.buildUserMongo();
        Utilisateur utilisateurMock = Utilisateur.builder()
                .id("ID")
                .adresse("ADRESSE")
                .email("EMAIL")
                .nom("NOM")
                .prenom("PRENOM")
                .build();

        ArgumentCaptor<UserMongo> userMongoCapture = ArgumentCaptor.forClass(UserMongo.class);

        Mockito.doReturn(Optional.of(userMongoMock)).when(userRepositoryMock).findByEmail(email);
        Mockito.doReturn(utilisateurMock).when(userMapperMock).buildUtilisateurFromUserMongo(userMongoCapture.capture());

        Utilisateur utilisateur = sbUser.getUserByEmail(email);

        Mockito.verify(userRepositoryMock).findByEmail(Mockito.anyString());
        Mockito.verify(userMapperMock).buildUtilisateurFromUserMongo(Mockito.any(UserMongo.class));
        Mockito.verifyNoMoreInteractions(userRepositoryMock, userMapperMock);

        Assertions.assertNotNull(userMongoCapture.getValue());
        Assertions.assertEquals(userMongoMock, userMongoCapture.getValue());
        Assertions.assertNotNull(utilisateur);
        Assertions.assertEquals(utilisateurMock, utilisateur);

    }

    @Test
    void shouldGetNotFoundExceptionFromInexistantEmail() {
        String email = "EMAIL";

        Mockito.doReturn(Optional.empty()).when(userRepositoryMock).findByEmail(email);

        try {
            sbUser.getUserByEmail(email);
            Assertions.fail("Should throw NotFoundUserException");
        } catch (NotFoundUserException e) {
            Assertions.assertNotNull(e);
            Assertions.assertEquals("L'utilisateur avec l'email '" + email + "' n'a pas été trouvé en base.", e.getMessage());
        }

        Mockito.verify(userRepositoryMock).findByEmail(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(userRepositoryMock, userMapperMock);

    }


}
