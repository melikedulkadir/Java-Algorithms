import java.util.ArrayList;
import java.util.Collections;

/**
 * This class accomplishes Mission Exterminate
 */
public class OptimalFinalDefenseGP
{
    private ArrayList<Integer> bombWeights;

    public OptimalFinalDefenseGP(ArrayList<Integer> bombWeights) {
        this.bombWeights = bombWeights;
    }

    public ArrayList<Integer> getBombWeights() {
        return bombWeights;
    }

    // Function to find the starting index of the remaining space so that it does not check the filled indexes of the remaining spaces every time
    public int findStart(int start,ArrayList<Integer> remainingSpaces){
        for (int i = start; i < remainingSpaces.size(); i++) {
            if (remainingSpaces.get(i)!=0) start = i;
        }
        return start;

    }

    /**
     *
     * @param maxNumberOfAvailableAUAVs the maximum number of available AUAVs to be loaded with bombs
     * @param maxAUAVCapacity the maximum capacity of an AUAV
     * @return the minimum number of AUAVs required using first fit approach over reversely sorted items.
     * Must return -1 if all bombs can't be loaded onto the available AUAVs
     */

    public int getMinNumberOfAUAVsToDeploy(int maxNumberOfAvailableAUAVs, int maxAUAVCapacity)
    {
        // First sort all weights in decreasing order
        // Initialize result (Count of AUAVs)
        // Create an array to store remaining space in AUAVs, there can be at most maxNumberOfAvailableAUAVs AUAVs
        // Place items one by one

        try{
        bombWeights.sort(Collections.reverseOrder());
        int result = 0;
        int start = 0;
        ArrayList<Integer> remainingSpaces = new ArrayList<>();
        // Checking the capacity and number of available AUAVs
        if (bombWeights.get(0) <= maxAUAVCapacity && maxNumberOfAvailableAUAVs!=0){
            remainingSpaces.add(maxAUAVCapacity-bombWeights.get(0));
            maxNumberOfAvailableAUAVs--;
            result++;
            if (maxAUAVCapacity-bombWeights.get(0)==0) start++;
        }
        else return -1;
        for (int i = 1; i < bombWeights.size(); i++) {
            boolean isReplaced = false;
            for (int j = start; j < remainingSpaces.size(); j++) {
                if (remainingSpaces.get(j)>=bombWeights.get(i)){
                    isReplaced = true;
                    remainingSpaces.set(j,remainingSpaces.get(j) - bombWeights.get(i));
                    if (remainingSpaces.get(j)==0){
                        if (j==start) start = findStart(start,remainingSpaces);
                    }
                    break;
                }
            }
            if (!isReplaced){
                // Checking the capacity and number of available AUAVs
                if (bombWeights.get(i)>maxAUAVCapacity || maxNumberOfAvailableAUAVs == 0){
                    return -1;
                }

                remainingSpaces.add(maxAUAVCapacity-bombWeights.get(i));
                maxNumberOfAvailableAUAVs--;
                result++;
                if (maxAUAVCapacity-bombWeights.get(i)==0){
                    if (start == remainingSpaces.size()-1) start = findStart(start,remainingSpaces);
                }
            }
        }
        return result;
        } catch (Exception exception){
            throw exception;
        }
    }


    public void printFinalDefenseOutcome(int maxNumberOfAvailableAUAVs, int AUAV_CAPACITY){
        int minNumberOfAUAVsToDeploy = this.getMinNumberOfAUAVsToDeploy(maxNumberOfAvailableAUAVs, AUAV_CAPACITY);
        if(minNumberOfAUAVsToDeploy!=-1) {
            System.out.println("The minimum number of AUAVs to deploy for complete extermination of the enemy army: " + minNumberOfAUAVsToDeploy);
        }
        else{
            System.out.println("We cannot load all the bombs. We are doomed.");
        }
    }
}
