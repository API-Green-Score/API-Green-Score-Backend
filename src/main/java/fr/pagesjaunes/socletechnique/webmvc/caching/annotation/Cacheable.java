package fr.pagesjaunes.socletechnique.webmvc.caching.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Source de la documentation : http://fr.wikipedia.org/wiki/Cache-Control
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

    /**
     *  false (public) : La réponse HTTP peut être mise en cache par n'importe quel cache.
     *   Un client ou un serveur proxy peuvent mettre en cache par exemple la réponse.
     *   Cela permet le partage de contenu à travers les utilisateurs qui utilisent le même serveur proxy.
     *  true (private) : Le message de réponse est destiné à un client unique et ne doit pas être mis en cache par un cache partagé.
     *   Un serveur proxy ne doit pas mettre en cache la réponse bien qu'un client puisse le faire.
     *   Cela permet au client de tenir à jour une version mise en mémoire cache pendant que tous les clients
     *   qui utilisent le même serveur proxy conservent les versions différentes mises en mémoire cache.
     *
     * @return boolean
     */
    boolean privateFlag() default false;

    /**
     * Aucun cache dans l'intégralité de chemin d'accès ne doit maintenir une copie mise en cache de la réponse.
     * Cela vous permet de spécifier que la requête suivante pour le même contenu sera obligatoirement
     * renvoyée par le serveur et non par un proxy ou le cache du navigateur.
     *
     * @return boolean
     */
    boolean noCache() default false;

    /**
     * Empêche le stockage non volatile (par exemple : sur disque) de la donnée.
     *
     * @return boolean
     */
    boolean noStore() default false;

    /**
     * La directive no-transform indique aux proxies et autres systèmes de cache qu'ils ne doivent
     * pas transformer le corps du message qu'ils recoivent.
     *
     * @return boolean
     */
    boolean noTransform() default false;

    /**
     * Force le cache à se reconnecter au serveur avec un If-Modified-Since et doit provoquer une erreur 504 si la page a disparu.
     * Si malgré les recommandations, le cache viole cette directive, il doit obligatoirement prévenir l'utilisateur
     * et obtenir son accord pour chaque accès non revalidé.
     *
     * @return boolean
     */
    boolean mustRevalidate() default false;

    /**
     * Mécanisme similaire à "must-revalidate", mais s'applique uniquement à des caches de proxy partagés
     * (autres que ceux utilisés par le logiciel client de l'utilisateur).
     * Cela permet de contrôler que l'utilisateur est bien authentifié sur le serveur.
     *
     * @return boolean
     */
    boolean proxyRevalidate() default false;

    /**
     * Cette directive permet à un serveur de fixer la durée maximale de rétention.
     * Lorsqu'elle est utilisée par un client, elle indique au(x) cache(s) la fraîcheur minimale souhaitée.
     * Les durées sont indiquées en secondes. (Cache-Control: max-age=600)
     *
     * Lorsque cette directive est présente dans la requête d'un client, le cache doit renvoyer un document
     * qui a été produit il y a au plus 10 minutes (en-tête Date).
     * Si le cache possède une réponse plus ancienne, il est censé contacter le serveur d'origine pour obtenir une version plus récente
     * (s'il ne le fait pas, le cache doit obligatoirement inclure un avertissement dans la réponse).
     *
     * Si cette directive apparaît dans la réponse d'un serveur, elle autorise le(s) cache(s) à servir cette même réponse pendant 10 minutes maximum.
     * Dans une réponse, la directive max-age outrepasse l'en-tête Expires.
     * Une valeur définie à 0 indique que la cible devrait être rechargé à chaque accès.
     *
     * Cette notion est proche mais non équivalente à la valeur no-cache qui elle oblige la création du fichier
     * à chaque accès sans laisser de place au choix du navigateur.
     *
     * @return durée de cache en secondes.
     */
    int maxAge() default -1;

    /**
     * s-maxage est identique à max-age, mais ne s'applique qu'aux caches publics : les proxies.
     *
     * @return durée de cache, en secondes.
     */
    int sMaxAge() default -1;

    /**
     * Pour déterminer le la période de grace dans varnish, en secondes.
     *
     * @return
     */
    int grace() default -1;
}
