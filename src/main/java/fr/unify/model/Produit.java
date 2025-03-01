package fr.unify.model;

import java.math.BigDecimal;

public record Produit(String nom, BigDecimal prix) {
    @Override
    public String toString() {
        return nom + " " + prix;
    }
}
