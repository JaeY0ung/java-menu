package menu.domain;

import java.util.ArrayList;
import java.util.List;

public class Coach {

    private final String name;
    private List<String> dislikeMenus;
    private final List<String> recommendMenusForWeek = new ArrayList<>();

    public Coach(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDislikeMenu(List<String> dislikeMenus) {
        this.dislikeMenus = dislikeMenus;
    }

    public List<String> getDislikeMenus() {
        return dislikeMenus;
    }

//    public void setRecommendMenusForWeek(List<String> recommendMenusForWeek) {
//        this.recommendMenusForWeek = recommendMenusForWeek;
//    }

    public void addRecommendMenusForWeek(String menu) {
        this.recommendMenusForWeek.add(menu);
    }

    public List<String> getRecommendMenusForWeek() {
        return recommendMenusForWeek;
    }
}
