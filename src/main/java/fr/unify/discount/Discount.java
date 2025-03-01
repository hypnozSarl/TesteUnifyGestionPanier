package fr.unify.discount;

import fr.unify.model.Panier;

import java.math.BigDecimal;

public sealed interface Discount permits PercentageDiscount, DiscountUnderCondition {
    BigDecimal calculateDiscount(Panier panier);
    boolean isApplicable(Panier panier);

    String getDescription();
}
