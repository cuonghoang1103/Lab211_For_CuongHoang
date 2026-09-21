package service;

import dto.ConvertRequestDTO;
import dto.ConvertResponseDTO;
import model.BaseNumber;
import repository.BaseNumberRepository;

/**
 * SERVICE and Strategy CONTEXT: converts the value from the input base to the output base
 * with whatever IBaseStrategy it was given. Called only by the controller; no print, no
 * keyboard.
 *
 * @author HE176322
 */
public class ConvertService {

    // The conversion algorithm, chosen by whoever creates this service.
    private IBaseStrategy baseStrategy;

    // Keeps the number to convert (Service -> Repository -> Model).
    private BaseNumberRepository baseNumberRepository;

    // Creates the service with an empty repository and the algorithm it must use.
    public ConvertService(IBaseStrategy baseStrategy) {
        baseNumberRepository = new BaseNumberRepository();
        this.baseStrategy = baseStrategy;
    }

    // Input base -> decimal value -> output base, returned as text.
    public ConvertResponseDTO convert(ConvertRequestDTO requestDTO) throws Exception {
        ConvertResponseDTO responseDTO = new ConvertResponseDTO();
        BaseNumber input = null;
        BaseNumber output = null;
        long value = 0;

        // keep the typed number in the repository, then work on the number it holds
        baseNumberRepository.saveBaseNumber(requestDTO);
        input = baseNumberRepository.getBaseNumber();

        // into decimal (examples 2, 4), then out of decimal into the output base (1, 3)
        value = baseStrategy.convertToDecimal(input);
        output = baseStrategy.convertFromDecimal(value, requestDTO.getOutputBase());

        // both sides written as "535 (DEC)" for the view
        responseDTO.setInput(input.toString());
        responseDTO.setOutput(output.toString());
        return responseDTO;
    }
}
