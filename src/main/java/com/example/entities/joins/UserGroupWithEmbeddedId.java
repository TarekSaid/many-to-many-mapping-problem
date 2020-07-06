package com.example.entities.joins;

import com.example.entities.ids.UserGroupId;
import com.example.entities.impl.Group;
import com.example.entities.impl.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "user_group3")
public class UserGroupWithEmbeddedId implements Serializable {
    private static final long serialVersionUID = 2910791645464088704L;

    @EmbeddedId
    private UserGroupId id = new UserGroupId();

    @ManyToOne
    @MapsId("groupId")
    private Group group;

    @ManyToOne
    @MapsId("userId")
    private User user;

    private boolean important;

    public UserGroupId getId() {
        return id;
    }

    public UserGroupWithEmbeddedId setId(UserGroupId id) {
        this.id = id;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public UserGroupWithEmbeddedId setGroup(Group group) {
        this.group = group;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserGroupWithEmbeddedId setUser(User user) {
        this.user = user;
        return this;
    }

    public boolean isImportant() {
        return important;
    }

    public UserGroupWithEmbeddedId setImportant(boolean important) {
        this.important = important;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroupWithEmbeddedId)) return false;
        UserGroupWithEmbeddedId that = (UserGroupWithEmbeddedId) o;
        return Objects.equals(group, that.group) &&
            Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, user);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserGroupWithEmbeddedId.class.getSimpleName() + "[", "]")
            .add("group=" + group)
            .add("user=" + user)
            .add("important=" + important)
            .toString();
    }
}
