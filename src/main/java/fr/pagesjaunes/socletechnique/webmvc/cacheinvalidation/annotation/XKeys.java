package fr.pagesjaunes.socletechnique.webmvc.cacheinvalidation.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XKeys {

    XKey[] value();

}
