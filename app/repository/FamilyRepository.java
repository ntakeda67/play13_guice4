package repository;

import models.Parent;
import models.Child;
import java.util.List;

public class FamilyRepository {
    public static List<Parent> findAllParents(){
	return Parent.findAll();
    }

    public static void addParent(String parentName){
	Parent.newParent(parentName).save();
    }

    public static void addParent(String parentName, Integer numOfChild){
	Parent p = Parent.newParent(parentName);
	bornChildren(p, numOfChild);
	p.save();
    }

    public static void bornChildren(Parent parent, Integer numOfChild){
	for(int i=0; i< numOfChild; i++){
	    bornChild(parent);
	}
    }
	
    public static void bornChild(Parent parent){
	parent.children.add(Child.newChild(parent, parent.name + "'s child"));
    }
    
}
