package fr.pagesjaunes.socletechnique.webmvc.cacheinvalidation.annotation;

import java.lang.annotation.*;

/**
 * Annotation à utiliser pour paramétrer la génération d'un entête 'xkey' en réponse.
 * La valeur de l'entête généré a le format 'dataType-dataId'.
 * L'annotation peut être posée au niveau des méthodes d'un SU.
 * -
 *
 * @author spiel
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(XKeys.class)
public @interface XKey {

    /**
     * Indique une expression SpEL à executer pour générer l'entête xkey. Equivalent à dataId mais permettant de générer une valeur dynamiquement.
     * Le contexte d'évaluation contient 1 objet :
     * - entity : l'objet (entité) retourné par la méthode annotée
     * <p>
     * Le résultat de l'expression doit être une liste de String.
     * <p>
     * Exemple :  @XKey(dataType = "list", dataIdExpression = "entity.![lieu]")
     */
    String dataIdExpression() default "";

    /**
     * Indique le type de données (préfixe) à utiliser dans la génération de l'entête xkey.
     */
    String dataType() default "";

    /**
     * Indique si le traitement de l'annotation (génération de l'entête xkey) est activé ou non.
     * Actif par défaut
     */
    boolean enabled() default true;
}
