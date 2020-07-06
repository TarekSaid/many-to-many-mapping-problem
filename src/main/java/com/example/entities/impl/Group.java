package com.example.entities.impl;

import com.example.entities.DomainEntity;
import com.example.entities.joins.UserGroup;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table(name = "groups")
public class Group extends DomainEntity {
    private static final long serialVersionUID = -8164353648031540293L;

    @Column(name = "group_name")
    private String groupName;

    @Column
    private String role;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserGroup> users = new HashSet<UserGroup>();

    public String getGroupName() {
        return groupName;
    }

    public Group setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getRole() {
        return role;
    }

    public Group setRole(String role) {
        this.role = role;
        return this;
    }

    public Set<UserGroup> getUsers() {
        return new HashSet<>(users);
    }

    public Group setUsers(Set<UserGroup> users) {
        this.users = users;
        return this;
    }

    public Group addUser(User user, boolean important) {
        UserGroup userGroup = new UserGroup().setGroup(this).setUser(user).setImportant(important);

        if (!users.contains(userGroup)) {
            users.add(userGroup);
            user.addGroup(this, important);
        }

        return this;
    }

    public Group deleteUser(User user) {
        UserGroup userGroup = new UserGroup().setGroup(this).setUser(user);

        if (users.contains(userGroup)) {
            users.remove(userGroup);
            user.deleteGroup(this);
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Group.class.getSimpleName() + "[", "]")
            .add("groupName='" + groupName + "'")
            .add("role='" + role + "'")
            .toString();
    }
}
