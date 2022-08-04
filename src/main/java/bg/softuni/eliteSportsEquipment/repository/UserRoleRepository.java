package bg.softuni.eliteSportsEquipment.repository;

import bg.softuni.eliteSportsEquipment.model.entity.user.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
