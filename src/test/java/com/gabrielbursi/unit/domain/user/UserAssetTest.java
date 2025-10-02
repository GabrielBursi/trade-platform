package com.gabrielbursi.unit.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.shared.AssetId;
import com.gabrielbursi.domain.user.UserAsset;
import com.gabrielbursi.domain.user.vo.Quantity;
import com.gabrielbursi.domain.user.vo.UserId;

public class UserAssetTest {

    @Test
    void shouldCreateUserAssetWithValidValues() {
        UserId userId = UserId.newId();
        String asset = AssetId.btc().toString();
        BigDecimal quantity = BigDecimal.valueOf(2.5);

        UserAsset userAsset = UserAsset.create(userId, asset, quantity);

        assertNotNull(userAsset);
        assertEquals(userId, userAsset.getUserId());
        assertEquals(asset, userAsset.getAssetId().getValueString());
        assertEquals(quantity, userAsset.getQuantity().getValue());
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserAsset(null, AssetId.btc(), Quantity.of(1)));
        assertEquals("UserId cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAssetIdIsNull() {
        UserId userId = UserId.newId();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserAsset(userId, null, Quantity.of(1)));
        assertEquals("AssetId cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNull() {
        UserId userId = UserId.newId();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserAsset(userId, AssetId.btc(), null));
        assertEquals("Quantity cannot be null", exception.getMessage());
    }

    @Test
    void shouldUpdateQuantityImmutably() {
        UserId userId = UserId.newId();
        UserAsset userAsset = UserAsset.create(userId, AssetId.btc().toString(), BigDecimal.valueOf(2));

        UserAsset updated = userAsset.withUpdatedQuantity(Quantity.of(5));

        assertEquals(Quantity.of(2), userAsset.getQuantity());
        assertEquals(Quantity.of(5), updated.getQuantity());
        assertEquals(userAsset.getUserId(), updated.getUserId());
        assertEquals(userAsset.getAssetId(), updated.getAssetId());
        assertNotSame(userAsset, updated);
    }

    @Test
    void equalsAndHashCodeShouldWorkBasedOnUserIdAndAssetId() {
        UserId userId1 = UserId.newId();
        UserId userId2 = UserId.newId();
        AssetId asset1 = AssetId.btc();
        AssetId asset2 = AssetId.usd();

        UserAsset ua1 = new UserAsset(userId1, asset1, Quantity.of(1));
        UserAsset ua2 = new UserAsset(userId1, asset1, Quantity.of(10));
        UserAsset ua3 = new UserAsset(userId1, asset2, Quantity.of(1));
        UserAsset ua4 = new UserAsset(userId2, asset1, Quantity.of(1));

        assertEquals(ua1, ua2);
        assertNotEquals(ua1, ua3);
        assertNotEquals(ua1, ua4);

        assertEquals(ua1.hashCode(), ua2.hashCode());
        assertNotEquals(ua1.hashCode(), ua3.hashCode());
        assertNotEquals(ua1.hashCode(), ua4.hashCode());
    }
}
