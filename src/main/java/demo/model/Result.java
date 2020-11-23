package demo.model;

public class Result {
    private Decision decision;

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public Decision getDecision() {
        return decision;
    }

    public boolean isInit() {
        return decision != null;
    }
}
