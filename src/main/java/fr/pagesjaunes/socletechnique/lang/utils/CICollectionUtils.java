package fr.pagesjaunes.socletechnique.lang.utils;

import org.apache.commons.collections4.*;

import java.util.*;

/**
 * Classe utilitaire pour les manipulations de chaines de caractéres.
 *
 * @author mbrossais
 */
public final class CICollectionUtils {

    private CICollectionUtils() {
    }

    // ========================================================================
    // METHODES PUBLIQUES
    // ========================================================================

    /**
     * @param pCollection est la collection que l'on souhaite enrichir.
     * @param pElt        est l'element que l'on veut ajouter
     * @param condition   est la condition pour laquelle on veut ajouter l'element
     * @throws NullPointerException si le collection est null.
     */
    public static <T> void addWithCondition(final Collection<T> pCollection, final T pElt, final boolean condition) {

        if (condition) {
            pCollection.add(pElt);
        }
    }

    /**
     * Ajout une chaine de caractere a une collection si la chaine n'est pas vide.
     * <pre>
     * addIgnoreEmpty(liste, null)      --> non ajoute
     * addIgnoreEmpty(liste, "")        --> non ajoute
     * addIgnoreEmpty(liste, "bob")     --> ajoute
     * </pre>
     *
     * @param pCollection est la collection dans laquelle l'element est ajoute
     * @param pString     est la chaine de caractere ajouter
     * @throws NullPointerException si le collection est null.
     */
    public static void addIgnoreEmpty(final Collection<String> pCollection, final String pString) {

        addWithCondition(pCollection, pString, CIStringUtils.isNotEmpty(pString));
    }

    /**
     * Ajoute une liste d'objet dans une autre liste cible.
     *
     * @param pCollectionCible  {@see Collection} dans laquelle l'objet doit &ecirc;tre ajout&eacute;.
     * @param pCollectionSource {@see Collection} &agrave; ajouter dans la collection.
     * @throws NullPointerException si le collection cible est null.
     */
    public static <T> void addAllIgnoreNull(final Collection<T> pCollectionCible, final Collection<? extends T> pCollectionSource) {

        if (pCollectionSource != null) {
            pCollectionCible.addAll(pCollectionSource);
        }
    }

    /**
     * Ajoute une liste d'objet dans une autre liste cible en ignorant les entité vide de la collection source.
     *
     * @param pCollectionCible  {@see Collection} dans laquelle l'objet doit &ecirc;tre ajout&eacute;.
     * @param pCollectionSource {@see Collection} &agrave; ajouter dans la collection.
     */
    public static <T> void addAllIgnoreNullEntities(final Collection<T> pCollectionCible, final Collection<? extends T> pCollectionSource) {
        if (pCollectionCible != null && pCollectionSource != null) {
            pCollectionCible.addAll(
                    pCollectionSource
                            .stream()
                            .filter(Objects::nonNull)
                            .toList());
        }
    }

    /**
     * D&eacute;coupe une liste en sous-listes de taille d&eacute;finie.
     *
     * <pre>
     * splitList(["A", "B", "C", "D", "E"], 2) => [["A", "B"], ["C", "D"], ["E"]]
     * splitList(["A", "B", "C", "D", "E"], 0) => [["A", "B", "C", "D", "E"]]
     * splitList(["A", "B", "C", "D", "E"], -2) => [["A", "B", "C", "D", "E"]]
     * splitList([], 2) => [] splitList(null, 2) => []
     * </pre>
     *
     * @param list        la liste initiale.
     * @param subListSize la taille des sous-listes &agrave; cr&eacute;&eacute;es.
     * @return les sous-listes.
     */
    public static <T> List<List<T>> splitList(final List<T> list, final int subListSize) {
        List<List<T>> lstSubList = new ArrayList<>();
        // Si la liste initiale est null ou vide, retourne une liste vide.
        if (CollectionUtils.isNotEmpty(list)) {
            // Si la taille des sous-listes est invalide, retourne une liste contenant la liste initiale.
            if (subListSize <= 0) {
                lstSubList.add(list);
            } else {
                // Cas nominal.
                int fromIndex = 0;
                // Tant que l'index de début de la prochaine sous-liste est inférieur é la taille de la liste initiale.
                while (fromIndex < list.size()) {
                    // Détermine l'index de fin de la prochaine sous-liste.
                    int toIndex = Math.min(fromIndex + subListSize, list.size());
                    // Construit et ajoute la sous-liste au résultat.
                    lstSubList.add(new ArrayList<>(list.subList(fromIndex, toIndex)));
                    // L'index de début de la prochaine sous-liste correspond é l'index de fin de celle créée.
                    fromIndex = toIndex;
                }
            }
        }
        return lstSubList;
    }

