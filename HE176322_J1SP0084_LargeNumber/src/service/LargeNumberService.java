package service;

import constants.Constants;
import dto.MultiplyRequestDTO;
import dto.MultiplyResponseDTO;
import model.LargeNumber;
import repository.NumberRepository;

/**
 * Service: multiplies two large numbers digit by digit (schoolbook method). It keeps the
 * numbers in the repository and runs the algorithm on what the repository holds.
 *
 * @author HE176322
 */
public class LargeNumberService {

    // keeps the two numbers the algorithm works on (Service -> Repository -> Model)
    private NumberRepository numberRepository;

    // creates the service with its store
    public LargeNumberService() {
        numberRepository = new NumberRepository();
    }

    // multiply the two typed numbers and pack the result for the view
    public MultiplyResponseDTO multiply(MultiplyRequestDTO requestDTO) {
        MultiplyResponseDTO responseDTO = new MultiplyResponseDTO();
        LargeNumber firstNumber = null;
        LargeNumber secondNumber = null;
        LargeNumber product = null;

        // the brief's step 1: each typed number becomes an array of digits, kept in the store
        numberRepository.saveFirstNumber(toLargeNumber(requestDTO.getFirstNumber()));
        numberRepository.saveSecondNumber(toLargeNumber(requestDTO.getSecondNumber()));

        // the brief's step 2: the schoolbook multiplication runs on the stored numbers
        firstNumber = numberRepository.getFirstNumber();
        secondNumber = numberRepository.getSecondNumber();
        product = multiplyDigits(firstNumber, secondNumber);

        // the brief's step 3: the three numbers as text without leading zeros, for the view
        responseDTO.setFirstNumber(firstNumber.toString());
        responseDTO.setSecondNumber(secondNumber.toString());
        responseDTO.setProduct(product.toString());
        return responseDTO;
    }

    // schoolbook multiplication: result[i + j] += A[i] * B[j], then carry
    private LargeNumber multiplyDigits(LargeNumber first, LargeNumber second) {
        int[] resultArray = new int[first.getLength() + second.getLength()];

        // every digit i of A ...
        for (int i = 0; i < first.getLength(); i++) {
            // ... times every digit j of B lands at position i + j
            for (int j = 0; j < second.getLength(); j++) {
                resultArray[i + j] += first.getDigit(i) * second.getDigit(j);
            }
        }

        // one carry pass from the units up: keep one digit, carry the rest
        for (int k = 0; k < (resultArray.length - 1); k++) {
            resultArray[k + 1] += resultArray[k] / Constants.BASE;
            resultArray[k] %= Constants.BASE;
        }

        return new LargeNumber(resultArray);
    }

    // digits stored units first: character length-1-i becomes digit i
    private LargeNumber toLargeNumber(String text) {
        int length = text.length();
        int[] digitArray = new int[length];

        // read the text from the right
        for (int i = 0; i < length; i++) {
            digitArray[i] = text.charAt((length - 1) - i) - '0';
        }

        return new LargeNumber(digitArray);
    }
}
