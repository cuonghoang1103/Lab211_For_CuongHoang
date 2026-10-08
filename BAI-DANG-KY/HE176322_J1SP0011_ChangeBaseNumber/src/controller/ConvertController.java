package controller;

import dto.ConvertRequestDTO;
import dto.ConvertResponseDTO;
import service.ConvertService;
import view.ConvertView;

public class ConvertController {

    // Service tinh toan doi he
    private ConvertService convertService;

    // View in ket qua
    private ConvertView convertView;

    // Constructor: tao service va view
    public ConvertController() {
        convertService = new ConvertService();
        convertView = new ConvertView();
    }

    // Doi he roi dua ket qua cho view in
    public void convert(ConvertRequestDTO convertRequestDTO) throws Exception {
        ConvertResponseDTO convertResponseDTO = convertService.convert(convertRequestDTO);
        convertView.setResponseDTO(convertResponseDTO);
        convertView.display();
    }
}
