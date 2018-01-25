/**
 * Problem Statement
        You are driving a taxi to reach your destination. Before reaching your destination you have to refuel your taxi so it does not run out of gas. Along the path there are many fueling stations. The price per gallon is different at different fueling stations. Given information about distances to fueling stations ,cost per gallon at each station, mpg that your taxi does, initial tank size, and total trip length find the lowest possible cost of the trip

 Input Format
        Line 1: Comma separated list of distance to i-th fueling station

        Line 2: Comma separated list of cost per gallon at i-th fueling station

        Line 3: mpg (miles per gallon)

        Line 4: Initial tank size

        Line 5: Total distance to destination

 Output format
        You should use double to calculate your cost and print that  **/

import java.io.*;
import java.util.*;
import java.util.ArrayList;

class Test {
    public static void main(String [] args) throws Exception{
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        String str = in.readLine();
        String strCost = in.readLine();

        int MPG = Integer.parseInt(in.readLine());
        double initTank = Integer.parseInt(in.readLine());
        int Total = Integer.parseInt(in.readLine());

        //convert input to integer
        String[] distListStr = str.split("\\p{Punct}");
        String[] costListStr = strCost.split("\\p{Punct}");
        int[] distList = new int[costListStr.length];;
        int[] costList = new int[costListStr.length];

        for(int i = 0; i < distListStr.length; i++){
            distList[i] = Integer.parseInt(distListStr[i]);
            costList[i] = Integer.parseInt(costListStr[i]);
        }

        //sort the distances from closest to farthest in distList
        sortDistCostList(distList, costList)

        //initialize variables
        int count = 0;
        double cost = 0;
        double milesAdd = 0;
        double distLeft = Total;
        double tankCapacity = initTank * MPG;
        double milesLeft = tankCapacity;
        double milesDrove = 0;

        //Goes through each station till reaching the destination
        while(distLeft != 0 ){
            //check if initial tank can reach the destination
            if(milesLeft >= distLeft){
                distLeft = 0;
            }else if (count < costList.length){
                //drive pass each station
                milesDrove = distList[count] - (Total - distLeft);
                milesLeft = milesLeft - milesDrove;
                distLeft = distLeft - milesDrove;
                //find out how much fuel to add
                milesAdd = calculateMilesAdd(costList, distList, count, milesLeft, distLeft, tankCapacity);
                cost = cost + costList[count]*milesAdd/MPG;
                milesLeft = milesLeft + milesAdd;
                count ++;
            }
        }
        System.out.println(cost);
    }


    //helper method: calculate miles to be added at the current station
    public static double calculateMilesAdd(int[] costList, int[] distList, int count, double milesLeft,  double distLeft, double tankCapacity){
        int StationIndex = nextCheapGasStation(costList, count);
        double milesAdd = 0;

        if (StationIndex == count){
            //the current station is the cheapeast, add gallons required to reach destination
            milesAdd = distLeft-milesLeft;
        }else if(distList[StationIndex] - distList[count] <= milesLeft){
            //if not the cheapest, there are enough fuel to get to next cheaper one
            milesAdd = 0;
        }else{
            // else add just enough fuel to next cheap station
            milesAdd = distList[StationIndex] - distList[count] - milesLeft;
        }

        //test if the milesAdd exceed tank capacity
        if (milesLeft + milesAdd > tankCapacity){
            milesAdd = tankCapacity - milesLeft;
        }

        return milesAdd;
    }

    //helper method: find the next cheaper gas station than the current one
    public static int nextCheapGasStation(int[] costList, int count){
        int temp = count;
        int current = costList[count];

        while(count < costList.length){
            if(costList[count] < current){
                return count;
            }
            count ++;
        }
        return temp;
    }


    //sort the list of distList and costList , return void
    public static void sortDistCostList(int[] distList, int[] costList){
        int temp;
        int index; //keep track which  index has the smallest value
        int lowestDist;
        for(int i = 0; i < distList.length-1; i++){
            lowestDist = distList[i];
            index = i;
            //find the smallest value at position i
            for(int j = i+1; j < distList.length; j++){
                if(lowestDist > distList[j]){
                    lowestDist = distList[j];
                    index = j;
                }
            }
            //swap current value with smallest value in position
            temp = distList[i];
            distList[i] = distList[index];
            distList[index] = temp;

            //get the smallest value in costlist at position i
            temp = costList[i];
            costList[i] = costList[index];
            costList[index] = temp;
        }
    }


}