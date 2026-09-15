package service;

import constants.Constants;
import dto.MultiplyRequestDTO;
import dto.MultiplyResponseDTO;
import model.LargeNumber;

/**
 * Service: multiplies two large numbers digit by digit (schoolbook method).
 *
 * @author HE176322
 */
public class LargeNumberService {

    // multiply the two typed numbers and pack the result for the view
    public MultiplyResponseDTO multiply(MultiplyRequestDTO requestDTO) {
        LargeNumber first = toLargeNumber(requestDTO.getFirstNumber());
        LargeNumber second = toLargeNumber(requestDTO.getSecondNumber());
        LargeNumber product = multiplyDigits(first, second);
        MultiplyResponseDTO response = new MultiplyResponseDTO();
        response.setFirstNumber(first.toString());
        response.setSecondNumber(second.toString());
        response.setProduct(product.toString());
        return response;
    }

    // schoolbook multiplication: result[i + j] += A[i] * B[j], then carry
    private LargeNumber multiplyDigits(LargeNumber first, LargeNumber second) {
        int[] result = new int[first.getLength() + second.getLength()];
        // every digit i of A ...
        for (int i = 0; i < first.getLength(); i++) {
            // ... times every digit j of B lands at position i + j
            for (int j = 0; j < second.getLength(); j++) {
                result[i + j] += first.getDigit(i) * second.getDigit(j);
            }
        }
        // one carry pass from the units up: keep one digit, carry the rest
        for (int k = 0; k < result.length - 1; k++) {
            result[k + 1] += result[k] / Constants.BASE;
            result[k] %= Constants.BASE;
        }
        return new LargeNumber(result);
    }

    // digits stored units first: character length-1-i becomes digit i
    private LargeNumber toLargeNumber(String text) {
        int length = text.length();
        int[] digits = new int[length];
        // read the text from the right
        for (int i = 0; i < length; i++) {
            digits[i] = text.charAt(length - 1 - i) - '0';
        }
        return new LargeNumber(digits);
    }
}
