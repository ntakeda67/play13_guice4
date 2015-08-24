import play.Logger;
import play.jobs.*;
import play.db.jpa.Transactional;

import models.BigEntity;

@OnApplicationStart
public class BootStrap extends Job {

    @Transactional(readOnly=false)
    public void doJob(){
	BigEntity.insertTestData("001", 1, 2, 3, 4, 5, "1", "2", "3", "4", "5");
	BigEntity.insertTestData("002", 2, 1, 3, 4, 5, "A", "B", "C", "D", "E");
	BigEntity.insertTestData("003", 2, 3, 1, 4, 5, "A", "B", "C", "D", "E");
	BigEntity.insertTestData("004", 2, 3, 4, 1, 5, "A", "B", "C", "D", "E");
	BigEntity.insertTestData("005", 2, 3, 4, 5, 1, "A", "B", "C", "D", "E");
    }
}
