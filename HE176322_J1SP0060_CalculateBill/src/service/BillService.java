package service;

import dto.BillRequestDTO;
import dto.BillResponseDTO;
import model.Person;
import model.Wallet;

/**
 * SERVICE: the business computation of the program - adding up the bills and asking the
 * wallet whether it can pay.
 *
 * @author HE176322
 */
public class BillService {

    // Creates the service; it holds no data of its own.
    public BillService() {
    }

    // The whole job of the program: build the user from the request, total his bills, and
    // let his wallet decide whether he can pay.
    public BillResponseDTO checkBill(BillRequestDTO requestDTO) {
        Person person = new Person(requestDTO.getBills(),
                new Wallet(requestDTO.getWalletAmount()));
        int total = calcTotal(person.getBills());
        boolean canBuy = person.getWallet().payMoney(total);
        return new BillResponseDTO(total, canBuy);
    }

    // The brief's calcTotal: the sum of every bill, by a plain accumulator.
    public int calcTotal(int[] bills) {
        int total = 0;
        // add each bill once, from the first to the last
        for (int bill : bills) {
            total += bill;
        }
        return total;
    }
}
