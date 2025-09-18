package com.gabrielbursi.domain.user.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AssetIdTest {

    @Test
    void shouldCreateValidAssetId() {
        AssetId assetId = TestAssetIdUtils.createValidAssetId();
        assertEquals(TestAssetIdUtils.createValidAssetId().getValue(), assetId.getValue());
        assertEquals(TestAssetIdUtils.createValidAssetId().getValue(), assetId.toString());
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
        assertThrows(IllegalArgumentException.class, () -> AssetId.of("btc"));
        assertThrows(IllegalArgumentException.class, () -> AssetId.of("BT"));
        assertThrows(IllegalArgumentException.class, () -> AssetId.of("BITCOIN12345"));
        assertThrows(IllegalArgumentException.class, () -> AssetId.of("BTC$"));
    }

    @Test
    void shouldBeEqualWhenSameValue() {
        AssetId asset1 = TestAssetIdUtils.createValidAssetId();
        AssetId asset2 = TestAssetIdUtils.createValidAssetId();
        assertEquals(asset1, asset2);
        assertTrue(asset1.sameAs(asset2));
    }

    @Test
    void shouldNotBeEqualWhenDifferentValue() {
        AssetId asset1 = TestAssetIdUtils.createValidAssetId();
        AssetId asset2 = AssetId.of("ETH");
        assertNotEquals(asset1, asset2);
        assertFalse(asset1.sameAs(asset2));
    }
}
