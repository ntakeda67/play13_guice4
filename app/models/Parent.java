package models;

import play.db.jpa.Model;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Parent extends Model {
    public String name;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    public Set<Child> children;

    public static Parent newParent(String name){
	Parent p = new Parent();

	p.name = name;
	p.children = new HashSet<>();
	
	return p;
    }

    private Parent(){}
}
