package com.example.entities.ids;

import com.example.entities.impl.Group;
import com.example.entities.impl.User;

import java.io.Serializable;
import java.util.Objects;

public class UserGroupIdClass implements Serializable {
    private static final long serialVersionUID = 1180961403171920986L;

    private Group group;

    private User user;

    public Group getGroup() {
        return group;
    }

    public UserGroupIdClass setGroup(Group group) {
        this.group = group;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserGroupIdClass setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroupIdClass)) return false;
        UserGroupIdClass that = (UserGroupIdClass) o;
        return Objects.equals(group, that.group) &&
            Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, user);
    }
}
