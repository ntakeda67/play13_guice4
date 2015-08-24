package controllers;

import java.util.List;

import play.Logger;
import play.mvc.*;

import models.Parent;
import repository.FamilyRepository;

public class FamilyController extends Controller {
    
    public static void index(){
	List<Parent> parentList = FamilyRepository.findAllParents();
	render(parentList);
    }

    public static void add(String parentName, Integer numOfChild){
	FamilyRepository.addParent(parentName, numOfChild);
    }
}
