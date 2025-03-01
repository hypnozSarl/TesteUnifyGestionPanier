package fr.unify.config;

import fr.unify.model.Produit;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CatalogueProduits {
    private static final class Holder {
        private static final CatalogueProduits INSTANCE = new CatalogueProduits();
    }

    private final Map<String, Produit> produits = new HashMap<>();

    public static final String SOUP = "Soup";
    public static final String BREAD = "Bread";
    public static final String MILK = "Milk";
    public static final String APPLES = "Apples";

    private CatalogueProduits() {
        produits.put(SOUP, new Produit(SOUP, new BigDecimal("65")));
        produits.put(BREAD, new Produit(BREAD, new BigDecimal("80")));
        produits.put(MILK, new Produit(MILK, new BigDecimal("130")));
        produits.put(APPLES, new Produit(APPLES, new BigDecimal("100")));
    }

    public static CatalogueProduits getInstance() {
        return Holder.INSTANCE;
    }

    public Produit getProduit(String nom) {
        return produits.get(nom);
    }

    public Optional<Produit> findProduit(String nom) {
        return Optional.ofNullable(produits.get(nom));
    }

    public Produit addProduit(String nom, BigDecimal prix) {
       Produit produit = new Produit(nom, prix);
       produits.put(nom, produit);
       return produit;
    }

    public boolean hasProduit(String nom) {
        return produits.containsKey(nom);
    }

    public Map<String, Produit> getAlleProduits() {
        return Collections.unmodifiableMap(produits);
    }
}
