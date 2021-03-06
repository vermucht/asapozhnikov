package ru.job4j.bomberman;

/**
 * Runnable class controlling random-moving personage.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version $Id$
 * @since 26.04.2018
 */
public class RunAutomatic implements Runnable {
    /**
     * Moving personage.
     */
    private Personage personage;
    /**
     * Flag for the thread to continue working.
     */
    private boolean working = true;

    /**
     * Constructs new Runnable random-moving object.
     *
     * @param personage personage to take control of.
     */
    public RunAutomatic(Personage personage) {
        this.personage = personage;
    }

    /**
     * Operations to do.
     */
    @Override
    public void run() {
        try {
            this.personage.place();
            System.out.format("+ %s placed to (%s, %s)%n", this.personage.name(), this.personage.x(), this.personage.y());
            while (working && !Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
                this.personage = this.personage.randomMove();
                System.out.format(">>> %s moved to (%s, %s)%n", this.personage.name(), this.personage.x(), this.personage.y());
                System.out.flush();
            }
        } catch (WrongCoordinatesException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.format("%s: thread interrupted, stopping.%n", this.personage.name());
            this.working = false;
        }
    }
}
