package service;

import constants.Message;
import dto.FruitRequestDTO;
import dto.FruitResponseDTO;
import dto.ShopResponseDTO;
import java.util.ArrayList;
import model.Fruit;
import repository.FruitRepository;

/**
 * SERVICE: the shop owner's rules - create fruits and list them. No print, no keyboard.
 *
 * @author HE176322
 */
public class FruitService {

    // Store of the fruits, shared with OrderService.
    private FruitRepository fruitRepository;

    // Creates the service on the given store.
    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    // Option 1, check only: refuses an id that another fruit already uses, ignoring case.
    public void checkFruitId(FruitRequestDTO requestDTO) throws Exception {
        // taken id: the owner must type another one
        if (fruitRepository.findById(requestDTO.getFruitId()) != null) {
            throw new Exception(String.format(Message.ID_EXISTS, requestDTO.getFruitId()));
        }
    }

    // Option 1, one fruit: stores it and answers with "Fruit F001 has been created.".
    public ShopResponseDTO createFruit(FruitRequestDTO requestDTO) throws Exception {
        ShopResponseDTO responseDTO = new ShopResponseDTO();

        // the service never trusts the caller: the id is checked again before storing
        checkFruitId(requestDTO);

        // the five fields of the brief become one Fruit of the list
        fruitRepository.addFruit(new Fruit(requestDTO.getFruitId(), requestDTO.getFruitName(),
                requestDTO.getPrice(), requestDTO.getQuantity(), requestDTO.getOrigin()));
        responseDTO.setMessage(String.format(Message.CREATE_SUCCESS, requestDTO.getFruitId()));
        return responseDTO;
    }

    // Option 1, the answer N: every fruit with its quantity in stock (the owner's table).
    public ShopResponseDTO getStock() {
        ShopResponseDTO responseDTO = new ShopResponseDTO();

        // every fruit as a row, numbered from 1
        responseDTO.setStockList(convertToRowList(fruitRepository.findAll()));
        return responseDTO;
    }

    // Option 3, each round: the buyer's "List of Fruit"; an empty shop is thrown ("There
    // is no fruit in the shop yet.").
    public ShopResponseDTO getFruitList() throws Exception {
        ShopResponseDTO responseDTO = new ShopResponseDTO();

        // nothing to sell yet
        if (fruitRepository.countFruits() == 0) {
            throw new Exception(Message.NO_FRUIT);
        }

        // every fruit as a row, numbered from 1
        responseDTO.setFruitList(convertToRowList(fruitRepository.findAll()));
        return responseDTO;
    }

    // Copies the fruits into table rows, numbered from 1 in the order of creation.
    private ArrayList<FruitResponseDTO> convertToRowList(ArrayList<Fruit> fruitList) {
        ArrayList<FruitResponseDTO> rowList = new ArrayList<>();

        // item number = position in the list + 1
        for (int i = 0; i < fruitList.size(); i++) {
            rowList.add(convertToRow(fruitList.get(i), i + 1));
        }

        return rowList;
    }

    // Copies a fruit into a row for the view (the view never sees the model).
    private FruitResponseDTO convertToRow(Fruit fruit, int itemNumber) {
        FruitResponseDTO row = new FruitResponseDTO();

        // the columns of both fruit tables
        row.setItemNumber(itemNumber);
        row.setFruitName(fruit.getFruitName());
        row.setOrigin(fruit.getOrigin());
        row.setPrice(fruit.getPrice());
        row.setQuantity(fruit.getQuantity());
        return row;
    }
}
