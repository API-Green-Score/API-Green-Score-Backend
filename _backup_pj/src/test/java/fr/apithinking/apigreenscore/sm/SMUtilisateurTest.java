package fr.apithinking.apigreenscore.sm;

import fr.apithinking.apigreenscore.modele.Utilisateur;
import fr.apithinking.apigreenscore.sb.SBUser;
import fr.apithinking.apigreenscore.sm.impl.SMUtilisateurImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("ut")
class SMUtilisateurTest {

    private SMUtilisateur smUtilisateur;

    @Mock
    private SBUser sbUserMock;

    @BeforeEach
    public void init() {
        smUtilisateur = new SMUtilisateurImpl(sbUserMock);
    }

    @AfterEach
    public void reset() {
        Mockito.reset(sbUserMock);
    }

    @Test
    void shouldGetUtilisateurFromIdAuthPartner() {
        String idAuthPartnerParam = "ID_AUTH_PARTNER";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(sbUserMock).getUser(idAuthPartnerParam);

        Utilisateur utilisateur = smUtilisateur.getUser(idAuthPartnerParam);

        Mockito.verify(sbUserMock).getUser(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(sbUserMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

    @Test
    void shouldGetUtilisateurFromExternalUserId() {
        String idUserExt = "ID_UTILISATEUR_EXTERNE";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(sbUserMock).getUserByExternalId(idUserExt);

        Utilisateur utilisateur = smUtilisateur.getUserByExternalId(idUserExt);

        Mockito.verify(sbUserMock).getUserByExternalId(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(sbUserMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

    @Test
    void shouldGetUtilisateurFromEmail() {
        String email = "EMAIL";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(sbUserMock).getUserByEmail(email);

        Utilisateur utilisateur = smUtilisateur.getUserByEmail(email);

        Mockito.verify(sbUserMock).getUserByEmail(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(sbUserMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

}
