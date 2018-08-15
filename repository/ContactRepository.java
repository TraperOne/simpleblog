package myblog.simpleblog.repository;

import myblog.simpleblog.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

//implementacja poleceń do DB
}