    /**
     * Transforme un tableau (ou une ellipse d'&eacute;l&eacute;ments) en liste.
     *
     * @param <T>   le type des &eacute;l&eacute;ments.
     * @param array le tableau, ou l'ellipse d'&eacute;l&eacute;ments.
     * @return la liste; ou <code>null</code> si le tableau est <code>null</code>.
     */
    @SafeVarargs
    public static <T> List<T> toList(final T... array) {
        List<T> list = null;
        if (array != null) {
            list = Arrays.asList(array);
        }
        return list;
    }

    /**
     * Supprime les chaines vides et &lt;code&gt;null&lt;/code&gt; d'une liste. La liste initiale n'est pas modifi&eacute;e par
     * l'op&eacute;ration. L'ordre des &eacute;l&eacute;ments est conserv&eacute;.
     *
     * <pre>
     * removeBlankString(null) => throw NullPointerException
     * removeBlankString([]) => []
     * removeBlankString(["A", null, "", "  ", "B"]) => ["A", "B"]
     * </pre>
     *
     * @param inputList la liste initiale.
     * @return une nouvelle liste contenant les chaines non vides et non <code>null</code> de la liste initiale. Ne
     * retourne jamais <code>null</code>.
     * @throws NullPointerException si la liste initiale est vide.
     */
    public static List<String> removeBlankString(final List<String> inputList) {
        List<String> newList = new ArrayList<>();
        for (String string : inputList) {
            if (CIStringUtils.isNotBlank(string)) {
                newList.add(string);
            }
        }
        return newList;
    }

    /**
     * Transforme un tableau (ou une ellipse d'&eacute;l&eacute;ments) en set. L'ordre des &eacute;l&eacute;ments est pr&eacute;serv&eacute;.
     *
     * @param <T>   le type des &eacute;l&eacute;ments.
     * @param array le tableau, ou l'ellipse d'&eacute;l&eacute;ments.
     * @return le set; ou <code>null</code> si le tableau est <code>null</code>.
     */
    @SafeVarargs
    public static <T> Set<T> toSet(final T... array) {
        Set<T> set = null;
        if (array != null) {
            set = new LinkedHashSet<>(Arrays.asList(array));
        }
        return set;
    }

    /**
     * Verifie si les 2 listes sont de meme taille ou non (v&eacute;rification de la nullit&eacute; aussi).
     *
     * @param pList1 liste1
     * @param pList2 liste2
     * @return Vrai si liste de meme taille (null et null => TRUE), Faux sinon
     */
    public static boolean isSameSize(final List<?> pList1, final List<?> pList2) {

        if (CollectionUtils.isEmpty(pList1) && CollectionUtils.isEmpty(pList2)) {
            return true;
        }

        return isSameSizeAndNotEmpty(pList1, pList2);
    }

    /**
     * Verifie si les 2 listes sont de meme taille ou non (v&eacute;rification de la nullit&eacute; aussi).
     *
     * @param pList1 liste1
     * @param pList2 liste2
     * @return Vrai si liste de meme taille (null et null => FALSE), Faux sinon
     */
    public static boolean isSameSizeAndNotEmpty(final List<?> pList1, final List<?> pList2) {
        return CollectionUtils.isNotEmpty(pList1) && CollectionUtils.isNotEmpty(pList2) && pList1.size() == pList2.size();
    }

