package ua.nure.bookmeetup.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.nure.bookmeetup.entity.role.Role;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
public abstract class User {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    protected String email;

    @Column(name = "password")
    protected String password;

    @Column(name = "is_locked")
    protected Boolean isLocked;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID")
    protected Role role;

    public User isLocked(boolean isLocked) {
        this.isLocked = isLocked;
        return this;
    }

    public Boolean isLocked() {
        return isLocked;
    }

}
