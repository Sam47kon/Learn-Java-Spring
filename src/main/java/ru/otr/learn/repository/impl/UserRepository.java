package ru.otr.learn.repository.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otr.learn.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	// region NamedQuery
	@Query(name = "User.findByNameAndAgeGreaterThanNamed")
	Optional<User> findByNameAndAgeGreaterThanNamed(@Param("name") String name, @Param("age") int age);
	// endregion


	// region NativeJpaQuery
	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE name = :name AND age > :age limit 1")
	Optional<User> findByNameAndAgeGreaterThanNative(@Param("name") String name, @Param("age") int age);
	// endregion


	// region SimpleJpaQuery
	@Query("SELECT u FROM User u WHERE u.name = :name2 AND u.age >= :age")
	Optional<User> findByNameAndAgeGreaterThanSimple(@Param("name2") String name, @Param("age") int age);

	@Transactional(propagation = Propagation.MANDATORY)
	@Modifying
	@Query("UPDATE User u SET u.role = :role WHERE u.id IN (:ids)")
	int updateRole(User.Role role, List<Long> ids);
	// endregion


	// region PartTreeJpaQuery
	Optional<User> findByNameAndAgeGreaterThanEqual(String name, int age);

	List<User> findUsersByRole(User.Role role);

	List<User> findTop3ByRoleOrderByAgeDesc(User.Role role);

	List<User> findTop3ByRole(User.Role role, Sort sort);

	Slice<User> findAllBy(Pageable pageable);

	Optional<User> findFirstByName(String userName);
	// endregion
}
