// Using Java, and threads in Java, program a solution to the Dining Philosophers problem, a classic problem in concurrent computation. 
// Use judicious output to show what is going on in your simulation as it executes. 
// Make sure your solution avoids deadlocks, starvations, and race conditions.

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class DiningPhilosophers {

    private static final int N = 5;
    private static final Semaphore[] chopsticks = new Semaphore[N];

    public static void main(String[] args) {
        for (int i = 0; i < N; i++) {
            // Fair semaphores prevent starvation
            chopsticks[i] = new Semaphore(1, true);
        }

        for (int i = 0; i < N; i++) {
            new Thread(new Philosopher(i), "Philosopher-" + i).start();
        }
    }

    static class Philosopher implements Runnable {
        private final int id;
        private final int left;
        private final int right;

        Philosopher(int id) {
            this.id = id;
            this.left = id;
            this.right = (id + 1) % N;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();
                    eat();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void think() throws InterruptedException {
            System.out.println(id + " is thinking");
            TimeUnit.MILLISECONDS.sleep(500 + id * 100);
        }

        private void eat() throws InterruptedException {
            System.out.println(id + " wants to eat");

            // Break symmetry to avoid deadlock
            if (id % 2 == 0) {
                pickUp(left, "left");
                pickUp(right, "right");
            } else {
                pickUp(right, "right");
                pickUp(left, "left");
            }

            System.out.println(id + " is eating");
            TimeUnit.MILLISECONDS.sleep(800);

            putDown(left);
            putDown(right);
            System.out.println(id + " finished eating");
        }

        private void pickUp(int chopstickId, String side) throws InterruptedException {
            chopsticks[chopstickId].acquire();
            System.out.println(id + " picked up " + side + " chopstick");
        }

        private void putDown(int chopstickId) {
            chopsticks[chopstickId].release();
        }
    }
}
