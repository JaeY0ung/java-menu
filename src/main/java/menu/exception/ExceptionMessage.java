package menu.exception;

public enum ExceptionMessage {
    ENDS_WITH_COMMA_OR_STARTS_WITH_COMMA("콤마(,)로 시작하거나 끝나지 않게 입력해야 합니다."),
    NUMBER_OF_COACH_IS_OVER_RANGE("코치는 최소 5명 이하 입력해야 합니다."),
    NUMBER_OF_COACH_IS_UNDER_RANGE("코치는 최소 2명 이상 입력해야 합니다."),
    LENGTH_OF_COACH_NAME_IS_SHORT("코치의 이름은 2글자 이상 입력해야 합니다."),
    LENGTH_OF_COACH_NAME_IS_LONG("코치의 이름은 4글자 이하 입력해야 합니다."),
    NUM_OF_DISLIKE_MENU_IS_OVER_RANGE("못 먹는 메뉴는 2개 이하 입력해야 합니다.");

    private static final String BASE_MESSAGE = "[ERROR]";
    private final String message;

    ExceptionMessage(String message) {
        this.message = String.format("%s %s", BASE_MESSAGE, message);
    }

    public String getMessage() {
        return message;
    }
}
