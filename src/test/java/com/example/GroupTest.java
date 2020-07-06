package com.example;

import com.example.entities.impl.Group;
import com.example.entities.impl.User;
import com.example.entities.joins.UserGroup;
import com.example.repositories.GroupRepository;
import com.example.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GroupTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addUsersToNewGroup() {
        // given that I have 3 users
        User u1 = new User().setName("U1");
        User u2 = new User().setName("U2");
        User u3 = new User().setName("U3");
        List<User> users = userRepository.saveAll(Arrays.asList(u1, u2, u3));

        // and I create a new group with these 3 users
        Group group = groupRepository.save(new Group().setGroupName("Test").addUser(u1, true).addUser(u2, true).addUser(u3, false));

        // then the group should contain all 3 users
        assertThat(group.getUsers())
            .containsOnly(new UserGroup().setGroup(group).setUser(u1), new UserGroup().setGroup(group).setUser(u2), new UserGroup().setGroup(group).setUser(u3));

        // and each user should contain the group
        for (User u : users) {
            assertThat(userRepository.findById(u.getId()).map(User::getGroups).orElse(new HashSet<>())).containsOnly(new UserGroup().setGroup(group).setUser(u));
        }
    }

    @Test
    public void addUserToExistingGroup() {
        // given that I have two users
        User u1 = userRepository.save(new User().setName("user 1"));
        User u2 = userRepository.save(new User().setName("user 2"));

        // and that I have a group with user 1
        Group group = groupRepository.save(new Group().setGroupName("test group").addUser(u1, true));
        assertThat(group.getUsers()).containsOnly(new UserGroup().setGroup(group).setUser(u1));
        assertThat(userRepository.findById(u1.getId()).map(User::getGroups).orElse(new HashSet<>())).containsOnly(new UserGroup().setUser(u1).setGroup(group));
        assertThat(userRepository.findById(u2.getId()).map(User::getGroups).orElse(new HashSet<>())).isEmpty();

        // when I add user 2 to the group
        group.addUser(u2, false);
        group = groupRepository.save(group);

        // then the group should have 2 users
        assertThat(group.getUsers()).containsOnly(new UserGroup().setGroup(group).setUser(u1), new UserGroup().setGroup(group).setUser(u2));

        // and user 2 should have the group
        assertThat(userRepository.findById(u2.getId()).map(User::getGroups).orElse(new HashSet<>())).containsOnly(new UserGroup().setGroup(group).setUser(u2));
    }

    @Test
    public void updateExistingUserGroup() {
        // given that I have user 1
        User user = userRepository.save(new User().setName("user 1"));

        // and that I have a group with user 1
        Group group = groupRepository.save(new Group().setGroupName("test group").addUser(user, false));

        // when I update the UserGroup
        group.getUsers().stream().filter(ug -> ug.getUser().equals(user) && ug.getGroup().equals(group)).findFirst()
            .ifPresent(ug -> ug.setActive(true));
        Group savedGroup = groupRepository.save(group);

        assertThat(savedGroup.getUsers()).filteredOn(ug -> ug.getUser().equals(user) && ug.getGroup().equals(savedGroup))
            .extracting(UserGroup::isActive).containsOnly(true);
    }

    @Test
    public void deleteUserFromGroup() {
        // given that I have two users
        User u1 = userRepository.save(new User().setName("user 1"));
        User u2 = userRepository.save(new User().setName("user 2"));

        // and that I have a group with these 2 users
        Group group = groupRepository.save(new Group().setGroupName("test group").addUser(u1, true).addUser(u2, false));

        // when I delete user 2 from the group
        group.deleteUser(u2);
        group = groupRepository.save(group);

        // then the group should have 1 user
        assertThat(group.getUsers()).containsOnly(new UserGroup().setGroup(group).setUser(u1));

        // and user 2 should not have any groups
        assertThat(userRepository.findById(u2.getId()).map(User::getGroups).orElse(new HashSet<>())).isEmpty();
    }
}
