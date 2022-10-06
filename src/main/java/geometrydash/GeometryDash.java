package geometrydash;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GeometryDash {
    /**
     * Returns whether the given level can be completed using the given play.
     * @param level is not null and not empty
     * @param play is not null and not empty
     * @return true if the play completes the level and false otherwise
     */
    public static boolean isSuccessfulPlay(String level, String play) {
        if (level.charAt(0) == '^' || level.charAt(level.length()-1) == '^') {
            return false;
        }
        char[] levelArr = level.toCharArray();
        char[] playArr = play.toCharArray();

        int position = 0;

        for (char p : playArr) {

            if (levelArr[position + p - '0'] == '_') {
                position += (p - '0');
            } else {
                return false;
            }
        }
        if (position >= levelArr.length-1) {
            return true;
        }

        return false;
    }

    private static boolean isSuccessfulPlayEnergy(String level, String play, int startingEnergy, int targetRestingEnergy) {
        if (level.charAt(0) == '^' || level.charAt(level.length()-1) == '^') {
            return false;
        }
        char[] levelArr = level.toCharArray();
        char[] playArr = play.toCharArray();

        int position = 0;
        int energy = startingEnergy;

        for (char p : playArr) {

            if (position >= levelArr.length-1) {
                return false;
            }

            if (p == '0') {
                if (energy < 3) {
                    energy = energy + 1;
                    break;
                } else {
                    return false;
                }
            }

            if ((levelArr[position + p - '0'] == '_' || levelArr[position + p - '0'] == '*')  && (energy - (p - '0') >= 0) ) {
                position += (p - '0');
                energy -= (p - '0');
            } else {
                return false;
            }

            if (levelArr[position] == '*') {
                position += 4;
            }
        }
        if ((position == levelArr.length-1) && energy >= targetRestingEnergy) {
            return true;
        }

        return false;
    }

    /**
     * Returns the subset of plays which can complete the given level ending
     * with the target resting energy
     * @param level is not null and not empty
     * @param possiblePlays is not null
     * @param startingEnergy the energy at the start of the level
     * @param targetRestingEnergy the minimum energy to end the level at
     * @return a subset of {@code possiblePlays} which complete the level with
     * {@code targetRestingEnergy} units of energy remaining
     */
    public static Set<String> successfulPlays(String level, Set<String> possiblePlays,
                                              int startingEnergy, int targetRestingEnergy) {

        Iterator<String> possiblePlaysIterator = possiblePlays.iterator();
        Set<String> successfulPlays = new HashSet<>();

        while(possiblePlaysIterator.hasNext()) {
            String testPlay = possiblePlaysIterator.next();
            if (isSuccessfulPlayEnergy(level, testPlay, startingEnergy, targetRestingEnergy)) {
                successfulPlays.add(testPlay);
            }
        }

        return successfulPlays;
    }

    /**
     * Returns the shortest play that completes the given level
     * @param level is not null and not empty
     * @param startingEnergy the energy at the start of the level
     * @param targetRestingEnergy the minimum energy to end the level at
     * @return the shortest play that allows a player to complete the given level
     * @throws UnplayableLevelException if no play can complete the level
     */
    public static String shortestPlay(String level, int startingEnergy, int targetRestingEnergy)
            throws UnplayableLevelException {

        return getPlays(level, startingEnergy, targetRestingEnergy, 0);
    }

    private static String getPlays (String level, int startingEnergy, int targetRestingEnergy, int position) {
        if (position >= level.length()-1) {
            return "";
        } else if (position == level.length()-1) {
            if (startingEnergy < targetRestingEnergy) {
                return "X";
            } else {
                return "";
            }
        }

        if (level.charAt(position) == '*') {
            position += 4;
        }

        String p0 = "";
        String p1 = "";
        String p2 = "";
        String p3 = "";

        char pos1 = level.charAt(position + 1);
        char pos2 = level.charAt(position + 2);
        char pos3 = level.charAt(position + 3);

        if (startingEnergy > 3) {
            p0 = "0".concat(getPlays(level, startingEnergy + 1, targetRestingEnergy, position));
        }
        if ((pos1 == '_' || pos1 == '*') && startingEnergy >= 1) {
            p1 = "1".concat(getPlays(level, startingEnergy-1, targetRestingEnergy, position+1));
        }
        if ((pos2 == '_' || pos2 == '*') && startingEnergy >= 2) {
            p2 = "2".concat(getPlays(level, startingEnergy-2, targetRestingEnergy, position+2));
        }
        if ((pos3 == '_' || pos3 == '*') && startingEnergy >= 3) {
            p3 = "3".concat(getPlays(level, startingEnergy-3, targetRestingEnergy, position+3));
        }

        if (p0.length() <= p1.length() && p0.length() <= p2.length() && p0.length() <= p3.length()) {
            return p0;
        }
        else if (p1.length() <= p0.length() && (p1.length() <= p2.length()) && p1.length() <= p3.length()) {
            return p1;
        } else if (p2.length() <= p0.length() && p2.length() <= p1.length() && p2.length() <= p3.length()) {
            return p2;
        } else {
            return p3;
        }

    }

    /**
     * Returns the total number of plays which allow a player to complete the given level
     * @param level is not null and not empty
     * @param startingEnergy the energy at the start of the level
     * @param targetRestingEnergy the minimum energy to end the level at
     * @return the total number of plays which allow a player to complete the given level
     * with target resting energy {@code targetRestingEnergy}
     */
    public static int numberOfPlays(String level, int startingEnergy, int targetRestingEnergy) {
        // TODO: Implement this method
        return -1;
    }
}
