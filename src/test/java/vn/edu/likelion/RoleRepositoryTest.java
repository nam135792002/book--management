package vn.edu.likelion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vn.edu.likelion.entity.Role;
import vn.edu.likelion.repository.RoleRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTest {

    @Autowired private RoleRepository roleRepository;

    @Test
    public void createListRoleTest(){
        Role roleAdmin = new Role();
        roleAdmin.setName("Manager");

        Role roleManager = new Role();
        roleManager.setName("Customer");
        List<Role> listRoles = roleRepository.saveAll(List.of(roleAdmin,roleManager));
        Assertions.assertThat(listRoles.size()).isEqualTo(2);
    }
}
