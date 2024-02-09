package fr.apithinking.apigreenscore.provider.mongo;

import fr.apithinking.apigreenscore.provider.mongo.modele.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserMongo, String> {

    /**
     * Get user by id
     * @param idUtilisateur
     * @return
     */
    Optional<UserMongo> findByIdAuthPartner(String idUtilisateur);

    /**
     * Get user by external identifier
     *
     * @param pExternalUserId
     * @return
     */
    // TODO DDC : remplacer chaine par constantes ci-dessous
//    UserMongo.FieldName.DATA
//    DataMongo.FieldName.COMMUN
//    CommunMongo.FieldName.ID_UTILISATEUR_EXTERNE
//    UserMongo.FieldName.DATE_CREATION_COMPTE
//    UserMongo.FieldName.RESEAUX_SOCIAUX_ASSOCIES
    @Query(value = "{ 'data.commun.id_utilisateur_externe' : ?0 }", fields = "{ 'date_creation_compte' : 0, 'reseaux_sociaux_associes' : 0 }")
    Optional<UserMongo> findByExternalUserId(String pExternalUserId);


    /**
     * Get user by email
     *
     * @param pEmail
     * @return
     */
    // TODO DDC : remplacer chaine par constantes ci-dessous
//    FieldName.EMAIL
//    UserMongo.FieldName.RESEAUX_SOCIAUX_ASSOCIES
    @Query(value = "{ 'email' : ?0 }", fields = "{ 'reseaux_sociaux_associes' : 0 }")
    Optional<UserMongo> findByEmail(String pEmail);

}