    /**
     * @see CollectionUtils#emptyCollection()
     */
    public static <T> Collection<T> emptyCollection() {
        return CollectionUtils.emptyCollection();
    }

    /**
     * @see CollectionUtils#emptyIfNull(Collection)
     */
    public static <T> Collection<T> emptyIfNull(final Collection<T> collection) {
        return CollectionUtils.emptyIfNull(collection);
    }

    /**
     * @see CollectionUtils#union(Iterable, Iterable)
     */
    public static <O> Collection<O> union(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        return CollectionUtils.union(a, b);
    }

    /**
     * @see CollectionUtils#intersection(Iterable, Iterable)
     */
    public static <O> Collection<O> intersection(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        return CollectionUtils.intersection(a, b);
    }

    /**
     * @see CollectionUtils#disjunction(Iterable, Iterable)
     */
    public static <O> Collection<O> disjunction(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        return CollectionUtils.disjunction(a, b);
    }

    /**
     * @see CollectionUtils#subtract(Iterable, Iterable)
     */
    public static <O> Collection<O> subtract(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        return CollectionUtils.subtract(a, b);
    }

    /**
     * @see CollectionUtils#subtract(Iterable, Iterable, Predicate)
     */
    public static <O> Collection<O> subtract(final Iterable<? extends O> a,
                                             final Iterable<? extends O> b,
                                             final Predicate<O> p) {
        return CollectionUtils.subtract(a, b, p);
    }

    /**
     * @see CollectionUtils#containsAll(Collection, Collection)
     */
    public static boolean containsAll(final Collection<?> coll1, final Collection<?> coll2) {
        return CollectionUtils.containsAll(coll1, coll2);
    }

    /**
     * @see CollectionUtils#containsAny(Collection, Collection)
     */
    public static boolean containsAny(final Collection<?> coll1, final Collection<?> coll2) {
        return CollectionUtils.containsAny(coll1, coll2);
    }

    /**
     * @see CollectionUtils#getCardinalityMap(Iterable)
     */
    public static <O> Map<O, Integer> getCardinalityMap(final Iterable<? extends O> coll) {
        return CollectionUtils.getCardinalityMap(coll);
    }

    /**
     * @see CollectionUtils#isSubCollection(Collection, Collection)
     */
    public static boolean isSubCollection(final Collection<?> a, final Collection<?> b) {
        return CollectionUtils.isSubCollection(a, b);
    }

    /**
     * @see CollectionUtils#isProperSubCollection(Collection, Collection)
     */
    public static boolean isProperSubCollection(final Collection<?> a, final Collection<?> b) {
        return CollectionUtils.isProperSubCollection(a, b);
    }

    /**
     * @see CollectionUtils#isEqualCollection(Collection, Collection)
     */
    public static boolean isEqualCollection(final Collection<?> a, final Collection<?> b) {
        return CollectionUtils.isEqualCollection(a, b);
    }

    /**
     * @see CollectionUtils#isEqualCollection(Collection, Collection, Equator)
     */
    public static <E> boolean isEqualCollection(final Collection<? extends E> a, final Collection<? extends E> b, final Equator<? super E> equator) {
        return CollectionUtils.isEqualCollection(a, b, equator);
    }

    /**
     * @see CollectionUtils#cardinality(Object, Iterable)
     */
    public static <O> int cardinality(final O obj, final Iterable<? super O> coll) {
        return CollectionUtils.cardinality(obj, coll);
    }

    /**
     * @see CollectionUtils#find(Iterable, Predicate)
     */
    public static <T> T find(final Iterable<T> collection, final Predicate<? super T> predicate) {
        return CollectionUtils.find(collection, predicate);
    }

