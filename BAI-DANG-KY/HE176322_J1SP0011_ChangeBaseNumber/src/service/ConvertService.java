package service;

import constants.Constants;
import constants.Message;
import dto.ConvertRequestDTO;
import dto.ConvertResponseDTO;
import model.BaseNumber;
import repository.BaseNumberRepository;

public class ConvertService {

    // Kho luu cac so da nhap
    private BaseNumberRepository baseNumberRepository;

    // Constructor: tao repository
    public ConvertService() {
        baseNumberRepository = new BaseNumberRepository();
    }

    // Doi gia tri tu he vao sang he ra: he vao -> thap phan -> he ra
    public ConvertResponseDTO convert(ConvertRequestDTO convertRequestDTO) throws Exception {
        baseNumberRepository.save(new BaseNumber(convertRequestDTO.getValue(),
                convertRequestDTO.getInputBase()));
        BaseNumber baseNumber = baseNumberRepository.findLast();
        long decimalValue = convertToDecimal(baseNumber.getValue(),
                baseNumber.getBase().getRadix());
        String outputValue = convertFromDecimal(decimalValue,
                convertRequestDTO.getOutputBase().getRadix());
        return new ConvertResponseDTO(baseNumber.getValue(), baseNumber.getBase().getLabel(),
                outputValue, convertRequestDTO.getOutputBase().getLabel());
    }

    // He bat ky -> thap phan: duyet tu trai sang phai, ket qua = ket qua * co so + chu so
    // (bang dung tong chu so * co so^vi tri nhu vi du cua de)
    private long convertToDecimal(String value, int radix) throws Exception {
        String digits = value.toUpperCase();
        boolean negative = digits.startsWith(Constants.MINUS);
        long result = 0;

        // Bo dau - hoac + o dau
        if (negative || digits.startsWith(Constants.PLUS)) {
            digits = digits.substring(1);
        }

        // Cong don tung chu so
        for (int i = 0; i < digits.length(); i++) {
            int digit = Constants.DIGITS.indexOf(digits.charAt(i));

            // Kiem truoc khi nhan de khong vuot qua so long lon nhat
            if (result > ((Long.MAX_VALUE - digit) / radix)) {
                throw new Exception(Message.TOO_BIG);
            }
            result = (result * radix) + digit;
        }

        // So am thi doi dau
        if (negative) {
            return -result;
        }
        return result;
    }

    // Thap phan -> he bat ky: chia lay du cho co so, doc so du nguoc tu duoi len
    private String convertFromDecimal(long value, int radix) {
        long left = Math.abs(value);
        String result = "";

        // So 0 thi tra ve "0" luon
        if (value == 0) {
            return Constants.ZERO;
        }

        // Chia lien tiep, so du moi ghep vao ben trai
        while (left > 0) {
            result = Constants.DIGITS.charAt((int) (left % radix)) + result;
            left = left / radix;
        }

        // So am thi them dau - phia truoc
        if (value < 0) {
            result = Constants.MINUS + result;
        }
        return result;
    }
}
