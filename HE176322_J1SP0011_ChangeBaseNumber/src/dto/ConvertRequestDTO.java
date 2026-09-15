package dto;

import constants.Base;

/**
 * DTO carrying what the user chose and typed FROM main INTO the controller: input base,
 * output base and the value.
 *
 * @author HE176322
 */
public class ConvertRequestDTO {

    // The base the value is written in.
    private Base inputBase;
    // The base to convert to.
    private Base outputBase;
    // The value as typed (trimmed, not empty).
    private String value;

    // JavaBean constructor: an empty request; main fills it through setters.
    public ConvertRequestDTO() {
    }

    // Returns the input base.
    public Base getInputBase() {
        return inputBase;
    }

    // Sets the input base.
    public void setInputBase(Base inputBase) {
        this.inputBase = inputBase;
    }

    // Returns the output base.
    public Base getOutputBase() {
        return outputBase;
    }

    // Sets the output base.
    public void setOutputBase(Base outputBase) {
        this.outputBase = outputBase;
    }

    // Returns the value.
    public String getValue() {
        return value;
    }

    // Sets the value.
    public void setValue(String value) {
        this.value = value;
    }
}
