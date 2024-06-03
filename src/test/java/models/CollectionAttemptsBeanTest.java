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