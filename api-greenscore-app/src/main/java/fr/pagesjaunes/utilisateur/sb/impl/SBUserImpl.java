package fr.pagesjaunes.utilisateur.sb.impl;

import fr.pagesjaunes.utilisateur.exception.NotFoundUserException;
import fr.pagesjaunes.utilisateur.mapper.UserMapper;
import fr.pagesjaunes.utilisateur.modele.Utilisateur;
import fr.pagesjaunes.utilisateur.provider.mongo.UserRepository;
import fr.pagesjaunes.utilisateur.sb.SBUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SBUserImpl implements SBUser {

    private UserRepository userRepository;

    private UserMapper userMapper;

    public SBUserImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Utilisateur getUser(final String utilisateurId) {
        LOGGER.debug("-->getUser(\"{}\")", utilisateurId);
        return userMapper.buildUtilisateurFromUserMongo(
                userRepository.findByIdAuthPartner(utilisateurId)
                .orElseThrow(() -> new NotFoundUserException(utilisateurId, "id"))
        );
    }

    @Override
    public Utilisateur getUserByExternalId(final String pExternalUserId) {
        LOGGER.debug("-->getUserByExternalId(\"{}\")", pExternalUserId);
        return userMapper.buildUtilisateurFromUserMongo(
                userRepository.findByExternalUserId(pExternalUserId)
                .orElseThrow(() -> new NotFoundUserException(pExternalUserId, "id externe"))
        );
    }

    @Override
    public Utilisateur getUserByEmail(final String pEmail) {
        LOGGER.debug("-->getUserByEmail(\"{}\")", pEmail);
        return userMapper.buildUtilisateurFromUserMongo(
                userRepository.findByEmail(pEmail)
                .orElseThrow(() -> new NotFoundUserException(pEmail, "email"))
        );
    }

}
