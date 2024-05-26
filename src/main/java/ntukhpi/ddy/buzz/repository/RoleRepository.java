package ntukhpi.ddy.buzz.repository;

import ntukhpi.ddy.buzz.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}