package com.gabrielbursi.unit.domain.user.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.user.vo.AssetId;

class AssetIdTest {

    @Test
    void shouldCreateValidAssetId() {
        AssetId btc = AssetId.btc();
        assertEquals("BTC", btc.getValue());
        assertEquals("BTC", btc.toString());

        AssetId usd = AssetId.usd();
        assertEquals("USD", usd.getValue());
        assertEquals("USD", usd.toString());
    }

    @Test
    void shouldThrowExceptionWhenNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> AssetId.of(null)
        );
        assertEquals("AssetId cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> AssetId.of(" ")
        );
        assertEquals("AssetId cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInvalidFormat() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> AssetId.of("btc"));
        assertEquals("Invalid AssetId. Only BTC and USD are supported.", exception.getMessage());
        
        exception = assertThrows(IllegalArgumentException.class, () -> AssetId.of("ETH"));
        assertEquals("Invalid AssetId. Only BTC and USD are supported.", exception.getMessage());
    }

    @Test
    void shouldBeEqualWhenSameValue() {
        AssetId asset1 = AssetId.btc();
        AssetId asset2 = AssetId.btc();
        assertEquals(asset1, asset2);
        assertTrue(asset1.sameAs(asset2));
    }

    @Test
    void shouldNotBeEqualWhenDifferentValue() {
        AssetId asset1 = AssetId.btc();
        AssetId asset2 = AssetId.usd();
        assertNotEquals(asset1, asset2);
        assertFalse(asset1.sameAs(asset2));
    }
}
