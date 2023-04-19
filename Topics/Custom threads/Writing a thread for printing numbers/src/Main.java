import java.util.Scanner;

class NumbersThread extends Thread {
    private int from;
    private int to;
    public NumbersThread(int from, int to) {
        // implement the constructor
        this.from = from;
        this.to = to;

    }

    // you should override some method here
    @Override
    public void run() {
        for(int i = from; i <= to; i++) {
            System.out.println(i);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int from = scanner.nextInt();
        int to = scanner.nextInt();
        NumbersThread numbersThread = new NumbersThread(from, to);
        numbersThread.start();

    }
}