    /**
     * @see CollectionUtils#forAllDo(Iterable, Closure)
     */
    public static <T, C extends Closure<? super T>> C forAllDo(final Iterable<T> collection, final C closure) {
        return CollectionUtils.forAllDo(collection, closure);
    }

    /**
     * @see CollectionUtils#forAllDo(Iterator, Closure)
     */
    public static <T, C extends Closure<? super T>> C forAllDo(final Iterator<T> iterator, final C closure) {
        return CollectionUtils.forAllDo(iterator, closure);
    }

    /**
     * @see CollectionUtils#forAllButLastDo(Iterable, Closure)
     */
    public static <T, C extends Closure<? super T>> T forAllButLastDo(final Iterable<T> collection,
                                                                      final C closure) {
        return CollectionUtils.forAllButLastDo(collection, closure);
    }

    /**
     * @see CollectionUtils#forAllButLastDo(Iterator, Closure)
     */
    public static <T, C extends Closure<? super T>> T forAllButLastDo(final Iterator<T> iterator, final C closure) {
        return CollectionUtils.forAllButLastDo(iterator, closure);
    }

    /**
     * @see CollectionUtils#filter(Iterable, Predicate)
     */
    public static <T> boolean filter(final Iterable<T> collection, final Predicate<? super T> predicate) {
        return CollectionUtils.filter(collection, predicate);
    }

    /**
     * @see CollectionUtils#filterInverse(Iterable, Predicate)
     */
    public static <T> boolean filterInverse(final Iterable<T> collection, final Predicate<? super T> predicate) {
        return CollectionUtils.filterInverse(collection, predicate);
    }

    /**
     * @see CollectionUtils#transform(Collection, Transformer)
     */
    public static <C> void transform(final Collection<C> collection,
                                     final Transformer<? super C, ? extends C> transformer) {
        CollectionUtils.transform(collection, transformer);
    }

    /**
     * @see CollectionUtils#countMatches(Iterable, Predicate)
     */
    public static <C> int countMatches(final Iterable<C> input, final Predicate<? super C> predicate) {
        return CollectionUtils.countMatches(input, predicate);
    }

    /**
     * @see CollectionUtils#exists(Iterable, Predicate)
     */
    public static <C> boolean exists(final Iterable<C> input, final Predicate<? super C> predicate) {
        return CollectionUtils.exists(input, predicate);
    }

    /**
     * @see CollectionUtils#matchesAll(Iterable, Predicate)
     */
    public static <C> boolean matchesAll(final Iterable<C> input, final Predicate<? super C> predicate) {
        return CollectionUtils.matchesAll(input, predicate);
    }

    /**
     * @see CollectionUtils#select(Iterable, Predicate)
     */
    public static <O> Collection<O> select(final Iterable<? extends O> inputCollection,
                                           final Predicate<? super O> predicate) {
        return CollectionUtils.select(inputCollection, predicate);
    }

    /**
     * @see CollectionUtils#select(Iterable, Predicate, Collection)
     */
    public static <O, R extends Collection<? super O>> R select(final Iterable<? extends O> inputCollection,
                                                                final Predicate<? super O> predicate, final R outputCollection) {
        return CollectionUtils.select(inputCollection, predicate, outputCollection);
    }

    /**
     * @see CollectionUtils#selectRejected(Iterable, Predicate)
     */
    public static <O> Collection<O> selectRejected(final Iterable<? extends O> inputCollection,
                                                   final Predicate<? super O> predicate) {
        return CollectionUtils.selectRejected(inputCollection, predicate);
    }

    /**
     * @see CollectionUtils#selectRejected(Iterable, Predicate, Collection)
     */
    public static <O, R extends Collection<? super O>> R selectRejected(final Iterable<? extends O> inputCollection,
                                                                        final Predicate<? super O> predicate, final R outputCollection) {
        return CollectionUtils.selectRejected(inputCollection, predicate, outputCollection);
    }

