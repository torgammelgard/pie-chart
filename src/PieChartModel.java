import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Tor Gammelgard
 * @version 2015-11-23
 */
public class PieChartModel {

    private ArrayList<Integer> numbers;
    private int[] angles;

    public PieChartModel() {
        angles = new int[0];
        numbers = new ArrayList<>();
    }

    /**
     * Stores angles like so, <it>[angle1_start, angle1_value, angle2_start, angle2_value, ...]</it>, in the array
     * <code>angles</code>. It sorts the number list first, because in the end it will try to fix so that the end
     * adds up to 360 degrees. (i.e. might distort the biggest pie slice in order to make things add up.)
     */
    private void updateAngles() {
        angles = new int[2 * numbers.size()];
        Collections.sort(numbers);
        ArrayList<Integer> sortedList = new ArrayList<>(numbers);
        Collections.sort(sortedList);
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        for (int i = 0; i < angles.length - 1; i += 2) {
            angles[i] = (i >= 2) ? angles[i - 2] + angles[i - 1] : 0;
            angles[i + 1] = Math.round((360 * (float) sortedList.get(i / 2)) / sum);
        }

        if (angles.length > 2 && angles[angles.length - 1] + angles[angles.length - 2] != 360)
            angles[angles.length - 1] = 360 - angles[angles.length - 2];
    }

    public void addNumber(int number) {
        if (number > 0)
            numbers.add(number);
        updateAngles();
    }

    public void reset() {
        numbers.clear();
        updateAngles();
    }

    public int[] getData() {
        int[] res = new int[numbers.size()];
        for (int i = 0; i < numbers.size(); i++)
            res[i] = numbers.get(i);
        return res;
    }

    public int[] getAngleArray() {
        return angles;
    }

}
