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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        // 4
        setRecommendMenuOfWeek(coaches);
        // 5
        outputView.printMenuRecommendResult(coaches);
        outputView.printEmptyLine();
        // 6
        outputView.printEnd();
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

    private void setRecommendMenuOfWeek(List<Coach> coaches) {
        Map<MenuCateGory, Integer> cateGoryCount = new HashMap<>();
        // 월~금 : 처음에 한 주에 두 번 초과 선택한 카테고리인지 확인
        for (int i = 0; i < 5; i++) {
            cateGoryCount = validateSameCategoryOverRange(cateGoryCount);
        }
    }

    private Map<MenuCateGory, Integer> validateSameCategoryOverRange(Map<MenuCateGory, Integer> cateGoryCount) {
        MenuCateGory cateGory = getRandomMenuCategory();
        if (cateGoryCount.containsKey(cateGory)) {
            int count = cateGoryCount.get(cateGory);
            if (count >= 2) {
                validateSameCategoryOverRange(cateGoryCount);
            }
            if (count < 2) {
                cateGoryCount.put(cateGory, count + 1);
            }
        }
        if (!cateGoryCount.containsKey(cateGory)) {
            cateGoryCount.put(cateGory, 1);
        }
        setRecommendMenuOfDayForAllCoaches(coaches, cateGory);
        return cateGoryCount;
    }

    private void setRecommendMenuOfDayForAllCoaches(List<Coach> coaches, MenuCateGory cateGory) {
        Map<MenuCateGory, Integer> cateGoryCount = new HashMap<>();
        for (int i = 0; i < coaches.size(); i++) {
            Coach coach = coaches.get(i);
            String menu = getRecommendMenuOfDayForOneCoach(coach, cateGory);
            coaches.get(i).addRecommendMenusForWeek(menu);
        }
    }

    private String getRecommendMenuOfDayForOneCoach(
            Coach coach, MenuCateGory cateGory) {
        List<String> dislikeMenus = coach.getDislikeMenus();
        String menu = getRandomMenu(cateGory);
        if (dislikeMenus.contains(menu)) {
            // 다시
        }
        return menu;
    }

    private MenuCateGory getRandomMenuCategory() {
        int pickNum = Randoms.pickUniqueNumbersInRange(1, 5, 1).get(0);
        if (pickNum == 1) {
            return MenuCateGory.JAPANESE;
        }
        if (pickNum == 2) {
            return MenuCateGory.KOREAN;
        }
        if (pickNum == 3) {
            return MenuCateGory.CHINESE;
        }
        if (pickNum == 4) {
            return MenuCateGory.ASIAN;
        }
        if (pickNum == 5) {
            return MenuCateGory.WESTERN;
        }
        // TODO: 언제인지 고민하기
        throw new IllegalArgumentException("고를 수 있는 카테고리가 없습니다.");
    }

    private String getRandomMenu(MenuCateGory cateGory) {
        List<String> menus = Arrays.asList(cateGory.getMenus());
        return Randoms.shuffle(menus).get(0);
    }
}
