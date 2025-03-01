package fr.unify.model;

import java.math.BigDecimal;
import java.util.*;

public class Panier {
    private final Map<Produit, Integer> articles = new HashMap<>();

    public void addProduit(Produit produit) {
        articles.merge(produit, 1, Integer::sum);
    }

    public int getQuantite(Produit produit) {
        return articles.getOrDefault(produit, 0);
    }

    public BigDecimal getCalculerSousTotal() {
        return articles.entrySet().stream()
                .map(entry -> entry.getKey().prix().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Produit> getProduitsUnique() {
        return new ArrayList<>(articles.keySet());
    }

    public boolean isProduitExiste(Produit produit) {
        return articles.containsKey(produit);
    }

    public List<Produit> getAllProduits() {
        return articles.entrySet().stream()
                .flatMap(entree -> Collections.nCopies(entree.getValue(), entree.getKey()).stream())
                .toList();
    }

    public Map<Produit, Integer> getArticles() {
        return Collections.unmodifiableMap(articles);
    }
}
