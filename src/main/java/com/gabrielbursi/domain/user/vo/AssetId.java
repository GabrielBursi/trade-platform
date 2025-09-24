package com.gabrielbursi.domain.user.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public final class AssetId {
    private final String value;
    public static final String BTC = "BTC";
    public static final String USD = "USD";

    private AssetId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("AssetId cannot be null or empty");
        }
        if (!value.equals(BTC) && !value.equals(USD)) {
            throw new IllegalArgumentException("Invalid AssetId. Only BTC and USD are supported.");
        }
        this.value = value;
    }

    public static AssetId of(String value) {
        return new AssetId(value);
    }

    public static AssetId btc() {
        return new AssetId(BTC);
    }

    public static AssetId usd() {
        return new AssetId(USD);
    }

    public boolean sameAs(AssetId other) {
        return this.value.equals(other.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
