package javathreads3.examples.ch06;

public class DeadlockDetectedException extends RuntimeException {

    public DeadlockDetectedException(String s) {
	super(s);
    }
}
