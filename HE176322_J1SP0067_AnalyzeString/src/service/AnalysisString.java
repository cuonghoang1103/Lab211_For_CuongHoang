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
import repository.TextRepository;

/**
 * Service (the brief's AnalysisString class): analyses the numbers and the characters of
 * the input string kept by the repository. Called only by the controller; no print, no
 * keyboard.
 *
 * @author HE176322
 */
public class AnalysisString {

    // keeps the string the service works on (Service -> Repository -> Model)
    private TextRepository textRepository;

    // finds every run of digits in the input
    private Pattern numberPattern;

    // creates the service with an empty repository and the compiled number regex
    public AnalysisString() {
        textRepository = new TextRepository();
        numberPattern = Pattern.compile(Constants.NUMBER_REGEX);
    }

    // keep the text in the repository, analyse it and pack every result line for the view
    public AnalysisResponseDTO analyze(AnalysisRequestDTO requestDTO) {
        // brief: getNumber returns HashMap<String, List<Integer>>
        HashMap<String, List<Integer>> numberMap = null;
        HashMap<String, StringBuilder> characterMap = null;
        InputText inputText = null;
        AnalysisResponseDTO responseDTO = new AnalysisResponseDTO();

        // keep the typed string in the repository, then work on the model it holds
        textRepository.saveInputText(requestDTO.getInput());
        inputText = textRepository.getInputText();

        // the brief's two analyses of the same text
        numberMap = getNumber(inputText.getText());
        characterMap = getCharacter(inputText.getText());

        // every result as text, in the order of the brief's screen
        responseDTO.setLength(inputText.getLength());
        responseDTO.setSquareNumbers(numberMap.get(Constants.KEY_SQUARE).toString());
        responseDTO.setOddNumbers(numberMap.get(Constants.KEY_ODD).toString());
        responseDTO.setEvenNumbers(numberMap.get(Constants.KEY_EVEN).toString());
        responseDTO.setAllNumbers(numberMap.get(Constants.KEY_ALL).toString());
        responseDTO.setUppercase(characterMap.get(Constants.KEY_UPPER).toString());
        responseDTO.setLowercase(characterMap.get(Constants.KEY_LOWER).toString());
        responseDTO.setSpecial(characterMap.get(Constants.KEY_SPECIAL).toString());
        responseDTO.setAllCharacters(characterMap.get(Constants.KEY_ALL_CHARS).toString());
        return responseDTO;
    }

    // brief: HashMap<String, List<Integer>> getNumber(String input)
    private HashMap<String, List<Integer>> getNumber(String input) {
        // brief: the result type is HashMap<String, List<Integer>>
        HashMap<String, List<Integer>> resultMap = new HashMap<>();
        ArrayList<Integer> allList = new ArrayList<>();
        ArrayList<Integer> squareList = new ArrayList<>();
        ArrayList<Integer> oddList = new ArrayList<>();
        ArrayList<Integer> evenList = new ArrayList<>();
        Matcher matcher = numberPattern.matcher(input);
        int number = 0;

        // each find() moves to the next run of digits, until none is left
        while (matcher.find()) {
            number = Integer.parseInt(matcher.group());
            allList.add(number);

            // the brief: square numbers are found with Math.sqrt
            if (isPerfectSquare(number)) {
                squareList.add(number);
            }

            // the brief: odd when number % 2 != 0, otherwise even
            if ((number % Constants.EVEN_DIVISOR) != 0) {
                oddList.add(number);
            } else {
                // divisible by 2
                evenList.add(number);
            }
        }

        // the four lists, each under its own key
        resultMap.put(Constants.KEY_ALL, allList);
        resultMap.put(Constants.KEY_SQUARE, squareList);
        resultMap.put(Constants.KEY_ODD, oddList);
        resultMap.put(Constants.KEY_EVEN, evenList);
        return resultMap;
    }

    // brief: HashMap<String, StringBuilder> getCharacter(String input)
    private HashMap<String, StringBuilder> getCharacter(String input) {
        HashMap<String, StringBuilder> resultMap = new HashMap<>();
        StringBuilder allCharacters = new StringBuilder();
        StringBuilder uppercase = new StringBuilder();
        StringBuilder lowercase = new StringBuilder();
        StringBuilder special = new StringBuilder();
        String character = "";

        // look at every character once, left to right
        for (char currentChar : input.toCharArray()) {
            character = String.valueOf(currentChar);

            // digits belong to getNumber, not to the characters
            if (character.matches(Constants.DIGIT_REGEX)) {
                continue;
            }

            // every other character is one of "All Characters"
            allCharacters.append(currentChar);

            // the brief: uppercase with Character.isUpperCase()
            if (Character.isUpperCase(currentChar)) {
                uppercase.append(currentChar);
            } else if (Character.isLowerCase(currentChar)) {
                // the brief: lowercase is "the opposite"
                lowercase.append(currentChar);
            } else if (character.matches(Constants.SPECIAL_REGEX)) {
                // the brief: special characters with a regular expression
                special.append(currentChar);
            }
        }

        // the four strings, each under its own key
        resultMap.put(Constants.KEY_ALL_CHARS, allCharacters);
        resultMap.put(Constants.KEY_UPPER, uppercase);
        resultMap.put(Constants.KEY_LOWER, lowercase);
        resultMap.put(Constants.KEY_SPECIAL, special);
        return resultMap;
    }

    // a perfect square: the rounded square root, squared, gives the number back
    private boolean isPerfectSquare(int number) {
        long root = Math.round(Math.sqrt(number));

        return (root * root) == number;
    }
}
