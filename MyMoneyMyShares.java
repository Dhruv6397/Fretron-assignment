import java.util.*;

public class MyMoneyMyShares {
    static class Allocation {
        List<Integer> ram = new ArrayList<>();
        List<Integer> sham = new ArrayList<>();
        List<Integer> rahim = new ArrayList<>();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> apples = new ArrayList<>();
        int totalWeight = 0;
        System.out.println("Enter apple weight in gram (-1 to stop ):");
        
        while (true) {
            int weight = scanner.nextInt();
            if (weight == -1) break;
            apples.add(weight);
            totalWeight += weight;
        }

        Allocation allocation = new Allocation();
        int ramTarget = totalWeight * 50 / 100;
        int shamTarget = totalWeight * 30 / 100;
        int rahimTarget = totalWeight * 20 / 100;

        if (canDistribute(totalWeight, ramTarget, shamTarget, rahimTarget)) {
            if (allocateApples(apples, allocation, 0, ramTarget, shamTarget, rahimTarget)) {
                System.out.println("Distribution Result :");
                System.out.println("Ram : " + allocation.ram);
                System.out.println("Sham : " + allocation.sham);
                System.out.println("Rahim : " + allocation.rahim);
            } else {
                System.out.println("It's not possible to distribute the apples as required.");
            }
        } else {
            System.out.println("It's not possible to divide the total weight into the required proportions.");
        }
    }

    private static boolean canDistribute(int totalWeight, int ramTarget, int shamTarget, int rahimTarget) {
        return totalWeight == (ramTarget + shamTarget + rahimTarget);
    }

    private static boolean allocateApples(List<Integer> apples, Allocation allocation, int index, int ramTarget, int shamTarget, int rahimTarget) {
        if (index == apples.size()) {
            return ramTarget == 0 && shamTarget == 0 && rahimTarget == 0;
        }

        int appleWeight = apples.get(index);

        // allocating to Ram
        if (ramTarget >= appleWeight) {
            allocation.ram.add(appleWeight);
            if (allocateApples(apples, allocation, index + 1, ramTarget - appleWeight, shamTarget, rahimTarget)) {
                return true;
            }
            allocation.ram.remove(allocation.ram.size() - 1);
        }

        // allocating to Sham
        if (shamTarget >= appleWeight) {
            allocation.sham.add(appleWeight);
            if (allocateApples(apples, allocation, index + 1, ramTarget, shamTarget - appleWeight, rahimTarget)) {
                return true;
            }
            allocation.sham.remove(allocation.sham.size() - 1);
        }

        // allocating to Rahim
        if (rahimTarget >= appleWeight) {
            allocation.rahim.add(appleWeight);
            if (allocateApples(apples, allocation, index + 1, ramTarget, shamTarget, rahimTarget - appleWeight)) {
                return true;
            }
            allocation.rahim.remove(allocation.rahim.size() - 1);
        }

        return false;
    }
}
