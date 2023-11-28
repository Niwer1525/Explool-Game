package io;

public class Random {
    
    /**
     * Generate a random number between 0 and 9
     * @return a random number between 0 and 9
     */
    public static int randomize() {
        return (int) (Math.random() * 10);
    }

    /**
     * Generate a random number between 0 and max
     * @param max the maximum value
     * @return The randomly generated number
     */
    public static int randomize(int max) {
        return (int) (Math.random() * max);
    }

    /**
     * Generate a random number between min and max
     * @param min the minimum value
     * @param max the maximum value
     * @return The randomly generated number
     */
    public static int randomize(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
