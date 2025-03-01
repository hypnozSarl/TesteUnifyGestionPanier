package fr.unify.service;

import fr.unify.discount.Discount;
import fr.unify.model.Panier;
import fr.unify.print.OutputFormatter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricingServiceImpl implements PricingService {
    private final List<Discount> remisesDisponibles;
    private final OutputFormatter formatter;

    public PricingServiceImpl(List<Discount> remisesDisponibles, OutputFormatter formatter) {
        this.remisesDisponibles = remisesDisponibles;
        this.formatter = formatter;
    }

    @Override
    public BigDecimal calculateTotal(Panier panier) {
        BigDecimal sousTotal = panier.getCalculerSousTotal();
        BigDecimal totalRemise = calculateRemise(panier).values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sousTotal.subtract(totalRemise);
    }

    @Override
    public Map<Discount, BigDecimal> calculateRemise(Panier panier) {
        var remises = new HashMap<Discount, BigDecimal>();
        remisesDisponibles.stream()
                .filter(discount -> discount.isApplicable(panier))
                .forEach(discount -> {
                    var amount = discount.calculateDiscount(panier);
                    if (amount.compareTo(BigDecimal.ZERO) > 0) {
                        remises.put(discount, amount);
                    }
                });
        return remises;
    }

    @Override
    public String generateOutput(Panier panier) {
        BigDecimal sousTotal = panier.getCalculerSousTotal();
        Map<Discount,BigDecimal> discounts = calculateRemise(panier);
        BigDecimal total = calculateTotal(panier);

        StringBuilder sb = new StringBuilder();
        sb.append(formatter.formatSousTotal(sousTotal)).append('\n');

        var discountsText = formatter.formatRemise(discounts);
        sb.append(discountsText).append('\n');

        sb.append(formatter.formatTotal(total));

        return sb.toString();
    }
}
