package vn.edu.likelion;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vn.edu.likelion.entity.Role;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.repository.UserRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;

    @Test
    public void createManagerTest(){
        User user = new User("Nam Phuong Nguyen", "phuongnam@gmail.com", "123456789", new Role(3));
        User user1 = new User("Tay Phuong Nguyen", "phuongtay@gmail.com", "123456789", new Role(3));
        List<User> listUser = userRepository.saveAll(List.of(user, user1));
        Assertions.assertThat(listUser.size()).isEqualTo(2);
    }

    @Test
    public void createCustomerTest(){
        User user = new User("Dong Phuong Nguyen", "phuongdong@gmail.com", "123456789", new Role(4));
        User user1 = new User("Bac Phuong Nguyen", "phuongbac@gmail.com", "123456789", new Role(4));

        List<User> savedUsers = userRepository.saveAll(List.of(user, user1));
        Assertions.assertThat(savedUsers).hasSize(2);
    }
}
