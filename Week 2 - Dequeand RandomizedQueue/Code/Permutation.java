/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int number = Integer.parseInt(args[0]);
        RandomizedQueue<String> input = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            input.enqueue(StdIn.readString());
        }
        for (int i = 0; i < number; i++) {
            StdOut.printf("%s \n", input.dequeue());
        }

    }
}
