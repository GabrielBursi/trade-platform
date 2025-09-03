package com.gabrielbursi.domain.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.user.vo.Cpf;
import com.gabrielbursi.domain.user.vo.Email;
import com.gabrielbursi.domain.user.vo.Name;
import com.gabrielbursi.domain.user.vo.Password;

public class UserTest {

    @Test
    void shouldCreateUserWithGeneratedIdAndEncryptedPassword() {
        User user = User.create("Gabriel", "Bursi", "gabriel@email.com", TestUserUtils.createValidCpf().getValue(),
                TestUserUtils.createValidPlainPassoword());

        assertNotNull(user.getId());
        assertDoesNotThrow(() -> UUID.fromString(user.getId()));
        assertEquals("Gabriel", user.getFirstName().getValue());
        assertEquals("Bursi", user.getLastName().getValue());
        assertEquals("gabriel@email.com", user.getEmail().getValue());
        assertEquals(TestUserUtils.createValidCpf().getValue(), user.getCpf().getValue());
        assertNotEquals(TestUserUtils.createValidPlainPassoword(), user.getPassword().getHash());
    }

    @Test
    void shouldRehydrateUserUsingBuilder() {
        String id = UUID.randomUUID().toString();
        Name firstName = Name.of("Gabriel");
        Name lastName = Name.of("Bursi");
        Email email = Email.of("gabriel@email.com");
        Cpf cpf = TestUserUtils.createValidCpf();
        Password password = Password.fromPlain(TestUserUtils.createValidPlainPassoword());

        User user = User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .cpf(cpf)
                .password(password)
                .build();

        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(cpf, user.getCpf());
        assertEquals(password, user.getPassword());
    }

    @Test
    void shouldReturnFullName() {
        User user = User.create("Gabriel", "Bursi", "gabriel@email.com", TestUserUtils.createValidCpf().getValue(),
                TestUserUtils.createValidPlainPassoword());
        assertEquals("Gabriel Bursi", user.getFullName());
    }

    @Test
    void usersWithSameIdShouldBeEqual() {
        String id = UUID.randomUUID().toString();
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
        User user1 = User.create("Gabriel", "Bursi", "gabriel@email.com", TestUserUtils.createValidCpf().getValue(),
                TestUserUtils.createValidPlainPassoword());
        User user2 = User.create("Gabriel", "Bursi", "gabriel@email.com", TestUserUtils.createValidCpf().getValue(),
                TestUserUtils.createValidPlainPassoword());

        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void shouldNotBeEqualWithDifferentType() {
        User user = User.create("Gabriel", "Bursi", "gabriel@email.com", TestUserUtils.createValidCpf().getValue(),
                TestUserUtils.createValidPlainPassoword());
        assertNotEquals(user, "any string");
    }
}
