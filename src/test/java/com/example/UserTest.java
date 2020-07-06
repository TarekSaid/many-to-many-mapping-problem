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
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void addUsersToNewGroup() {
        // given that I have 3 groups
        Group g1 = new Group().setGroupName("group 1");
        Group g2 = new Group().setGroupName("group 2");
        Group g3 = new Group().setGroupName("group 3");
        List<Group> groups = groupRepository.saveAll(Arrays.asList(g1, g2, g3));

        // and I create a new user with these 3 groups
        User user = userRepository.save(new User().setName("user").addGroup(g1, false).addGroup(g2, false).addGroup(g3, true));

        // then the user should contain all 3 groups
        assertThat(user.getGroups())
            .containsOnly(new UserGroup().setUser(user).setGroup(g1), new UserGroup().setUser(user).setGroup(g2), new UserGroup().setUser(user).setGroup(g3));

        // and each user should contain the group
        for (Group g : groups) {
            assertThat(groupRepository.findById(g.getId()).map(Group::getUsers).orElse(new HashSet<>())).containsOnly(new UserGroup().setUser(user).setGroup(g));
        }
    }

    @Test
    public void addUserToExistingGroup() {
        // given that I have two groups
        Group g1 = new Group().setGroupName("group 1");
        Group g2 = new Group().setGroupName("group 2");
        g1 = groupRepository.save(g1);
        g2 = groupRepository.save(g2);

        // and that I have an user with group 1
        User user = userRepository.save(new User().setName("user 1").addGroup(g1, false));
        assertThat(user.getGroups()).containsOnly(new UserGroup().setUser(user).setGroup(g1));
        assertThat(userRepository.findById(g1.getId()).map(User::getGroups).orElse(new HashSet<>())).containsOnly(new UserGroup().setUser(user).setGroup(g1));
        assertThat(userRepository.findById(g2.getId()).map(User::getGroups).orElse(new HashSet<>())).isEmpty();

        // when I add user 2 to the group
        user.addGroup(g2, false);
        user = userRepository.save(user);

        // then the group should have 2 users
        assertThat(user.getGroups()).containsOnly(new UserGroup().setUser(user).setGroup(g1), new UserGroup().setUser(user).setGroup(g2));

        // and user 2 should have the group
        assertThat(groupRepository.findById(g2.getId()).map(Group::getUsers).orElse(new HashSet<>())).containsOnly(new UserGroup().setUser(user).setGroup(g2));
    }

    @Test
    public void updateExistingUserGroup() {
        // given that I have group 1
        Group group = groupRepository.save(new Group().setGroupName("test group"));

        // and that I have a user with group 1
        User user = userRepository.save(new User().setName("user 1").addGroup(group, false));

        // when I update the UserGroup
        user.getGroups().stream().filter(ug -> ug.getUser().equals(user) && ug.getGroup().equals(group)).findFirst()
            .ifPresent(ug -> ug.setImportant(true));
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getGroups()).filteredOn(ug -> ug.getUser().equals(savedUser) && ug.getGroup().equals(group))
            .extracting(UserGroup::isImportant).containsOnly(true);
    }

    @Test
    public void deleteUserFromGroup() {
        // given that I have two users
        User u1 = new User().setName("user 1");
        User u2 = new User().setName("user 2");
        u1 = userRepository.save(u1);
        u2 = userRepository.save(u2);

        // and that I have a group with user 1
        Group group = groupRepository.save(new Group().setGroupName("test group").addUser(u1, false).addUser(u2, false));

        // when I delete user 2 from the group
        group.deleteUser(u2);
        group = groupRepository.save(group);

        // then the group should have 1 user
//        assertThat(group.getUsers()).containsOnly(new UserGroup().setGroup(group).setUser(u1));

        // and user 2 should not have the group
        assertThat(userRepository.findById(u2.getId()).map(User::getGroups).orElse(new HashSet<>())).isEmpty();
    }
}
