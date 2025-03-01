package fr.unify.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@DisplayName("Tests de la classe Panier")
class PanierTest {
    private Panier panier;
    private Produit pommes;
    private Produit pain;
    private Produit lait;

    @BeforeEach
    void setUp() {
        panier = new Panier();
        pommes = new Produit("Pommes", new BigDecimal("100"));
        pain = new Produit("Pain", new BigDecimal("80"));
        lait = new Produit("Lait", new BigDecimal("130"));
    }

    @Test
    @DisplayName("Vérification de l'ajout de produits au panier")
    void testAjouterProduit() {
        panier.addProduit(pommes);
        panier.addProduit(pain);
        panier.addProduit(pommes);


        assertThat(panier.getQuantite(pommes)).isEqualTo(2);
        assertThat(panier.getQuantite(pain)).isEqualTo(1);
        assertThat(panier.getQuantite(lait)).isZero();

        assertThat(panier.isProduitExiste(pommes)).isTrue();
        assertThat(panier.isProduitExiste(pain)).isTrue();
        assertThat(panier.isProduitExiste(lait)).isFalse();
    }

    @Test
    @DisplayName("Vérification du calcul du sous-total")
    void testCalculerSousTotal() {

        panier.addProduit(pommes);
        panier.addProduit(pain);
        panier.addProduit(pommes);

        BigDecimal sousTotal = panier.getCalculerSousTotal();

        assertThat(sousTotal).isEqualTo(new BigDecimal("280"));
    }

    @Test
    @DisplayName("Vérification de l'obtention des produits uniques")
    void testObtenirProduitsUniques() {
        // Given
        panier.addProduit(pommes);
        panier.addProduit(pain);
        panier.addProduit(pommes);


        List<Produit> produitsUniques = panier.getProduitsUnique();


        assertThat(produitsUniques).hasSize(2)
                .contains(pommes, pain)
                .doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Vérification de l'obtention de tous les produits")
    void testObtenirTousProduits() {

        panier.addProduit(pommes);
        panier.addProduit(pain);
        panier.addProduit(pommes);

        List<Produit> tousProduits = panier.getAllProduits();

        assertThat(tousProduits).hasSize(3)
                .containsExactlyInAnyOrder(pommes, pain, pommes);
    }

    @Test
    @DisplayName("Vérification de l'obtention des articles et quantités")
    void testObtenirArticles() {
        panier.addProduit(pommes);
        panier.addProduit(pain);
        panier.addProduit(pommes);


        Map<Produit, Integer> articles = panier.getArticles();

        assertThat(articles).hasSize(2)
                .containsEntry(pommes, 2)
                .containsEntry(pain, 1);
    }

    @Test
    @DisplayName("Vérification du panier vide")
    void testPanierVide() {
        // Then
        assertThat(panier.getCalculerSousTotal()).isEqualTo(BigDecimal.ZERO);
        assertThat(panier.getProduitsUnique()).isEmpty();
        assertThat(panier.getAllProduits()).isEmpty();
        assertThat(panier.getArticles()).isEmpty();
    }
}