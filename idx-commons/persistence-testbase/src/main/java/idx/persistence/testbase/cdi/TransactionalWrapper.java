package idx.persistence.testbase.cdi;

import javax.transaction.Transactional;

public class TransactionalWrapper {

    @Transactional
    public void execute(CheckedRunnable runnable) throws Exception {
        try {
            runnable.run();
        } catch (Exception ex) {
            throw ex;
        }
    }


    @FunctionalInterface
    public static interface CheckedRunnable {
        void run() throws Exception;
    }
}
