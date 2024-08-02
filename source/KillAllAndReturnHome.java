import java.util.*;

public class KillAllAndReturnHome {
    static class Position {
        int row, col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return row == position.row && col == position.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    static final int BOARD_SIZE = 8;
    static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; 
    static final int RIGHT = 0;
    static final int DOWN = 1;
    static final int LEFT = 2;
    static final int UP = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of soldiers: ");
        int numSoldiers = scanner.nextInt();
        scanner.nextLine(); 

        Set<Position> soldiers = new HashSet<>();
        for (int i = 0; i < numSoldiers; i++) {
            System.out.print("Enter coordinates for soldier " + (i + 1) + ": ");
            String[] input = scanner.nextLine().split(",");
            int row = Integer.parseInt(input[0]);
            int col = Integer.parseInt(input[1]);
            soldiers.add(new Position(row, col));
        }

        System.out.print("Enter the coordinates for your castle: ");
        String[] input = scanner.nextLine().split(",");
        Position startCastle = new Position(Integer.parseInt(input[0]), Integer.parseInt(input[1]));

        List<List<Position>> paths = new ArrayList<>();
        findPaths(startCastle, startCastle, soldiers, new ArrayList<>(), paths, RIGHT);

        System.out.println("Thanks. There are " + paths.size() + " unique paths for your ‘special_castle’");
        for (int i = 0; i < paths.size(); i++) {
            System.out.println("Path " + (i + 1));
            System.out.println("=======");
            List<Position> path = paths.get(i);
            for (int j = 0; j < path.size(); j++) {
                Position pos = path.get(j);
                if (j == 0) {
                    System.out.println("Start " + pos);
                } else if (j == path.size() - 1) {
                    System.out.println("Arrive " + pos);
                } else {
                    System.out.println("Kill " + pos + ". Turn Left");
                }
            }
        }
    }

    private static void findPaths(Position start, Position current, Set<Position> soldiers, List<Position> path, List<List<Position>> allPaths, int direction) {
        if (current.equals(start) && !path.isEmpty()) {
            allPaths.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < DIRECTIONS.length; i++) {
            int newDirection = (direction + i) % 4;
            Position nextPosition = move(current, newDirection);

            if (isValid(nextPosition) && soldiers.contains(nextPosition)) {
                soldiers.remove(nextPosition);
                path.add(nextPosition);

                // continue pathfinding
                findPaths(start, nextPosition, soldiers, path, allPaths, (newDirection + 1) % 4);
                
                // Backtrack
                path.remove(path.size() - 1);
                soldiers.add(nextPosition); 
            }
        }
    }

    private static Position move(Position current, int direction) {
        return new Position(current.row + DIRECTIONS[direction][0], current.col + DIRECTIONS[direction][1]);
    }

    private static boolean isValid(Position pos) {
        return pos.row >= 1 && pos.row <= BOARD_SIZE && pos.col >= 1 && pos.col <= BOARD_SIZE;
    }
}
