package fr.unify.print;

import fr.unify.discount.Discount;

import java.math.BigDecimal;
import java.util.Map;

public sealed interface OutputFormatter permits ConsoleOutputFormatter {
    String formatSousTotal(BigDecimal sousTotal);
    String formatRemise(Map<Discount,BigDecimal> remises);
    String formatTotal(BigDecimal total);
    String formatMontant(BigDecimal montant);

}
