package menu.controller;

import static menu.exception.ExceptionMessage.ENDS_WITH_COMMA_OR_STARTS_WITH_COMMA;
import static menu.exception.ExceptionMessage.LENGTH_OF_COACH_NAME_IS_LONG;
import static menu.exception.ExceptionMessage.LENGTH_OF_COACH_NAME_IS_SHORT;
import static menu.exception.ExceptionMessage.NUMBER_OF_COACH_IS_OVER_RANGE;
import static menu.exception.ExceptionMessage.NUMBER_OF_COACH_IS_UNDER_RANGE;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import menu.domain.Coach;
import menu.view.InputView;
import menu.view.OutputView;

public class MainController {

    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();
    private Map<Coach, String> coachesDislikeFoods;
    private List<Coach> coaches;

    public void run() {
        outputView.printStart();
        outputView.printEmptyLine();

        List<String> coachNames = getCoachNames();
        setCoaches(coachNames);


    }

    private List<String> getCoachNames() {
        String input = inputView.getCoachNames();
        if (input.startsWith(",") || input.endsWith(",")) {
            System.out.println(ENDS_WITH_COMMA_OR_STARTS_WITH_COMMA.getMessage());
            throw new IllegalArgumentException(ENDS_WITH_COMMA_OR_STARTS_WITH_COMMA.getMessage());
        }

        String[] coachNames = input.split(",");
        if (coachNames.length < 2) {
            System.out.println(NUMBER_OF_COACH_IS_UNDER_RANGE.getMessage());
            throw new IllegalArgumentException(NUMBER_OF_COACH_IS_UNDER_RANGE.getMessage());
        }
        if (coachNames.length > 5) {
            System.out.println(NUMBER_OF_COACH_IS_OVER_RANGE.getMessage());
            throw new IllegalArgumentException(NUMBER_OF_COACH_IS_OVER_RANGE.getMessage());
        }

        for (String name : coachNames) {
            if (name.length() < 2) {
                System.out.println(LENGTH_OF_COACH_NAME_IS_SHORT.getMessage());
                throw new IllegalArgumentException(LENGTH_OF_COACH_NAME_IS_SHORT.getMessage());
            }
            if (name.length() > 4) {
                System.out.println(LENGTH_OF_COACH_NAME_IS_LONG.getMessage());
                throw new IllegalArgumentException(LENGTH_OF_COACH_NAME_IS_LONG.getMessage());
            }
        }
        return Arrays.asList(coachNames);
    }

    private void setCoaches(List<String> coachNames) {
        for (String name : coachNames) {
            coaches.add(new Coach(name));
        }
    }

}
