/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationtest.support;

/**
 *
 * @author Balázs
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
