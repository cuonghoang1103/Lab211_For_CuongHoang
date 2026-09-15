package service;

import dto.ConvertRequestDTO;
import dto.ConvertResponseDTO;
import model.BaseNumber;

/**
 * SERVICE and Strategy CONTEXT: converts the value from the input base to the output base
 * with whatever BaseStrategy it was given.
 *
 * @author HE176322
 */
public class ConvertService {

    // The conversion algorithm, chosen by whoever creates this service.
    private BaseStrategy baseStrategy;

    // Creates the service with the algorithm it must use.
    public ConvertService(BaseStrategy baseStrategy) {
        this.baseStrategy = baseStrategy;
    }

    // Input base -> decimal value -> output base, returned as text.
    public ConvertResponseDTO convert(ConvertRequestDTO requestDTO) throws Exception {
        BaseNumber input = new BaseNumber(requestDTO.getValue(), requestDTO.getInputBase());
        long value = baseStrategy.toDecimal(input);
        BaseNumber output = baseStrategy.fromDecimal(value, requestDTO.getOutputBase());
        ConvertResponseDTO response = new ConvertResponseDTO();
        response.setInput(input.toString());
        response.setOutput(output.toString());
        return response;
    }
}
