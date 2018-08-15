package myblog.simpleblog.service;


import myblog.simpleblog.model.entity.Contact;
import myblog.simpleblog.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    public Contact createContact(Contact contact){
        Contact savedContact = contactRepository.save(contact);
        return savedContact;
    }
}
