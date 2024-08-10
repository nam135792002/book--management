package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
