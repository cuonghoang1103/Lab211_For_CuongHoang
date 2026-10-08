package view;

import dto.ConvertResponseDTO;

public class ConvertView {

    // Ket qua controller dua vao
    private ConvertResponseDTO convertResponseDTO;

    // Constructor
    public ConvertView() {
    }

    // Nhan ket qua tu controller
    public void setResponseDTO(ConvertResponseDTO convertResponseDTO) {
        this.convertResponseDTO = convertResponseDTO;
    }

    // In 1 dong ket qua
    public void display() {
        System.out.println(convertResponseDTO);
    }
}
