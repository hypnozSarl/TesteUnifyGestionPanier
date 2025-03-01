package fr.unify.discount;

import fr.unify.model.Panier;
import fr.unify.model.Produit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;


@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de la classe RemisePourcentage")
class PercentageDiscountTest {
    @Mock
    private Panier mockPanier;

    private Produit pommes;
    private PercentageDiscount remise;

    @BeforeEach
    void setUp() {
        pommes = new Produit("Pommes", new BigDecimal("100"));
        remise = new PercentageDiscount(pommes, new BigDecimal("10"));
    }

    @Test
    @DisplayName("Vérification de la description de la remise")
    void testObtenirDescription() {
        assertThat(remise.getDescription()).isEqualTo("Pommes 10%");
    }

    @Test
    @DisplayName("Vérification de l'applicabilité de la remise quand le produit est présent")
    void testEstApplicable_ProduitPresent() {
        when(mockPanier.isProduitExiste(pommes)).thenReturn(true);
        boolean applicable = remise.isApplicable(mockPanier);

        assertThat(applicable).isTrue();
        verify(mockPanier).isProduitExiste(pommes);
    }

    @Test
    @DisplayName("Vérification de l'applicabilité de la remise quand le produit est absent")
    void testEstApplicable_ProduitAbsent() {
        when(mockPanier.isProduitExiste(pommes)).thenReturn(false);

        boolean applicable = remise.isApplicable(mockPanier);

        assertThat(applicable).isFalse();
        verify(mockPanier).isProduitExiste(pommes);
    }

    @Test
    @DisplayName("Vérification du calcul de la remise pour un article")
    void testCalculerRemise_UnArticle() {

        when(mockPanier.isProduitExiste(pommes)).thenReturn(true);
        when(mockPanier.getQuantite(pommes)).thenReturn(1);

        BigDecimal montantRemise = remise.calculateDiscount(mockPanier);

        assertThat(montantRemise).isEqualTo(new BigDecimal("10"));
        verify(mockPanier).isProduitExiste(pommes);
        verify(mockPanier).getQuantite(pommes);
    }

    @Test
    @DisplayName("Vérification du calcul de la remise pour plusieurs articles")
    void testCalculerRemise_PlusieursArticles() {

        when(mockPanier.isProduitExiste(pommes)).thenReturn(true);
        when(mockPanier.getQuantite(pommes)).thenReturn(3);

        BigDecimal montantRemise = remise.calculateDiscount(mockPanier);


        assertThat(montantRemise).isEqualTo(new BigDecimal("30")); // 10% de 100 × 3 = 30
        verify(mockPanier).isProduitExiste(pommes);
        verify(mockPanier).getQuantite(pommes);
    }

    @Test
    @DisplayName("Vérification que la remise est zéro si le produit est absent")
    void testCalculerRemise_ProduitAbsent() {

        when(mockPanier.isProduitExiste(pommes)).thenReturn(false);

        BigDecimal montantRemise = remise.calculateDiscount(mockPanier);

        assertThat(montantRemise).isEqualTo(BigDecimal.ZERO);
        verify(mockPanier).isProduitExiste(pommes);
        verify(mockPanier, never()).getQuantite(any());
    }
}