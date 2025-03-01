package fr.unify.discount;

import fr.unify.model.Panier;
import fr.unify.model.Produit;

import java.math.BigDecimal;

public record DiscountUnderCondition(Produit produitTrigger,
                                     int quantiteTrigger,
                                     Produit remiseProduit,
                                     BigDecimal pourcentageRemise,
                                     String description) implements Discount {

    public DiscountUnderCondition(Produit produitTrigger,
                                  int quantiteTrigger,
                                  Produit remiseProduit,
                                  BigDecimal pourcentageRemise){
        this(
                produitTrigger,
                quantiteTrigger,
                remiseProduit,
                pourcentageRemise,
                "Buy " + quantiteTrigger + " " + produitTrigger.nom() +
                        " get " + remiseProduit.nom() + " " + pourcentageRemise + "% off"
        );
    }

    @Override
    public BigDecimal calculateDiscount(Panier panier) {
        if(!isApplicable(panier)){
            return BigDecimal.ZERO;
        }
        int countTrigger = panier.getQuantite(produitTrigger)/quantiteTrigger();
        int remiseCount = Math.min(countTrigger, panier.getQuantite(remiseProduit));

       return remiseProduit.prix()
                .multiply(pourcentageRemise)
                .divide(new BigDecimal("100"),0, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(remiseCount));
    }

    @Override
    public boolean isApplicable(Panier panier) {
        return panier.getQuantite(produitTrigger) >= quantiteTrigger() &&
                panier.isProduitExiste(remiseProduit);
    }

    @Override
    public String getDescription() {
        return description;
    }
}
