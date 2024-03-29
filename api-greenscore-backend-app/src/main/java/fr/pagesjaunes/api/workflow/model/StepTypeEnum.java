package fr.pagesjaunes.api.workflow.model;

public enum StepTypeEnum {

    /**
     * Depôt du message dans le gestionnaire.
     */
    INIT,

    /**
     * Depôt du message dans le gestionnaire.
     */
    PUBLISH,

    /**
     * Réception d'un message du gestionnaire.
     */
    SUBSCRIBE,

    /**
     * Nouveau message deposer pour la suite des opérations.
     */
    POST_MESSAGE_OUT,

    /**
     * Traitement du message terminé.
     */
    COMPLETION,

    /**
     * Une erreur est survenue.
     */
    ON_ERROR,
}
