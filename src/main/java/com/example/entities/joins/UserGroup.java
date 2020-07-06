package com.example.entities.joins;

import com.example.entities.impl.Group;
import com.example.entities.impl.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "user_group")
//@IdClass(UserGroupIdClass.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
public class UserGroup implements Serializable {
    private static final long serialVersionUID = -1122736741031787995L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Id
    @ManyToOne
    @JoinColumn
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
    private Group group;

//    @Id
    @ManyToOne
    @JoinColumn
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
    private User user;

    private boolean important;

    public Long getId() {
        return id;
    }

    public UserGroup setId(Long id) {
        this.id = id;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public UserGroup setGroup(Group group) {
        this.group = group;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserGroup setUser(User user) {
        this.user = user;
        return this;
    }

    public boolean isImportant() {
        return important;
    }

    public UserGroup setImportant(boolean important) {
        this.important = important;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroup)) return false;
        UserGroup userGroup = (UserGroup) o;
        return Objects.equals(group, userGroup.group) &&
            Objects.equals(user, userGroup.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, user);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserGroup.class.getSimpleName() + "[", "]")
            .add("group=" + group)
            .add("user=" + user)
            .add("important=" + important)
            .toString();
    }
}
