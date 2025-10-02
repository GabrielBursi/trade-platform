package com.gabrielbursi.domain.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public final class AssetId {
    private final AssetIdEnum value;

    private AssetId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("AssetId cannot be null or empty");
        }
        try {
            this.value = AssetIdEnum.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid AssetId. Only BTC and USD are supported.");
        }
    }

    public static AssetId of(String value) {
        return new AssetId(value);
    }

    public static AssetId btc() {
        return new AssetId(AssetIdEnum.BTC.toString());
    }

    public static AssetId usd() {
        return new AssetId(AssetIdEnum.USD.toString());
    }

    public boolean sameAs(AssetId other) {
        return this.value.equals(other.value);
    }

    public String getValueString() {
        return getValue().toString();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
