package fr.apithinking.apigreenscore.mapper;

import fr.apithinking.apigreenscore.modele.Cgu;
import fr.apithinking.apigreenscore.modele.TypeEntiteCGU;
import fr.apithinking.apigreenscore.modele.TypeEtatCompte;
import fr.apithinking.apigreenscore.modele.Utilisateur;
import fr.apithinking.apigreenscore.provider.mongo.modele.UserMongo;
import fr.apithinking.apigreenscore.provider.mongo.modele.commun.CommunMongo;
import fr.apithinking.apigreenscore.provider.mongo.modele.pjprofil.ModerationMongo;
import fr.apithinking.apigreenscore.provider.mongo.modele.pjprofil.PjProfilMongo;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;

public abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Override
    public Utilisateur buildUtilisateurFromUserMongo(UserMongo source) {
        Utilisateur utilisateur = delegate.buildUtilisateurFromUserMongo(source);

        // active
        utilisateur.setActive(TypeEtatCompte.ACTIVE.equals(source.getEtatCompte())
                || TypeEtatCompte.EN_ATTENTE_ACTIVATION.equals(source.getEtatCompte()));

        // etatCompte
        // TODO DDC - voir si possible de gérer le booléen isExcludeVerified via le mapper
        // TODO DDC - => checker utilisation réelle du booléan + voir avec François
//        if (BooleanUtils.isTrue(isExcludeVerified)) {
//            if (source.getEtatCompte() == null || TypeEtatCompte.DESACTIVE.equals(source.getEtatCompte())) {
//                utilisateur.setEtatCompte(TypeEtatCompte.DESACTIVE);
//            } else {
//                utilisateur.setEtatCompte(TypeEtatCompte.EN_ATTENTE_ACTIVATION);
//            }
//        }
//        else { // TODO DDC partie déjà faite automatiquement par MapStruct via mapper ==> check OK
//            utilisateur.setEtatCompte(source.getEtatCompte());
//        }

        // profilPjComplet
        // TODO DDC - checker utilisation réelle du booléan car toujours à false + voir avec François
        utilisateur.setProfilPjComplet(false);

        if (source.getData() != null && source.getData().getCommun() != null) {
            final CommunMongo profilCommun = source.getData().getCommun();

            // TODO DDC : vu avec BRI => notion de profils jamais utilisés
            // ==> nettoyage de BD à prévoir
            // ==> à voir si besoin du profil PJ quand même (champ profilPjExiste) avec FDE
//            List<EntiteSolocal> profilsPartenaires = new ArrayList<>();
//            if (profilCommun.getChronoresto() != null && BooleanUtils.isTrue(profilCommun.getChronoresto().getProfilExiste())) {
//                profilsPartenaires.add(EntiteSolocal.CHRONO_RESTO);
//            }
//            if (profilCommun.getClicrdv() != null && BooleanUtils.isTrue(profilCommun.getClicrdv().getProfilExiste())) {
//                profilsPartenaires.add(EntiteSolocal.CLIC_RDV);
//            }
//            if (profilCommun.getHamak() != null && BooleanUtils.isTrue(profilCommun.getHamak().getProfilExiste())) {
//                profilsPartenaires.add(EntiteSolocal.HAMAK);
//            }
//            if (profilCommun.getPagesjaunes() != null && profilCommun.getPagesjaunes().getProfilExiste() != null) {
//                profilsPartenaires.add(EntiteSolocal.PAGES_JAUNES);
//            }
//            utilisateur.setListeProfilPartenaires(profilsPartenaires);

            // Initialiser la liste des CGU, même si je n'en ai aucune d'acceptée.
            if (utilisateur.getListeCgus() == null) {
                utilisateur.setListeCgus(new ArrayList<>());
            }
            if (profilCommun.getDateAcceptationCgus() != null) {
                utilisateur.getListeCgus().add(new Cgu(TypeEntiteCGU.GLOBALES, profilCommun.getDateAcceptationCgus()));
            }
        }

        if (source.getData() != null && source.getData().getPjProfil() != null) {

            // Initialiser la liste des CGU, même si je n'en ai aucune d'acceptée.
            if (utilisateur.getListeCgus() == null) {
                utilisateur.setListeCgus(new ArrayList<>());
            }

            final PjProfilMongo profilPj = source.getData().getPjProfil();
            if (profilPj.getDateAcceptationCgus() != null) {
                utilisateur.getListeCgus().add(new Cgu(TypeEntiteCGU.PJ, profilPj.getDateAcceptationCgus()));
            }

            ModerationMongo moderationMongo = source.getData().getPjProfil().getModeration();
            if (moderationMongo == null) {
                utilisateur.setEnModeration(false);
            } else {
                utilisateur.setEnModeration(BooleanUtils.isTrue(moderationMongo.getPseudoEnModeration()) || BooleanUtils.isTrue(moderationMongo.getAvatarEnModeration()));
            }
        }

        return utilisateur;
    }
}
