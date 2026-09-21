package dto;

/**
 * DTO carrying the nine answers FROM the controller OUT TO the view, already as text.
 *
 * @author HE176322
 */
public class AnalysisResponseDTO {

    // Number of characters of the string.
    private int length;

    // Perfect square numbers.
    private String squareNumbers;

    // Odd numbers.
    private String oddNumbers;

    // Even numbers.
    private String evenNumbers;

    // All numbers, in the order they appear.
    private String allNumbers;

    // Uppercase characters.
    private String uppercase;

    // Lowercase characters.
    private String lowercase;

    // Special characters.
    private String special;

    // Every character that is not a digit.
    private String allCharacters;

    // JavaBean constructor: an empty response, filled through the setters.
    public AnalysisResponseDTO() {
    }

    // Returns the number of characters.
    public int getLength() {
        return length;
    }

    // Sets the number of characters.
    public void setLength(int length) {
        this.length = length;
    }

    // Returns the perfect square numbers.
    public String getSquareNumbers() {
        return squareNumbers;
    }

    // Sets the perfect square numbers.
    public void setSquareNumbers(String squareNumbers) {
        this.squareNumbers = squareNumbers;
    }

    // Returns the odd numbers.
    public String getOddNumbers() {
        return oddNumbers;
    }

    // Sets the odd numbers.
    public void setOddNumbers(String oddNumbers) {
        this.oddNumbers = oddNumbers;
    }

    // Returns the even numbers.
    public String getEvenNumbers() {
        return evenNumbers;
    }

    // Sets the even numbers.
    public void setEvenNumbers(String evenNumbers) {
        this.evenNumbers = evenNumbers;
    }

    // Returns all numbers.
    public String getAllNumbers() {
        return allNumbers;
    }

    // Sets all numbers.
    public void setAllNumbers(String allNumbers) {
        this.allNumbers = allNumbers;
    }

    // Returns the uppercase characters.
    public String getUppercase() {
        return uppercase;
    }

    // Sets the uppercase characters.
    public void setUppercase(String uppercase) {
        this.uppercase = uppercase;
    }

    // Returns the lowercase characters.
    public String getLowercase() {
        return lowercase;
    }

    // Sets the lowercase characters.
    public void setLowercase(String lowercase) {
        this.lowercase = lowercase;
    }

    // Returns the special characters.
    public String getSpecial() {
        return special;
    }

    // Sets the special characters.
    public void setSpecial(String special) {
        this.special = special;
    }

    // Returns every non-digit character.
    public String getAllCharacters() {
        return allCharacters;
    }

    // Sets every non-digit character.
    public void setAllCharacters(String allCharacters) {
        this.allCharacters = allCharacters;
    }
}
