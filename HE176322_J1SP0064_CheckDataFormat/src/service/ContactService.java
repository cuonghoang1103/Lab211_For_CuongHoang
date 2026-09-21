package service;

import constants.Constants;
import constants.Message;
import dto.ContactRequestDTO;
import dto.ContactResponseDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Contact;
import repository.ContactRepository;

/**
 * SERVICE: turns the three accepted texts into a Contact whose date is a real calendar
 * Date, keeps it in the repository, and gives back what the view must show. Called only
 * by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class ContactService {

    // Keeps the contact the service works on (Service -> Repository -> Model).
    private ContactRepository contactRepository;

    // Creates the service with an empty repository.
    public ContactService() {
        contactRepository = new ContactRepository();
    }

    // Builds the Contact from the request, keeps it in the repository, then copies it
    // into the response.
    public ContactResponseDTO createContact(ContactRequestDTO requestDTO) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = null;
        Contact contact = null;
        ContactResponseDTO responseDTO = new ContactResponseDTO();

        // a strict calendar: 31/02 is an error, not 03/03
        formatter.setLenient(false);

        // the date text has already passed checkDate; parsing is still checked
        try {
            date = formatter.parse(requestDTO.getDate());
        } catch (ParseException e) {
            // a caller that did not call checkDate first
            throw new Exception(Message.DATE_INVALID);
        }

        // the repository keeps the contact; the answer is built from what it holds
        contactRepository.saveContact(new Contact(requestDTO.getPhone(),
                requestDTO.getEmail(), date));
        contact = contactRepository.getContact();

        // the three values as the screen shows them, the date written back as dd/MM/yyyy
        responseDTO.setPhone(contact.getPhone());
        responseDTO.setEmail(contact.getEmail());
        responseDTO.setDate(formatter.format(contact.getDate()));
        return responseDTO;
    }
}
