package fr.unify;

import fr.unify.config.CatalogueProduits;
import fr.unify.discount.Discount;
import fr.unify.discount.DiscountUnderCondition;
import fr.unify.discount.PercentageDiscount;
import fr.unify.model.Panier;
import fr.unify.print.ConsoleOutputFormatter;
import fr.unify.service.PricingServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static List<Discount> creerRemisesDirectement() {
        var catalogue = CatalogueProduits.getInstance();
        List<Discount> remises = new ArrayList<>();
        var apple = catalogue.getProduit(CatalogueProduits.APPLES);
        remises.add(new PercentageDiscount(apple, new BigDecimal("10")));
        var soupe = catalogue.getProduit(CatalogueProduits.SOUP);
        var pain = catalogue.getProduit(CatalogueProduits.BREAD);
        remises.add(new DiscountUnderCondition(soupe, 2, pain, new BigDecimal("50")));

        return remises;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("""
                Usage: PriceBasket item1 item2 ...
                Example: PriceBasket Apples Milk Bread
                """);
            return;
        }
        var catalogue = CatalogueProduits.getInstance();
        var remises = creerRemisesDirectement();
        var formateur = new ConsoleOutputFormatter();
        var serviceTarification = new PricingServiceImpl(remises, formateur);

        var panier = new Panier();
        Arrays.stream(args)
                .filter(catalogue::hasProduit)
                .map(catalogue::getProduit)
                .forEach(panier::addProduit);

        Arrays.stream(args)
                .filter(nom -> !catalogue.hasProduit(nom))
                .forEach(nom -> LOGGER.warning("Attention: " + nom + " n'est pas un produit valide et sera ignoré."));

        var sortie = serviceTarification.generateOutput(panier);
        System.out.println(sortie);
    }
}