package menu.controller;

import static menu.exception.ExceptionMessage.ENDS_WITH_COMMA_OR_STARTS_WITH_COMMA;
import static menu.exception.ExceptionMessage.LENGTH_OF_COACH_NAME_IS_LONG;
import static menu.exception.ExceptionMessage.LENGTH_OF_COACH_NAME_IS_SHORT;
import static menu.exception.ExceptionMessage.NUMBER_OF_COACH_IS_OVER_RANGE;
import static menu.exception.ExceptionMessage.NUMBER_OF_COACH_IS_UNDER_RANGE;
import static menu.exception.ExceptionMessage.NUM_OF_DISLIKE_MENU_IS_OVER_RANGE;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import menu.domain.Coach;
import menu.domain.MenuCateGory;
import menu.view.InputView;
import menu.view.OutputView;

public class MainController {

    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();
    private List<Coach> coaches;

    public void run() {
        // 1
        outputView.printStart();
        outputView.printEmptyLine();
        // 2
        coaches = getCoaches();
        outputView.printEmptyLine();
        // 3
        setCoachesDislikeMenus();
        outputView.printEmptyLine();
    }

    private List<Coach> getCoaches() {
        List<Coach> coaches = new ArrayList<Coach>();

        String input = inputView.getCoachNames();
        if (input.startsWith(",") || input.endsWith(",")) {
            System.out.println(ENDS_WITH_COMMA_OR_STARTS_WITH_COMMA.getMessage());
            throw new IllegalArgumentException(ENDS_WITH_COMMA_OR_STARTS_WITH_COMMA.getMessage());
        }
        // 코치 수
        String[] coachNames = input.split(",");
        if (coachNames.length < 2) {
            System.out.println(NUMBER_OF_COACH_IS_UNDER_RANGE.getMessage());
            throw new IllegalArgumentException(NUMBER_OF_COACH_IS_UNDER_RANGE.getMessage());
        }
        if (coachNames.length > 5) {
            System.out.println(NUMBER_OF_COACH_IS_OVER_RANGE.getMessage());
            throw new IllegalArgumentException(NUMBER_OF_COACH_IS_OVER_RANGE.getMessage());
        }
        // 코치 이름 길이
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
        for (String name : coachNames) {
            coaches.add(new Coach(name));
        }
        return coaches;
    }

    private void setCoachesDislikeMenus() {
        for (int i = 0; i < coaches.size(); i++) {
            List<String> dislikeMenus = getCoachDislikeMenus(coaches.get(i));
            coaches.get(i).setDislikeMenu(dislikeMenus);
        }
    }

    private List<String> getCoachDislikeMenus(Coach coach) {
        String input = inputView.getCoachDislikeFoods(coach.getName());

        if (input.startsWith(",") || input.endsWith(",")) {
            System.out.println(ENDS_WITH_COMMA_OR_STARTS_WITH_COMMA.getMessage());
            throw new IllegalArgumentException(ENDS_WITH_COMMA_OR_STARTS_WITH_COMMA.getMessage());
        }
        // 빈 리스트 반환
        if (input.isEmpty()) {
            return new ArrayList<String>();
        }
        String[] menus = input.split(",");
        if (menus.length > 2) {
            System.out.println(NUM_OF_DISLIKE_MENU_IS_OVER_RANGE.getMessage());
            throw new IllegalArgumentException(NUM_OF_DISLIKE_MENU_IS_OVER_RANGE.getMessage());
        }
        // TODO: 메뉴에 없으면 다시 입력하게 하기
//            for (String menu : menus) {
//                if (menu)
//            }
        return Arrays.asList(menus);
    }
    }

}
