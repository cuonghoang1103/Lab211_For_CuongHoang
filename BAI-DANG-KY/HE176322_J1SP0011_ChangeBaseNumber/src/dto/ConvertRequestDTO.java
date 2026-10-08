package dto;

import constants.Base;

public class ConvertRequestDTO {

    // He co so dau vao
    private Base inputBase;

    // He co so dau ra
    private Base outputBase;

    // Gia tri can doi
    private String value;

    // Constructor rong, Main dien du lieu qua setter
    public ConvertRequestDTO() {
    }

    // Lay he dau vao
    public Base getInputBase() {
        return inputBase;
    }

    // Gan he dau vao
    public void setInputBase(Base inputBase) {
        this.inputBase = inputBase;
    }

    // Lay he dau ra
    public Base getOutputBase() {
        return outputBase;
    }

    // Gan he dau ra
    public void setOutputBase(Base outputBase) {
        this.outputBase = outputBase;
    }

    // Lay gia tri
    public String getValue() {
        return value;
    }

    // Gan gia tri
    public void setValue(String value) {
        this.value = value;
    }
}
