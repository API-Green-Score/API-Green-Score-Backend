package fr.pagesjaunes.socletechnique.lang.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by nbulteau on 01/06/2017.
 * <p>
 * LinkedHashMap avec une capacité maximale, et un mode de fonctionnement MRU.
 */
public class CIMRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    /**
     *
     */
    private static final long serialVersionUID = 4125662497608689481L;

    private final int maxEntries;

    public CIMRULinkedHashMap() {
        this(100);
    }

    /**
     * Construteur
     * <p>
     * accessOrder est a false dans le constructeur de la classe mere pour implémenter le MRU
     *
     * @param maxEntries fixe la taille maximale du nombre d'entrées
     */
    public CIMRULinkedHashMap(final int maxEntries) {
        super(maxEntries, 1.0F);
        this.maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(final Map.Entry eldest) {
        return size() > maxEntries;
    }
}
