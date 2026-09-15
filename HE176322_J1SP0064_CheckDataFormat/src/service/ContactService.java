package service;

import constants.Constants;
import constants.Message;
import dto.ContactRequestDTO;
import dto.ContactResponseDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Contact;

/**
 * SERVICE: turns the three accepted texts into a Contact whose date is a real calendar
 * Date, and gives back what the view must show.
 *
 * @author HE176322
 */
public class ContactService {

    // Creates the service; it holds no data of its own.
    public ContactService() {
    }

    // Builds the Contact from the request, then copies it into the response.
    public ContactResponseDTO createContact(ContactRequestDTO requestDTO) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        formatter.setLenient(false);
        Date date;
        // the date text has already passed checkDate; parsing is still checked
        try {
            date = formatter.parse(requestDTO.getDate());
        } catch (ParseException e) {
            // a caller that did not call checkDate first
            throw new Exception(Message.DATE_INVALID);
        }
        Contact contact = new Contact(requestDTO.getPhone(), requestDTO.getEmail(), date);
        ContactResponseDTO response = new ContactResponseDTO();
        response.setPhone(contact.getPhone());
        response.setEmail(contact.getEmail());
        response.setDate(formatter.format(contact.getDate()));
        return response;
    }
}
