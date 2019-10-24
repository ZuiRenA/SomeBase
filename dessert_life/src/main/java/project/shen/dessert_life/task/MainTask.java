package project.shen.dessert_life.task;

public abstract class MainTask extends Task {
    @Override
    public boolean runOnMainThread() {
        return true;
    }
}
