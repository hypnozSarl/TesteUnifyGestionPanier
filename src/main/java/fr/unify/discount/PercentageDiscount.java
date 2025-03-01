package fr.unify.discount;

import fr.unify.model.Panier;
import fr.unify.model.Produit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record PercentageDiscount(Produit produit,
                                 BigDecimal pourcentageDiscount,String description) implements Discount {

    public PercentageDiscount(Produit produit, BigDecimal pourcentageDiscount){
        this(produit,
                pourcentageDiscount,
                produit.nom()+" "+pourcentageDiscount + "%");
    }

    @Override
    public BigDecimal calculateDiscount(Panier panier) {
        if(!isApplicable(panier)){
            return BigDecimal.ZERO;
        }
        int quantity = panier.getQuantite(produit);
        return produit.prix()
                .multiply(BigDecimal.valueOf(quantity))
                .multiply(pourcentageDiscount)
                .divide(new BigDecimal("100"),0, RoundingMode.HALF_UP);
    }

    @Override
    public boolean isApplicable(Panier panier) {
        return panier.isProduitExiste(produit);
    }

    @Override
    public String getDescription() {
        return description;
    }
}
