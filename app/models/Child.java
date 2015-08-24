package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import play.db.jpa.Model;

@Entity
public class Child extends Model {
    public String name;

    @ManyToOne
    @NotNull
    public Parent parent;

    public static Child newChild(Parent parent, String name){
	Child c = new Child();

	c.name = name;
	c.parent = parent;
	return c;
    }


    private Child(){}
}
