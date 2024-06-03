package models;


import database.HibernateManager;
import utils.Checker;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@ManagedBean(name = "attempts", eager = true)
@SessionScoped
public class CollectionAttemptsBean implements Serializable {
    //нужно использовать потокобезопасные коллекции,
    // потому что взаимодействия с коллекциями могут происходить в разных потоках.
    private final CopyOnWriteArrayList<Attempt> attempts;
    private final HibernateManager hibernateManager;

    private Attempt currentAttempt = new Attempt("0", "0", "3");

    public CollectionAttemptsBean() {
        hibernateManager = new HibernateManager();
        // чтобы он работал, не забывать прокидывать порт!!!

        attempts = new CopyOnWriteArrayList<>();
    }

    public void add(Attempt attempt) {
        attempts.add(attempt);

        hibernateManager.addAttempt(attempt);
    }

    public void clear() {
        attempts.clear();

        hibernateManager.clearAttempts();
    }

    public void attemptsClear(){
        attempts.clear();
    }

    public List<Attempt> getAttempts() {
        return hibernateManager.getAttempts();
    }

    public void addFromForm() {
            currentAttempt.updateIsHIt();
            add(currentAttempt);
            currentAttempt = new Attempt(currentAttempt.getX(),
                    currentAttempt.getY(), currentAttempt.getR());
    }

    public void addFromCanvas() {
        String strX = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("x");
        String strY = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("y");
        String strR = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("r");

        Attempt attempt = new Attempt(strX, strY, strR);

        add(attempt);
    }

    public Attempt getCurrentAttempt() {
        return currentAttempt;
    }

    public void setCurrentAttempt(Attempt currentAttempt) {
        this.currentAttempt = currentAttempt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionAttemptsBean that = (CollectionAttemptsBean) o;
        return Objects.equals(attempts, that.attempts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempts);
    }

    @Override
    public String toString() {
        return "CollectionAttempts{" +
                "attempts=" + attempts +
                '}';
    }
}

//package models;
//
//import org.junit.Test;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//import static org.junit.Assert.*;
//
//public class CollectionAttemptsBeanTest {
//
//    @Test
//    public void clearTest(){
//        List<Attempt> expected = new CopyOnWriteArrayList<>();
//
//        CollectionAttemptsBean bean = new CollectionAttemptsBean();
//        bean.clear();
//
//        List<Attempt> actual = bean.getAttempts();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void normTest(){
//        CollectionAttemptsBean bean = new CollectionAttemptsBean();
//        Attempt attempt1 = new Attempt(3, 3, 2);
//        Attempt attempt2 = new Attempt(4, -1.1, 2.5);
//        Attempt attempt3 = new Attempt(0.01, -2, 1);
//
//        bean.clear();
//        bean.add(attempt1);
//        bean.add(attempt2);
//        bean.add(attempt3);
//
//        List<Attempt> expectedAttempts = new CopyOnWriteArrayList<>();
//        expectedAttempts.add(attempt1);
//        expectedAttempts.add(attempt2);
//        expectedAttempts.add(attempt3);
//
//        List<Attempt> actualAttempts = bean.getAttempts();
//
//        assertEquals(expectedAttempts, actualAttempts);
//        bean.clear();
//    }
//}