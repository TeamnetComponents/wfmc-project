package org.wfmc.elo.utils;

import de.elo.ix.client.UserTask;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by andras on 3/5/2015.
 */
public class EloToWfMCObjectConverterTest {

    EloToWfMCObjectConverter eloToWfMCObjectConverter;
    UserTask[] userTasks;

    @Before
    public void setUp(){
        eloToWfMCObjectConverter = new EloToWfMCObjectConverter();
        userTasks = new UserTask[2];
        UserTask userTask = Mockito.mock(UserTask.class);
        //Mockito.stub(userTask)
    }


    @Test
    public void test_convertWFNodesToWMWorkItems_(){

        //eloToWfMCObjectConverter.convertUserTasksToWMWorkItems();
    }

}
