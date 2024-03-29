package fr.apithinking.apigreenscore.su;

import fr.apithinking.apigreenscore.modele.Utilisateur;
import fr.apithinking.apigreenscore.sm.SMUtilisateur;
import fr.apithinking.apigreenscore.su.impl.SUUtilisateurImpl;
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

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("ut")
class SUUtilisateurTest {

    private SUUtilisateur suUtilisateur;

    @Mock
    private SMUtilisateur smUtilisateurMock;

    @BeforeEach
    public void init() {
        suUtilisateur = new SUUtilisateurImpl(smUtilisateurMock);
    }

    @Test
    void shouldGetUtilisateurFromIdAuthPartnerWhenNominalUseCase() {
        String idAuthPartnerParam = "ID_AUTH_PARTNER";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(smUtilisateurMock).getUser(idAuthPartnerParam);

        Utilisateur utilisateur = suUtilisateur.getUser(idAuthPartnerParam);

        Mockito.verify(smUtilisateurMock).getUser(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(smUtilisateurMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

    @Test
    void shouldGetUtilisateurFromIdAuthPartnerWhenMockUseCase() {
        String idAuthPartnerParam = "ID_AUTH_PARTNER";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(smUtilisateurMock).getUser(idAuthPartnerParam);

        Utilisateur utilisateur = suUtilisateur.getUser(idAuthPartnerParam);

        Mockito.verifyNoMoreInteractions(smUtilisateurMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

    @Test
    void shouldGetUtilisateurFromExternalUserIdWhenNominalUseCase() {
        String idUserExt = "ID_UTILISATEUR_EXTERNE";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(smUtilisateurMock).getUserByExternalId(idUserExt);

        Utilisateur utilisateur = suUtilisateur.getUserByExternalId(idUserExt);

        Mockito.verify(smUtilisateurMock).getUserByExternalId(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(smUtilisateurMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

    @Test
    void shouldGetUtilisateurFromEmailWhenNominalUseCase() {
        String email = "EMAIL";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(smUtilisateurMock).getUserByEmail(email);

        Utilisateur utilisateur = suUtilisateur.getUserByEmail(email);

        Mockito.verify(smUtilisateurMock).getUserByEmail(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(smUtilisateurMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

    @Test
    void shouldRaiseExceptionWhenGettingUtilisateurFromEmailGivenBadFormattedEmail() {
        String email = "EMAIL";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(smUtilisateurMock).getUserByEmail(email);

        Utilisateur utilisateur = suUtilisateur.getUserByEmail(email);

        Mockito.verify(smUtilisateurMock).getUserByEmail(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(smUtilisateurMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

}
