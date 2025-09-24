package com.gabrielbursi.domain.user;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.gabrielbursi.domain.user.vo.AssetId;
import com.gabrielbursi.domain.user.vo.Cpf;
import com.gabrielbursi.domain.user.vo.Email;
import com.gabrielbursi.domain.user.vo.Name;
import com.gabrielbursi.domain.user.vo.Password;
import com.gabrielbursi.domain.user.vo.Quantity;
import com.gabrielbursi.domain.user.vo.UserId;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {
    private final UserId id;
    private final Name firstName;
    private final Name lastName;
    private final Email email;
    private final Cpf cpf;
    @ToString.Exclude
    private final Password password;
    @Getter(AccessLevel.NONE)
    private final Map<AssetId, UserAsset> assets = new HashMap<>();

    /**
     * Usado principalmente para reidratar um User a partir de dados persistidos
     */
    @Builder
    public User(UserId id, Name firstName, Name lastName, Email email, Cpf cpf, Password password,
            Map<AssetId, UserAsset> assets) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        if (assets != null && !assets.isEmpty())
            this.assets.putAll(assets);
    }

    /**
     * Cria um novo User a partir de dados crus (plain text).
     * Gera novo UUID e encripta a senha.
     */
    public static User create(String firstName, String lastName, String email, String cpf, String password) {
        return new User(
                UserId.newId(),
                Name.of(firstName),
                Name.of(lastName),
                Email.of(email),
                Cpf.of(cpf),
                Password.fromPlain(password),
                Map.of());
    }

    public void deposit(String assetId, BigDecimal quantity) {
        assets.merge(
                AssetId.of(assetId),
                UserAsset.create(id, assetId, quantity),
                (oldAsset, newAsset) -> oldAsset
                        .withUpdatedQuantity(oldAsset.getQuantity().add(newAsset.getQuantity())));
    }

    public void withdraw(String assetId, BigDecimal quantity) {
        Quantity q = Quantity.of(quantity);
        UserAsset asset = assets.get(AssetId.of(assetId));
        if (asset == null || asset.getQuantity().lessThan(q)) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        assets.put(AssetId.of(assetId), asset.withUpdatedQuantity(asset.getQuantity().subtract(q)));
    }

    public BigDecimal getBalance(String assetId) {
        if (!assets.containsKey(AssetId.of(assetId))) {
            return BigDecimal.ZERO;
        }
        return assets.get(
                AssetId.of(assetId))
                .getQuantity()
                .getValue();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Map<AssetId, UserAsset> getAssets() {
        return Collections.unmodifiableMap(assets);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User))
            return false;
        User otherUser = (User) other;
        return id.sameAs(otherUser.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
