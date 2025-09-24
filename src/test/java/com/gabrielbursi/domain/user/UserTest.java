package com.gabrielbursi.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.user.vo.AssetId;
import com.gabrielbursi.domain.user.vo.Cpf;
import com.gabrielbursi.domain.user.vo.Email;
import com.gabrielbursi.domain.user.vo.Name;
import com.gabrielbursi.domain.user.vo.Password;
import com.gabrielbursi.domain.user.vo.UserId;

public class UserTest {

        @Test
        void shouldCreateUserWithGeneratedIdAndEncryptedPassword() {
                User user = User.create("Gabriel", "Bursi", "gabriel@email.com",
                                TestUserUtils.createValidCpf().getValue(),
                                TestUserUtils.createValidPlainPassoword());

                assertNotNull(user.getId());
                assertEquals("Gabriel", user.getFirstName().getValue());
                assertEquals("Bursi", user.getLastName().getValue());
                assertEquals("gabriel@email.com", user.getEmail().getValue());
                assertEquals(TestUserUtils.createValidCpf().getValue(), user.getCpf().getValue());
                assertNotEquals(TestUserUtils.createValidPlainPassoword(), user.getPassword().getHash());
        }

        @Test
        void shouldRehydrateUserUsingBuilder() {
                UserId id = UserId.newId();
                Name firstName = Name.of("Gabriel");
                Name lastName = Name.of("Bursi");
                Email email = Email.of("gabriel@email.com");
                Cpf cpf = TestUserUtils.createValidCpf();
                Password password = Password.fromPlain(TestUserUtils.createValidPlainPassoword());
                Map<AssetId, UserAsset> assets = Map.of(AssetId.of("BTC"),
                                UserAsset.create(id, "BTC", BigDecimal.TEN));

                User user = User.builder()
                                .id(id)
                                .firstName(firstName)
                                .lastName(lastName)
                                .email(email)
                                .cpf(cpf)
                                .password(password)
                                .assets(assets)
                                .build();

                assertEquals(id, user.getId());
                assertEquals(firstName, user.getFirstName());
                assertEquals(lastName, user.getLastName());
                assertEquals(email, user.getEmail());
                assertEquals(cpf, user.getCpf());
                assertEquals(password, user.getPassword());
                assertEquals(assets, user.getAssets());
        }

        @Test
        void shouldReturnFullName() {
                User user = User.create("Gabriel", "Bursi", "gabriel@email.com",
                                TestUserUtils.createValidCpf().getValue(),
                                TestUserUtils.createValidPlainPassoword());
                assertEquals("Gabriel Bursi", user.getFullName());
        }

        @Test
        void usersWithSameIdShouldBeEqual() {
                UserId id = UserId.newId();
                User user1 = User.builder()
                                .id(id)
                                .firstName(Name.of("Gabriel"))
                                .lastName(Name.of("Bursi"))
                                .email(Email.of("gabriel@email.com"))
                                .cpf(TestUserUtils.createValidCpf())
                                .password(Password.fromPlain(TestUserUtils.createValidPlainPassoword()))
                                .build();

                User user2 = User.builder()
                                .id(id)
                                .firstName(Name.of("Other"))
                                .lastName(Name.of("User"))
                                .email(Email.of("other@email.com"))
                                .cpf(Cpf.of("98765432100"))
                                .password(Password.fromPlain(TestUserUtils.createValidPlainPassoword()))
                                .build();

                assertEquals(user1, user2);
                assertEquals(user1.hashCode(), user2.hashCode());
        }

        @Test
        void usersWithDifferentIdShouldNotBeEqual() {
                User user1 = User.create("Gabriel", "Bursi", "gabriel@email.com",
                                TestUserUtils.createValidCpf().getValue(),
                                TestUserUtils.createValidPlainPassoword());
                User user2 = User.create("Gabriel", "Bursi", "gabriel@email.com",
                                TestUserUtils.createValidCpf().getValue(),
                                TestUserUtils.createValidPlainPassoword());

                assertNotEquals(user1, user2);
                assertNotEquals(user1.hashCode(), user2.hashCode());
        }

        @Test
        void shouldNotBeEqualWithDifferentType() {
                User user = User.create("Gabriel", "Bursi", "gabriel@email.com",
                                TestUserUtils.createValidCpf().getValue(),
                                TestUserUtils.createValidPlainPassoword());
                assertNotEquals(user, "any string");
        }

        @Test
        void shouldDepositNewAsset() {
                User user = TestUserUtils.createValidUser();
                user.deposit("BTC", BigDecimal.TEN);

                assertTrue(user.getAssets().containsKey(AssetId.of("BTC")));
                assertEquals(0, user.getBalance("BTC").compareTo(BigDecimal.TEN));
        }

        @Test
        void shouldDepositAndIncreaseExistingAsset() {
                User user = TestUserUtils.createValidUser();
                user.deposit("BTC", BigDecimal.valueOf(5));
                user.deposit("BTC", BigDecimal.valueOf(7));

                assertEquals(BigDecimal.valueOf(12), user.getBalance("BTC"));
        }

        @Test
        void shouldWithdrawFromExistingAsset() {
                User user = TestUserUtils.createValidUser();
                user.deposit("BTC", BigDecimal.valueOf(10));
                user.withdraw("BTC", BigDecimal.valueOf(4));

                assertEquals(BigDecimal.valueOf(6), user.getBalance("BTC"));
        }

        @Test
        void shouldThrowWhenWithdrawingFromNonexistentAsset() {
                User user = TestUserUtils.createValidUser();
                assertThrows(IllegalArgumentException.class, () -> user.withdraw("BTC", BigDecimal.ONE));
        }

        @Test
        void shouldThrowWhenWithdrawingMoreThanBalance() {
                User user = TestUserUtils.createValidUser();
                user.deposit("BTC", BigDecimal.valueOf(3));

                assertThrows(IllegalArgumentException.class, () -> user.withdraw("BTC", BigDecimal.valueOf(5)));
        }

        @Test
        void shouldReturnZeroBalanceForNonexistentAsset() {
                User user = TestUserUtils.createValidUser();
                assertEquals(BigDecimal.ZERO, user.getBalance("USD"));
        }

        @Test
        void shouldReturnTotalBalance() {
                User user = TestUserUtils.createValidUser();
                user.deposit("BTC", BigDecimal.valueOf(3));
                user.deposit("ETH", BigDecimal.valueOf(7));
                assertEquals(0, user.getTotalBalance().compareTo(BigDecimal.TEN));
        }

        @Test
        void shouldReturnUnmodifiableAssetsMap() {
                User user = TestUserUtils.createValidUser();
                user.deposit("BTC", BigDecimal.ONE);

                Map<AssetId, UserAsset> assets = user.getAssets();
                assertThrows(UnsupportedOperationException.class, () -> assets.put(AssetId.of("ETH"),
                                UserAsset.create(UserId.newId(), "ETH", BigDecimal.ONE)));
        }
}
