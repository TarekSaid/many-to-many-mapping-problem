package com.example.entities.joins;

import com.example.entities.ids.UserGroupIdClass;
import com.example.entities.impl.Group;
import com.example.entities.impl.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "user_group2")
@IdClass(UserGroupIdClass.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
public class UserGroupWithIdClass implements Serializable {
    private static final long serialVersionUID = -4455709755460548269L;

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "groupId", referencedColumnName = "id", nullable = false, updatable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
    private Group group;

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false, updatable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
    private User user;

    private boolean important;

    public Group getGroup() {
        return group;
    }

    public UserGroupWithIdClass setGroup(Group group) {
        this.group = group;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserGroupWithIdClass setUser(User user) {
        this.user = user;
        return this;
    }

    public boolean isImportant() {
        return important;
    }

    public UserGroupWithIdClass setImportant(boolean important) {
        this.important = important;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroupWithIdClass)) return false;
        UserGroupWithIdClass userGroup = (UserGroupWithIdClass) o;
        return Objects.equals(group, userGroup.group) &&
            Objects.equals(user, userGroup.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, user);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserGroupWithIdClass.class.getSimpleName() + "[", "]")
            .add("group=" + group)
            .add("user=" + user)
            .add("important=" + important)
            .toString();
    }
}
