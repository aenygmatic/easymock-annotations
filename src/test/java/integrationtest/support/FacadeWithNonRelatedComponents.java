package integrationtest.support;

/**
 * Dummy class for support integration testing.
 * <p>
 * @author Balazs Berkes
 */
public class FacadeWithNonRelatedComponents {

    private ThridLevelClassA component1;
    private IndependentObject component2;

    public ThridLevelClassA getThirdLevelClassA() {
        return component1;
    }

    public IndependentObject getIndependentObject() {
        return component2;
    }
}
