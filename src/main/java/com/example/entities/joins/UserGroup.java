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
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
public class UserGroup implements Serializable {
    private static final long serialVersionUID = -1122736741031787995L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
    private User user;

    @Column
    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public UserGroup setActive(boolean active) {
        this.active = active;
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
            .add("id=" + id)
            .add("group=" + group)
            .add("user=" + user)
            .add("active=" + active)
            .toString();
    }
}
