package com.example.entities.ids;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserGroupId implements Serializable {
    private static final long serialVersionUID = -299618878639163956L;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "user_id")
    private Long userId;

    public Long getGroupId() {
        return groupId;
    }

    public UserGroupId setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public UserGroupId setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroupId)) return false;
        UserGroupId that = (UserGroupId) o;
        return Objects.equals(groupId, that.groupId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, userId);
    }
}
