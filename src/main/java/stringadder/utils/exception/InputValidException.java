package stringadder.utils.exception;

public class InputValidException {

    private static final String NEGATIVE_INPUT_ERROR = "음수는 입력할 수 없습니다!";
    private static final int ZERO_NUMBER = 0;

    public static void inputNegativeValidation(int input) {
        if (input < ZERO_NUMBER) {
            throw new RuntimeException(NEGATIVE_INPUT_ERROR);
        }
    }
}
