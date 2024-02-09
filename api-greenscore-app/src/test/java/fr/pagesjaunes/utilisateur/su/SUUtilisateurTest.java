package fr.pagesjaunes.utilisateur.su;

import fr.pagesjaunes.socletechnique.featureflipping.STFeatureFlipping;
import fr.pagesjaunes.utilisateur.modele.Utilisateur;
import fr.pagesjaunes.utilisateur.sm.SMUtilisateur;
import fr.pagesjaunes.utilisateur.st.STMockUtilisateur;
import fr.pagesjaunes.utilisateur.su.impl.SUUtilisateurImpl;
import fr.pagesjaunes.utilisateur.utils.Constantes;
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

    @Mock
    private STFeatureFlipping stFeatureFlippingMock;

    @Mock
    private STMockUtilisateur stMockUtilisateurMock;

    @BeforeEach
    public void init() {
        suUtilisateur = new SUUtilisateurImpl(
                smUtilisateurMock,
                stFeatureFlippingMock,
                stMockUtilisateurMock
        );
    }

    @Test
    void shouldGetUtilisateurFromIdAuthPartnerWhenNominalUseCase() {
        String idAuthPartnerParam = "ID_AUTH_PARTNER";

        ArgumentCaptor<String> ffCapture = ArgumentCaptor.forClass(String.class);

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(false).when(stFeatureFlippingMock).isActive(ffCapture.capture());
        Mockito.doReturn(utilisateurMock).when(smUtilisateurMock).getUser(idAuthPartnerParam);

        Utilisateur utilisateur = suUtilisateur.getUser(idAuthPartnerParam);

        Mockito.verify(smUtilisateurMock).getUser(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(smUtilisateurMock, stFeatureFlippingMock, stMockUtilisateurMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
        Assertions.assertNotNull(ffCapture.getValue());
        Assertions.assertEquals(Constantes.FF_TECH_MOCK_USER, ffCapture.getValue());
    }

    @Test
    void shouldGetUtilisateurFromIdAuthPartnerWhenMockUseCase() {
        String idAuthPartnerParam = "ID_AUTH_PARTNER";

        ArgumentCaptor<String> ffCapture = ArgumentCaptor.forClass(String.class);

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(true).when(stFeatureFlippingMock).isActive(ffCapture.capture());
        Mockito.doReturn(utilisateurMock).when(stMockUtilisateurMock).recupererUtilisateurMock(idAuthPartnerParam);

        Utilisateur utilisateur = suUtilisateur.getUser(idAuthPartnerParam);

        Mockito.verify(stMockUtilisateurMock).recupererUtilisateurMock(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(smUtilisateurMock, stFeatureFlippingMock, stMockUtilisateurMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
        Assertions.assertNotNull(ffCapture.getValue());
        Assertions.assertEquals(Constantes.FF_TECH_MOCK_USER, ffCapture.getValue());
    }

    @Test
    void shouldGetUtilisateurFromExternalUserIdWhenNominalUseCase() {
        String idUserExt = "ID_UTILISATEUR_EXTERNE";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(smUtilisateurMock).getUserByExternalId(idUserExt);

        Utilisateur utilisateur = suUtilisateur.getUserByExternalId(idUserExt);

        Mockito.verify(smUtilisateurMock).getUserByExternalId(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(smUtilisateurMock, stFeatureFlippingMock, stMockUtilisateurMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

    @Test
    void shouldGetUtilisateurFromEmailWhenNominalUseCase() {
        String email = "EMAIL";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(smUtilisateurMock).getUserByEmail(email);

        Utilisateur utilisateur = suUtilisateur.getUserByEmail(email);

        Mockito.verify(smUtilisateurMock).getUserByEmail(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(smUtilisateurMock, stFeatureFlippingMock, stMockUtilisateurMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

    @Test
    void shouldRaiseExceptionWhenGettingUtilisateurFromEmailGivenBadFormattedEmail() {
        String email = "EMAIL";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(smUtilisateurMock).getUserByEmail(email);

        Utilisateur utilisateur = suUtilisateur.getUserByEmail(email);

        Mockito.verify(smUtilisateurMock).getUserByEmail(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(smUtilisateurMock, stFeatureFlippingMock, stMockUtilisateurMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
    }

}
