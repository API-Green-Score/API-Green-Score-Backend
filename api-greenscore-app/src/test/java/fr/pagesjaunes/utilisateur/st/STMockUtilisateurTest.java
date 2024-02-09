package fr.pagesjaunes.utilisateur.st;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pagesjaunes.utilisateur.modele.Utilisateur;
import fr.pagesjaunes.utilisateur.st.impl.STMockUtilisateurImpl;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("ut")
class STMockUtilisateurTest {

    private STMockUtilisateur stMockUtilisateur;

    @Mock
    private ObjectMapper objectMapperMock;

    @BeforeEach
    public void init() {
        stMockUtilisateur = new STMockUtilisateurImpl(objectMapperMock);
    }

    @AfterEach
    public void reset() {
        Mockito.reset(objectMapperMock);
    }

    @Test
    void shouldGetUtilisateurMockFromIdAuthPartnerWhenProfilGiven() throws IOException {
        String idAuthPartnerParam = "ID_AUTH_PARTNER+5";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(objectMapperMock).readValue(Mockito.any(InputStream.class), Mockito.eq(Utilisateur.class));

        Utilisateur utilisateur = stMockUtilisateur.recupererUtilisateurMock(idAuthPartnerParam);

        Mockito.verify(objectMapperMock).readValue(Mockito.any(InputStream.class), Mockito.eq(Utilisateur.class));
        Mockito.verifyNoMoreInteractions(objectMapperMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
        Assertions.assertEquals(idAuthPartnerParam, utilisateur.getId());
        Assertions.assertEquals(idAuthPartnerParam + "_ext", utilisateur.getIdUtilisateurExterne());
    }

    @Test
    void shouldGetUtilisateurMockFromIdAuthPartnerWhenNoProfilGiven() throws IOException {
        String idAuthPartnerParam = "ID_AUTH_PARTNER";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(objectMapperMock).readValue(Mockito.any(InputStream.class), Mockito.eq(Utilisateur.class));

        Utilisateur utilisateur = stMockUtilisateur.recupererUtilisateurMock(idAuthPartnerParam);

        Mockito.verify(objectMapperMock).readValue(Mockito.any(InputStream.class), Mockito.eq(Utilisateur.class));
        Mockito.verifyNoMoreInteractions(objectMapperMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
        Assertions.assertEquals(idAuthPartnerParam, utilisateur.getId());
        Assertions.assertEquals(idAuthPartnerParam + "_ext", utilisateur.getIdUtilisateurExterne());
    }

    @Test
    void shouldGetUtilisateurMockFromIdAuthPartnerWhenProfil8Given() throws IOException {
        String idAuthPartnerParam = "ID_AUTH_PARTNER+8";

        Utilisateur utilisateurMock = Utilisateur.builder().email("MY_EMAIL").build();
        Mockito.doReturn(utilisateurMock).when(objectMapperMock).readValue(Mockito.any(InputStream.class), Mockito.eq(Utilisateur.class));

        Utilisateur utilisateur = stMockUtilisateur.recupererUtilisateurMock(idAuthPartnerParam);

        Mockito.verify(objectMapperMock).readValue(Mockito.any(InputStream.class), Mockito.eq(Utilisateur.class));
        Mockito.verifyNoMoreInteractions(objectMapperMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
        Assertions.assertEquals(idAuthPartnerParam, utilisateur.getId());
        Assertions.assertEquals(idAuthPartnerParam + "_ext", utilisateur.getIdUtilisateurExterne());
        Assertions.assertEquals("ID_AUTH_PARTNER-MY_EMAIL", utilisateur.getEmail());
    }

    @Test
    void shouldGetUtilisateurMockFromIdAuthPartnerWhenUnknownProfilGiven() throws IOException {
        String idAuthPartnerParam = "ID_AUTH_PARTNER+123";

        Utilisateur utilisateurMock = Utilisateur.builder().build();
        Mockito.doReturn(utilisateurMock).when(objectMapperMock).readValue(Mockito.any(InputStream.class), Mockito.eq(Utilisateur.class));

        Utilisateur utilisateur = stMockUtilisateur.recupererUtilisateurMock(idAuthPartnerParam);

        Mockito.verify(objectMapperMock).readValue(Mockito.any(InputStream.class), Mockito.eq(Utilisateur.class));
        Mockito.verifyNoMoreInteractions(objectMapperMock);

        Assertions.assertEquals(utilisateurMock, utilisateur);
        Assertions.assertEquals(idAuthPartnerParam, utilisateur.getId());
        Assertions.assertEquals(idAuthPartnerParam + "_ext", utilisateur.getIdUtilisateurExterne());
    }

    @Test
    void shouldRaiseExceptionWhenIOExceptionOnObjectMapperCall() throws IOException {
        String idAuthPartnerParam = "ID_AUTH_PARTNER";

        String errMsg = "My error message";
        Mockito.doThrow(new IOException(errMsg)).when(objectMapperMock).readValue(Mockito.any(InputStream.class), Mockito.eq(Utilisateur.class));

        try {
            stMockUtilisateur.recupererUtilisateurMock(idAuthPartnerParam);
            Assertions.fail("Should raise an UncheckedIOException");
        } catch (UncheckedIOException ex) {
            Assertions.assertNotNull(ex.getCause());
            Assertions.assertEquals(IOException.class, ex.getCause().getClass());
            Assertions.assertEquals(errMsg, ex.getCause().getMessage());
        }

        Mockito.verify(objectMapperMock).readValue(Mockito.any(InputStream.class), Mockito.eq(Utilisateur.class));
        Mockito.verifyNoMoreInteractions(objectMapperMock);


    }

}
