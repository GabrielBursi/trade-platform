package com.gabrielbursi.domain.user;

import com.gabrielbursi.domain.user.vo.Cpf;
import com.gabrielbursi.domain.user.vo.Email;
import com.gabrielbursi.domain.user.vo.Name;
import com.gabrielbursi.domain.user.vo.Password;
import com.gabrielbursi.domain.user.vo.UserId;

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

    /**
     * Usado principalmente para reidratar um User a partir de dados persistidos
     */
    @Builder
    public User(UserId id, Name firstName, Name lastName, Email email, Cpf cpf, Password password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
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
                Password.fromPlain(password));
    }

    public String getFullName() {
        return firstName + " " + lastName;
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
