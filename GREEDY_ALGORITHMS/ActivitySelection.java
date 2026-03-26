package ALGORITHMS.GREEDY_ALGORITHMS;
/**
 * Activity Selection Problem - Greedy Algorithm
 * 
 * Problem: Select maximum number of non-overlapping activities
 * 
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 */

import java.util.*;

public class ActivitySelection {

    static class Activity {
        int start, end;

        Activity(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static List<Activity> selectActivities(int[] start, int[] end) {
        int n = start.length;
        List<Activity> activities = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            activities.add(new Activity(start[i], end[i]));
        }

        // Sort by end time (greedy choice)
        Collections.sort(activities, (a, b) -> a.end - b.end);

        List<Activity> selected = new ArrayList<>();
        selected.add(activities.get(0));
        int lastEnd = activities.get(0).end;

        for (int i = 1; i < n; i++) {
            if (activities.get(i).start >= lastEnd) {
                selected.add(activities.get(i));
                lastEnd = activities.get(i).end;
            }
        }

        return selected;
    }

    public static void main(String[] args) {
        int[] start = { 1, 3, 0, 5, 8, 5 };
        int[] end = { 2, 4, 6, 7, 9, 9 };

        List<Activity> selected = selectActivities(start, end);

        System.out.println("Maximum activities: " + selected.size());
        System.out.println("Selected activities:");
        for (Activity a : selected) {
            System.out.println("(" + a.start + ", " + a.end + ")");
        }
    }
}
