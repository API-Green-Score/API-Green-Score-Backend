package fr.apithinking.apigreenscore.sm.impl;

import fr.apithinking.apigreenscore.modele.Utilisateur;
import fr.apithinking.apigreenscore.sb.SBUser;
import fr.apithinking.apigreenscore.sm.SMUtilisateur;
import org.springframework.stereotype.Component;

@Component
public class SMUtilisateurImpl implements SMUtilisateur {

    private SBUser sbUserMongo;

    public SMUtilisateurImpl(SBUser sbUserMongo) {
        this.sbUserMongo = sbUserMongo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Utilisateur getUser(final String pIdUtilisateur) {
        return sbUserMongo.getUser(pIdUtilisateur);
    }

    @Override
    public Utilisateur getUserByExternalId(final String pExternalUserId) { return sbUserMongo.getUserByExternalId(pExternalUserId); }

    /**
     * {@inheritDoc}
     */
    @Override
    public Utilisateur getUserByEmail(final String pEmail) { return sbUserMongo.getUserByEmail(pEmail); }

}
