package service;

import constants.Message;
import dto.FruitRequestDTO;
import dto.FruitResponseDTO;
import java.util.ArrayList;
import model.Fruit;
import repository.FruitRepository;

/**
 * SERVICE: the shop owner's rules - create fruits and list them.
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

    // Refuses an id that another fruit already uses, ignoring case.
    public void checkFruitId(FruitRequestDTO requestDTO) throws Exception {
        // taken id: the owner must type another one
        if (fruitRepository.findById(requestDTO.getFruitId()) != null) {
            throw new Exception(String.format(Message.ID_EXISTS, requestDTO.getFruitId()));
        }
    }

    // Stores a new fruit and returns its id for the success message.
    public String createFruit(FruitRequestDTO requestDTO) throws Exception {
        checkFruitId(requestDTO);
        Fruit fruit = new Fruit(requestDTO.getFruitId(), requestDTO.getFruitName(),
                requestDTO.getPrice(), requestDTO.getQuantity(), requestDTO.getOrigin());
        fruitRepository.addFruit(fruit);
        return fruit.getFruitId();
    }

    // Returns every fruit as a table row, numbered from 1 in the order of creation.
    public ArrayList<FruitResponseDTO> getAllFruits() {
        ArrayList<Fruit> fruits = fruitRepository.findAll();
        ArrayList<FruitResponseDTO> rows = new ArrayList<>();
        // item number = position in the list + 1
        for (int i = 0; i < fruits.size(); i++) {
            rows.add(toResponse(fruits.get(i), i + 1));
        }
        return rows;
    }

    // Copies a fruit into a row for the view.
    private FruitResponseDTO toResponse(Fruit fruit, int itemNumber) {
        FruitResponseDTO row = new FruitResponseDTO();
        row.setItemNumber(itemNumber);
        row.setFruitName(fruit.getFruitName());
        row.setOrigin(fruit.getOrigin());
        row.setPrice(fruit.getPrice());
        row.setQuantity(fruit.getQuantity());
        return row;
    }
}