    /**
     * @see CollectionUtils#collect(Iterable, Transformer)
     */
    public static <I, O> Collection<O> collect(final Iterable<I> inputCollection,
                                               final Transformer<? super I, ? extends O> transformer) {
        return CollectionUtils.collect(inputCollection, transformer);
    }

    /**
     * @see CollectionUtils#collect(Iterator, Transformer)
     */
    public static <I, O> Collection<O> collect(final Iterator<I> inputIterator,
                                               final Transformer<? super I, ? extends O> transformer) {
        return CollectionUtils.collect(inputIterator, transformer);
    }

    /**
     * @see CollectionUtils#collect(Iterable, Transformer, Collection)
     */
    public static <I, O, R extends Collection<? super O>> R collect(final Iterable<? extends I> inputCollection,
                                                                    final Transformer<? super I, ? extends O> transformer, final R outputCollection) {
        return CollectionUtils.collect(inputCollection, transformer, outputCollection);
    }

    /**
     * @see CollectionUtils#collect(Iterator, Transformer, Collection)
     */
    public static <I, O, R extends Collection<? super O>> R collect(final Iterator<? extends I> inputIterator,
                                                                    final Transformer<? super I, ? extends O> transformer, final R outputCollection) {
        return CollectionUtils.collect(inputIterator, transformer, outputCollection);
    }

    /**
     * @see CollectionUtils#addIgnoreNull(Collection, Object)
     */
    public static <T> boolean addIgnoreNull(final Collection<T> collection, final T object) {
        return CollectionUtils.addIgnoreNull(collection, object);
    }

    /**
     * @see CollectionUtils#addAll(Collection, Iterable)
     */
    public static <C> boolean addAll(final Collection<C> collection, final Iterable<? extends C> iterable) {
        return CollectionUtils.addAll(collection, iterable);
    }

    /**
     * @see CollectionUtils#addAll(Collection, Iterator)
     */
    public static <C> boolean addAll(final Collection<C> collection, final Iterator<? extends C> iterator) {
        return CollectionUtils.addAll(collection, iterator);
    }

    /**
     * @see CollectionUtils#addAll(Collection, Enumeration)
     */
    public static <C> boolean addAll(final Collection<C> collection, final Enumeration<? extends C> enumeration) {
        return CollectionUtils.addAll(collection, enumeration);
    }

    /**
     * @see CollectionUtils#addAll(Collection, Object[])
     */
    public static <C> boolean addAll(final Collection<C> collection, final C[] elements) {
        return CollectionUtils.addAll(collection, elements);
    }

    /**
     * @see CollectionUtils#get(Iterator, int)
     */
    public static <T> T get(final Iterator<T> iterator, final int index) {
        return CollectionUtils.get(iterator, index);
    }

    /**
     * @see CollectionUtils#get(Iterable, int)
     */
    public static <T> T get(final Iterable<T> iterable, final int index) {
        return CollectionUtils.get(iterable, index);
    }

    /**
     * @see CollectionUtils#get(Iterable, int)
     */
    public static Object get(final Object object, final int index) {
        return CollectionUtils.get(object, index);
    }

    /**
     * @see CollectionUtils#get(Map, int)
     */
    public static <K, V> Map.Entry<K, V> get(final Map<K, V> map, final int index) {
        return CollectionUtils.get(map, index);
    }

    /**
     * @see CollectionUtils#size(Object)
     */
    public static int size(final Object object) {
        return CollectionUtils.size(object);
    }

    /**
     * @see CollectionUtils#sizeIsEmpty(Object)
     */
    public static boolean sizeIsEmpty(final Object object) {
        return CollectionUtils.sizeIsEmpty(object);
    }

    /**
     * @see CollectionUtils#isEmpty(Collection)
     */
    public static boolean isEmpty(final Collection<?> coll) {
        return CollectionUtils.isEmpty(coll);
    }

    /**
     * @see CollectionUtils#isNotEmpty(Collection)
     */
    public static boolean isNotEmpty(final Collection<?> coll) {
        return CollectionUtils.isNotEmpty(coll);
    }

