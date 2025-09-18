package com.gabrielbursi.domain.user.vo;

import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public final class AssetId {
    private final String value;
    private static final Pattern ASSET_ID = Pattern.compile("^[A-Z]{3,10}$");

    public AssetId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("AssetId cannot be null or empty");
        }
        if (!ASSET_ID.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid AssetId format");
        }
        this.value = value;
    }

    public static AssetId of(String value) {
        return new AssetId(value);
    }

    public boolean sameAs(AssetId other) {
        return this.value.equals(other.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
