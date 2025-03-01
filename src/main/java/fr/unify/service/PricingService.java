package fr.unify.service;

import fr.unify.discount.Discount;
import fr.unify.model.Panier;

import java.math.BigDecimal;
import java.util.Map;

public interface PricingService {
    BigDecimal calculateTotal(Panier panier);
    Map<Discount,BigDecimal> calculateRemise(Panier panier);
    String generateOutput(Panier panier);
}
