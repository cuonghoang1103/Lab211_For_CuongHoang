package service;

import dto.BillRequestDTO;
import dto.BillResponseDTO;
import model.Person;
import repository.PersonRepository;

/**
 * SERVICE: the business computation of the program - adding up the bills and asking the
 * wallet whether it can pay. Called only by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class BillService {

    // Keeps the user the service works on (Service -> Repository -> Model).
    private PersonRepository personRepository;

    // Creates the service with an empty repository.
    public BillService() {
        personRepository = new PersonRepository();
    }

    // The whole job of the program: keep the user (his bills and his wallet) in the
    // repository, total his bills, and let his wallet decide whether he can pay.
    public BillResponseDTO checkBill(BillRequestDTO requestDTO) {
        Person person = null;
        int total = 0;

        // store the user built from the request, then work on the one the repository holds
        personRepository.savePerson(requestDTO);
        person = personRepository.getPerson();

        // the brief's two functions: calcTotal on the bills, payMoney on the wallet
        total = calcTotal(person.getBillArray());
        return new BillResponseDTO(total, person.getWallet().payMoney(total));
    }

    // The brief's calcTotal: the sum of every bill, by a plain accumulator.
    // brief: calcTotal(int[] bills) - the array is named billArray (checklist 1.5).
    public int calcTotal(int[] billArray) {
        int total = 0;

        // add each bill once, from the first to the last
        for (int bill : billArray) {
            total += bill;
        }

        return total;
    }
}
