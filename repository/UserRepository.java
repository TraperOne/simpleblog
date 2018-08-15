package myblog.simpleblog.repository;


import myblog.simpleblog.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //implementacja poleceń do DB
    User findOneByEmail(String email);
    //adnotacja
}
