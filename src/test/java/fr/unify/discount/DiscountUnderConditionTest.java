package fr.unify.discount;


import fr.unify.model.Panier;
import fr.unify.model.Produit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de la classe Remise sous condition")
class DiscountUnderConditionTest {
    @Mock
    private Panier mockPanier;

    private Produit soupe;
    private Produit pain;
    private DiscountUnderCondition remise;

    @BeforeEach
    void setUp() {
        soupe = new Produit("Soupe", new BigDecimal("65"));
        pain = new Produit("Pain", new BigDecimal("80"));
        remise = new DiscountUnderCondition(soupe, 2, pain, new BigDecimal("50"));
    }

    @Test
    @DisplayName("Vérification de la description de la remise")
    void testObtenirDescription() {
        assertThat(remise.getDescription()).isEqualTo("Buy 2 Soupe get Pain 50% off");
    }

    @Test
    @DisplayName("Vérification de l'applicabilité quand les conditions sont remplies")
    void testEstApplicable_ConditionsRemplies() {
        when(mockPanier.isProduitExiste(pain)).thenReturn(true);
        when(mockPanier.getQuantite(soupe)).thenReturn(2);

        boolean applicable = remise.isApplicable(mockPanier);

        assertThat(applicable).isTrue();
        verify(mockPanier).isProduitExiste(pain);
        verify(mockPanier).getQuantite(soupe);
    }

    @Test
    @DisplayName("Vérification de l'applicabilité quand le produit remisé est absent")
    void testEstApplicable_ProduitRemiseAbsent() {
        // Given
        when(mockPanier.isProduitExiste(pain)).thenReturn(false);
        when(mockPanier.getQuantite(soupe)).thenReturn(2);

        // When
        boolean applicable = remise.isApplicable(mockPanier);

        // Then
        assertThat(applicable).isFalse();
        verify(mockPanier).isProduitExiste(pain);
        verify(mockPanier).getQuantite(soupe);
    }

}
