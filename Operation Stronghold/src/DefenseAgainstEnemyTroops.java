import java.util.ArrayList;
import java.util.Collections;

/**
 * This class accomplishes Mission Nuke'm
 */
public class DefenseAgainstEnemyTroops {
    private ArrayList<Integer> numberOfEnemiesArrivingPerHour;

    public DefenseAgainstEnemyTroops(ArrayList<Integer> numberOfEnemiesArrivingPerHour){
        this.numberOfEnemiesArrivingPerHour = numberOfEnemiesArrivingPerHour;
    }

    public ArrayList<Integer> getNumberOfEnemiesArrivingPerHour() {
        return numberOfEnemiesArrivingPerHour;
    }

    private int getRechargedWeaponPower(int hoursCharging){
        return hoursCharging*hoursCharging;
    }

    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalEnemyDefenseSolution
     */

    // Function for finding optimal solution and hours
    public OptimalEnemyDefenseSolution getOptimalDefenseSolutionDP(){
        ArrayList<Integer> sol = new ArrayList<>();
        ArrayList<ArrayList<Integer>> hours = new ArrayList<>();
            // TODO: YOUR CODE HERE
            sol.add(0);
            hours.add(new ArrayList<>());
            int max = 0;
            int min = 0;
            int size = numberOfEnemiesArrivingPerHour.size();
            for (int j = 1; j < size+1; j++) {
                hours.add(new ArrayList<>());
                for (int i = 0; i < j; i++) {
                    min = Math.min(numberOfEnemiesArrivingPerHour.get(j-1),getRechargedWeaponPower(j-i));
                    int sum = sol.get(i)+ min;
                    if (sum  > max){
                        max = sum;
                        hours.get(j).clear();
                        hours.get(j).addAll(hours.get(i));
                    }
                }
                sol.add(max);
                hours.get(j).add(j);
            }
            return new OptimalEnemyDefenseSolution(max, hours.get((hours.size()-1)));

    }
}
