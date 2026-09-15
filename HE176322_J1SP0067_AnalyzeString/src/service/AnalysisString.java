package service;

import constants.Constants;
import dto.AnalysisRequestDTO;
import dto.AnalysisResponseDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.InputText;

/**
 * Service (the brief's AnalysisString class): analyses the numbers and the characters of
 * the input string.
 *
 * @author HE176322
 */
public class AnalysisString {

    // finds every run of digits in the input
    private Pattern numberPattern = Pattern.compile(Constants.NUMBER_REGEX);

    // analyse the text and pack every result line for the view
    public AnalysisResponseDTO analyze(AnalysisRequestDTO requestDTO) {
        InputText inputText = new InputText(requestDTO.getInput());
        // brief: getNumber returns HashMap<String, List<Integer>>
        HashMap<String, List<Integer>> numbers = getNumber(inputText.getText());
        HashMap<String, StringBuilder> characters = getCharacter(inputText.getText());
        AnalysisResponseDTO response = new AnalysisResponseDTO();
        response.setLength(inputText.getLength());
        response.setSquareNumbers(numbers.get(Constants.KEY_SQUARE).toString());
        response.setOddNumbers(numbers.get(Constants.KEY_ODD).toString());
        response.setEvenNumbers(numbers.get(Constants.KEY_EVEN).toString());
        response.setAllNumbers(numbers.get(Constants.KEY_ALL).toString());
        response.setUppercase(characters.get(Constants.KEY_UPPER).toString());
        response.setLowercase(characters.get(Constants.KEY_LOWER).toString());
        response.setSpecial(characters.get(Constants.KEY_SPECIAL).toString());
        response.setAllCharacters(characters.get(Constants.KEY_ALL_CHARS).toString());
        return response;
    }

    // brief: HashMap<String, List<Integer>> getNumber(String input)
    private HashMap<String, List<Integer>> getNumber(String input) {
        ArrayList<Integer> all = new ArrayList<>();
        ArrayList<Integer> square = new ArrayList<>();
        ArrayList<Integer> odd = new ArrayList<>();
        ArrayList<Integer> even = new ArrayList<>();
        Matcher matcher = numberPattern.matcher(input);
        // each find() moves to the next run of digits, until none is left
        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group());
            all.add(number);
            // the brief: square numbers are found with Math.sqrt
            if (isPerfectSquare(number)) {
                square.add(number);
            }
            // the brief: odd when number % 2 != 0, otherwise even
            if (number % Constants.EVEN_DIVISOR != 0) {
                odd.add(number);
            } else {
                // divisible by 2
                even.add(number);
            }
        }
        // brief: the result type is HashMap<String, List<Integer>>
        HashMap<String, List<Integer>> result = new HashMap<>();
        result.put(Constants.KEY_ALL, all);
        result.put(Constants.KEY_SQUARE, square);
        result.put(Constants.KEY_ODD, odd);
        result.put(Constants.KEY_EVEN, even);
        return result;
    }

    // brief: HashMap<String, StringBuilder> getCharacter(String input)
    private HashMap<String, StringBuilder> getCharacter(String input) {
        StringBuilder allCharacters = new StringBuilder();
        StringBuilder uppercase = new StringBuilder();
        StringBuilder lowercase = new StringBuilder();
        StringBuilder special = new StringBuilder();
        // look at every character once, left to right
        for (char c : input.toCharArray()) {
            String character = String.valueOf(c);
            // digits belong to getNumber, not to the characters
            if (character.matches(Constants.DIGIT_REGEX)) {
                continue;
            }
            allCharacters.append(c);
            // the brief: uppercase with Character.isUpperCase()
            if (Character.isUpperCase(c)) {
                uppercase.append(c);
            } else if (Character.isLowerCase(c)) {
                // the brief: lowercase is "the opposite"
                lowercase.append(c);
            } else if (character.matches(Constants.SPECIAL_REGEX)) {
                // the brief: special characters with a regular expression
                special.append(c);
            }
        }
        HashMap<String, StringBuilder> result = new HashMap<>();
        result.put(Constants.KEY_ALL_CHARS, allCharacters);
        result.put(Constants.KEY_UPPER, uppercase);
        result.put(Constants.KEY_LOWER, lowercase);
        result.put(Constants.KEY_SPECIAL, special);
        return result;
    }

    // a perfect square: the rounded square root, squared, gives the number back
    private boolean isPerfectSquare(int number) {
        long root = Math.round(Math.sqrt(number));
        return root * root == number;
    }
}
