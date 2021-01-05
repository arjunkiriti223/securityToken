package in.nit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nit.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User getUserByMail(String mail);

}
