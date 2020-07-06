package com.example.entities.impl;

import com.example.entities.DomainEntity;
import com.example.entities.joins.UserGroup;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table(name = "users")
public class User extends DomainEntity {
    private static final long serialVersionUID = 7932221846506424557L;

    @Column
    private String name;

    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserGroup> groups = new HashSet<>();

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Set<UserGroup> getGroups() {
        return new HashSet<>(groups);
    }

    public User setGroups(Set<UserGroup> groups) {
        this.groups = groups;
        return this;
    }

    public User addGroup(Group group, boolean important) {
        UserGroup userGroup = new UserGroup().setUser(this).setGroup(group).setImportant(important);

        if (!groups.contains(userGroup)) {
            groups.add(userGroup);
            group.addUser(this, important);
        }

        return this;
    }

    public User deleteGroup(Group group) {
        UserGroup userGroup = new UserGroup().setUser(this).setGroup(group);

        if (groups.contains(userGroup)) {
            groups.remove(userGroup);
            group.deleteUser(this);
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
            .add("name='" + name + "'")
            .toString();
    }
}
