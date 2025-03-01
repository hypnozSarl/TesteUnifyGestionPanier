package fr.unify.print;

import fr.unify.discount.Discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

public final class ConsoleOutputFormatter implements OutputFormatter {
    private static final BigDecimal CENT = new BigDecimal("100");

    @Override
    public String formatSousTotal(BigDecimal sousTotal) {
        return "Subtotal: " + formatMontant(sousTotal);
    }

    @Override
    public String formatRemise(Map<Discount, BigDecimal> remises) {
        if (remises.isEmpty()) {
            return "(No offers available)";
        }

        return remises.entrySet().stream()
                .map(entry -> entry.getKey().getDescription() + ": -" + formatMontant(entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String formatTotal(BigDecimal total) {
        return "Total" + (total.compareTo(BigDecimal.ZERO) == 0 ? " price" : "") + ": "
                + formatMontant(total);
    }

    @Override
    public String formatMontant(BigDecimal montant) {
        if (montant.compareTo(CENT) < 0) {
            return montant + "p";
        } else {
            var pounds = montant.divide(CENT, 2, RoundingMode.HALF_UP);
            return "£" + pounds;
        }
    }
}
