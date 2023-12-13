package menu.view;

import java.util.List;
import menu.domain.Coach;

public class OutputView {

    public void printStart() {
        System.out.println("점심 메뉴 추천을 시작합니다.");
    }

    public void printMenuRecommendResult(List<Coach> coaches) {
        System.out.println("메뉴 추천 결과입니다.");
        System.out.println("[ 구분 | 월요일 | 화요일 | 수요일 | 목요일 | 금요일 ]");
        System.out.println("[ 카테고리 | 한식 | 한식 | 일식 | 중식 | 아시안 ]");
        for (Coach coach : coaches) {
            String name = coach.getName();
            List<String> menus = coach.getRecommendMenusForWeek();
            String menusFormat = String.join(" | ", menus);
            // [ 토미 | 쌈밥 | 김치찌개 | 미소시루 | 짜장면 | 팟타이 ]
            System.out.printf("[ %s | %s ]\n", name, menusFormat);
        }
    }

    public void printEnd() {
        System.out.println("추천을 완료했습니다.");
    }

    public void printEmptyLine() {
        System.out.println();
    }
}
