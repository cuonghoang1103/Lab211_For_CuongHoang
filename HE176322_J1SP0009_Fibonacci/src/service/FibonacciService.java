package service;

import dto.FibonacciResponseDTO;
import java.util.HashMap;
import model.FibonacciSequence;

/**
 * Service: computes the Fibonacci numbers with recursion (the brief) and a memo so each
 * number is computed only once.
 *
 * @author HE176322
 */
public class FibonacciService {

    // numbers already computed: n -> F(n)
    private HashMap<Integer, Long> memo = new HashMap<>();

    // build the first count Fibonacci numbers and pack them for the view
    public FibonacciResponseDTO generateSequence(int count) {
        FibonacciSequence sequence = new FibonacciSequence();
        // one recursive call per position; the memo makes each one cheap
        for (int n = 0; n < count; n++) {
            sequence.addNumber(fibonacci(n));
        }
        FibonacciResponseDTO response = new FibonacciResponseDTO();
        response.setCount(sequence.getSize());
        response.setSequence(sequence.toString());
        return response;
    }

    // recursive definition F(n) = F(n-1) + F(n-2), remembered in the memo
    private long fibonacci(int n) {
        // base cases F(0) = 0 and F(1) = 1 stop the recursion
        if (n <= 1) {
            return n;
        }
        // computed before: reuse it instead of recursing again
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        long value = fibonacci(n - 1) + fibonacci(n - 2);
        memo.put(n, value);
        return value;
    }
}
