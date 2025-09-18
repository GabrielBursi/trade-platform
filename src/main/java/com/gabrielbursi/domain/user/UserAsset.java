package com.gabrielbursi.domain.user;

import java.math.BigDecimal;

import com.gabrielbursi.domain.user.vo.AssetId;
import com.gabrielbursi.domain.user.vo.Quantity;
import com.gabrielbursi.domain.user.vo.UserId;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserAsset {
    private final UserId userId;
    private final AssetId assetId;
    private final Quantity quantity;

    /**
     * Usado principalmente para reidratar a entidade a partir do banco.
     */
    @Builder
    public UserAsset(UserId userId, AssetId assetId, Quantity quantity) {
        if (userId == null) throw new IllegalArgumentException("UserId cannot be null");
        if (assetId == null) throw new IllegalArgumentException("AssetId cannot be null");
        if (quantity == null) throw new IllegalArgumentException("Quantity cannot be null");
        this.userId = userId;
        this.assetId = assetId;
        this.quantity = quantity;
    }

    /**
     * Cria uma nova relação entre usuário e ativo com quantidade inicial.
     */
    public static UserAsset create(UserId userId, String assetId, BigDecimal quantity) {
        return new UserAsset(userId, AssetId.of(assetId), Quantity.of(quantity));
    }

    /**
     * Retorna uma nova instância com a quantidade atualizada.
     */
    public UserAsset withUpdatedQuantity(Quantity newQuantity) {
        return new UserAsset(this.userId, this.assetId, newQuantity);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof UserAsset)) return false;
        UserAsset otherUserAsset = (UserAsset) other;
        return this.userId.sameAs(otherUserAsset.userId)
                && this.assetId.sameAs(otherUserAsset.assetId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode() ^ assetId.hashCode();
    }
}
