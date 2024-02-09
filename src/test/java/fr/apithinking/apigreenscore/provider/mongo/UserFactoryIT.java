package fr.apithinking.apigreenscore.provider.mongo;

import fr.apithinking.apigreenscore.ApiGreenscoreApplication;
import fr.apithinking.apigreenscore.TestUtils;
import fr.apithinking.apigreenscore.provider.mongo.modele.UserMongo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ApiGreenscoreApplication.class)
@ActiveProfiles("it")
public class UserFactoryIT {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void initDatabase() { insertUsers(); }

    @AfterEach
    public void cleanDatabase() {
        userRepository.deleteAll();
    }

    @Test
    void shouldGetUserMongoFromIdAuthPartner() {

        UserMongo user = userRepository.findByIdAuthPartner("id_utilisateur3").orElse(null);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("id_utilisateur3", user.getIdAuthPartner());
        Assertions.assertEquals("EMAIL3", user.getEmail());
        Assertions.assertNotNull(user.getDateCreationCompte());
        Assertions.assertNotNull(user.getReseauSociauxAssocies());
        Assertions.assertEquals(2, user.getReseauSociauxAssocies().size());
    }

    @Test
    void shouldGetUserMongoFromExternalUserId() {

        UserMongo user = userRepository.findByExternalUserId("ID_UTILISATEUR_EXTERNE3").orElse(null);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("id_utilisateur3", user.getIdAuthPartner());
        Assertions.assertEquals("ID_UTILISATEUR_EXTERNE3", user.getData().getCommun().getIdUtilisateurExterne());
        Assertions.assertEquals("EMAIL3", user.getEmail());

        Assertions.assertNull(user.getDateCreationCompte());
        Assertions.assertNull(user.getReseauSociauxAssocies());
    }

    @Test
    void shouldGetUserMongoFromEmail() {

        UserMongo user = userRepository.findByEmail("EMAIL3").orElse(null);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("id_utilisateur3", user.getIdAuthPartner());
        Assertions.assertEquals("ID_UTILISATEUR_EXTERNE3", user.getData().getCommun().getIdUtilisateurExterne());
        Assertions.assertEquals("EMAIL3", user.getEmail());

        Assertions.assertNull(user.getReseauSociauxAssocies());
    }

    private void insertUsers() {
        insertUser(1);
        insertUser(5);
        insertUser(6);
        insertUser(2);
        insertUser(3);
        insertUser(7);
        insertUser(8);
        insertUser(4);
        insertUser(9);
    }

    private UserMongo insertUser(int index) {
        UserMongo user = TestUtils.buildUserMongo(index);
        userRepository.save(user);
        return user;
    }

}