    /**
     * @see CollectionUtils#reverseArray(Object[])
     */
    public static void reverseArray(final Object[] array) {
        CollectionUtils.reverseArray(array);
    }

    /**
     * @see CollectionUtils#isFull(Collection)
     */
    public static boolean isFull(final Collection<? extends Object> coll) {
        return CollectionUtils.isFull(coll);
    }

    /**
     * @see CollectionUtils#maxSize(Collection)
     */
    public static int maxSize(final Collection<? extends Object> coll) {
        return CollectionUtils.maxSize(coll);
    }

    /**
     * @see CollectionUtils#collate(Iterable, Iterable)
     */
    public static <O extends Comparable<? super O>> List<O> collate(final Iterable<? extends O> a,
                                                                    final Iterable<? extends O> b) {
        return CollectionUtils.collate(a, b);
    }

    /**
     * @see CollectionUtils#collate(Iterable, Iterable, boolean)
     */
    public static <O extends Comparable<? super O>> List<O> collate(final Iterable<? extends O> a,
                                                                    final Iterable<? extends O> b,
                                                                    final boolean includeDuplicates) {
        return CollectionUtils.collate(a, b, includeDuplicates);
    }

    /**
     * @see CollectionUtils#collate(Iterable, Iterable, Comparator)
     */
    public static <O> List<O> collate(final Iterable<? extends O> a, final Iterable<? extends O> b,
                                      final Comparator<? super O> c) {
        return CollectionUtils.collate(a, b, c);
    }

    /**
     * @see CollectionUtils#collate(Iterable, Iterable, Comparator, boolean)
     */
    public static <O> List<O> collate(final Iterable<? extends O> a, final Iterable<? extends O> b,
                                      final Comparator<? super O> c, final boolean includeDuplicates) {
        return CollectionUtils.collate(a, b, c, includeDuplicates);
    }

    /**
     * @see CollectionUtils#permutations(Collection)
     */
    public static <E> Collection<List<E>> permutations(final Collection<E> collection) {
        return CollectionUtils.permutations(collection);
    }

    /**
     * @see CollectionUtils#retainAll(Collection, Collection)
     */
    public static <C> Collection<C> retainAll(final Collection<C> collection, final Collection<?> retain) {
        return CollectionUtils.retainAll(collection, retain);
    }

    /**
     * @see CollectionUtils#removeAll(Collection, Collection)
     */
    public static <E> Collection<E> removeAll(final Collection<E> collection, final Collection<?> remove) {
        return CollectionUtils.removeAll(collection, remove);
    }

    /**
     * @see CollectionUtils#synchronizedCollection(Collection)
     * @deprecated NE PAS utiliser
     */
    @Deprecated
    public static <C> Collection<C> synchronizedCollection(final Collection<C> collection) {
        return CollectionUtils.synchronizedCollection(collection);
    }

    /**
     * @see CollectionUtils#unmodifiableCollection(Collection)
     * @deprecated NE PAS utiliser
     */
    @Deprecated
    public static <C> Collection<C> unmodifiableCollection(final Collection<? extends C> collection) {
        return CollectionUtils.unmodifiableCollection(collection);
    }

    /**
     * @see CollectionUtils#predicatedCollection(Collection, Predicate)
     */
    public static <C> Collection<C> predicatedCollection(final Collection<C> collection,
                                                         final Predicate<? super C> predicate) {
        return CollectionUtils.predicatedCollection(collection, predicate);
    }

    /**
     * @see CollectionUtils#transformingCollection(Collection, Transformer)
     */
    public static <E> Collection<E> transformingCollection(final Collection<E> collection,
                                                           final Transformer<? super E, ? extends E> transformer) {
        return CollectionUtils.transformingCollection(collection, transformer);
    }

    /**
     * @see CollectionUtils#extractSingleton(Collection)
     */
    public static <E> E extractSingleton(final Collection<E> collection) {
        return CollectionUtils.extractSingleton(collection);
    }
}
