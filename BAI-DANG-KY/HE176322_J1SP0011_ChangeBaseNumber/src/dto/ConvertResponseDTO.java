package dto;

import constants.Message;

public class ConvertResponseDTO {

    // Gia tri dau vao (nhu nguoi dung go)
    private String inputValue;

    // Nhan he dau vao (BIN/DEC/HEX)
    private String inputLabel;

    // Gia tri sau khi doi
    private String outputValue;

    // Nhan he dau ra (BIN/DEC/HEX)
    private String outputLabel;

    // Constructor day du thuoc tinh
    public ConvertResponseDTO(String inputValue, String inputLabel, String outputValue,
            String outputLabel) {
        this.inputValue = inputValue;
        this.inputLabel = inputLabel;
        this.outputValue = outputValue;
        this.outputLabel = outputLabel;
    }

    // In ket qua dang: 535 (DEC) = 217 (HEX)
    @Override
    public String toString() {
        return String.format(Message.RESULT_FORMAT, inputValue, inputLabel, outputValue,
                outputLabel);
    }
}
