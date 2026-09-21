package service;

import dto.FibonacciRequestDTO;
import dto.FibonacciResponseDTO;
import java.util.HashMap;
import model.FibonacciSequence;
import repository.FibonacciRepository;

/**
 * SERVICE: computes the Fibonacci numbers with recursion (the brief) and a memo so each
 * number is computed only once, then keeps them in the repository. Called only by the
 * controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class FibonacciService {

    // Keeps the computed sequence (Service -> Repository -> Model).
    private FibonacciRepository fibonacciRepository;

    // Numbers already computed by the recursion: index -> F(index).
    private HashMap<Integer, Long> memoMap;

    // Creates the service with an empty repository and an empty memo.
    public FibonacciService() {
        fibonacciRepository = new FibonacciRepository();
        memoMap = new HashMap<>();
    }

    // Computes the first count Fibonacci numbers, keeps them in the repository, and packs
    // the sequence it holds for the view.
    public FibonacciResponseDTO generateSequence(FibonacciRequestDTO requestDTO) {
        FibonacciResponseDTO responseDTO = new FibonacciResponseDTO();
        FibonacciSequence fibonacciSequence = null;

        // one recursive call per position; the memo makes each one cheap
        for (int index = 0; index < requestDTO.getCount(); index++) {
            fibonacciRepository.addNumber(calculateFibonacci(index));
        }

        // read the whole sequence back from the repository, then pack it for the view
        fibonacciSequence = fibonacciRepository.getFibonacciSequence();
        responseDTO.setCount(fibonacciSequence.getSize());
        responseDTO.setSequence(fibonacciSequence.toString());
        return responseDTO;
    }

    // The brief's recursion: F(index) = F(index - 1) + F(index - 2), remembered in the memo.
    private long calculateFibonacci(int index) {
        long value = 0;

        // base cases F(0) = 0 and F(1) = 1 stop the recursion
        if (index <= 1) {
            return index;
        }

        // computed before: reuse it instead of recursing again
        if (memoMap.containsKey(index)) {
            return memoMap.get(index);
        }

        // not computed yet: recurse on the two numbers before it, then remember the result
        value = calculateFibonacci(index - 1) + calculateFibonacci(index - 2);
        memoMap.put(index, value);
        return value;
    }
}
