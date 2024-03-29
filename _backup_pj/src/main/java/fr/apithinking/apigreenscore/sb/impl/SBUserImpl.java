package fr.apithinking.apigreenscore.sb.impl;

import fr.apithinking.apigreenscore.exception.NotFoundUserException;
import fr.apithinking.apigreenscore.mapper.UserMapper;
import fr.apithinking.apigreenscore.modele.Utilisateur;
import fr.apithinking.apigreenscore.provider.mongo.UserRepository;
import fr.apithinking.apigreenscore.sb.SBUser;
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
