package controllers;

import java.util.List;
import play.Logger;
import play.mvc.*;
import models.BigEntity;

public class BigEntityController extends Controller {
    
    public static void all(){
	renderJSON(BigEntity.findAll());
    }

    public static void value01(Integer v01){
	System.out.println(v01);
	renderJSON(BigEntity.findByValue01(v01));
    }
    public static void example(BigEntity e){
	e.cleansing();
	//renderJSON(BigEntity.findByExample(e));
	List<BigEntity> all = BigEntity.findAll();
	List<BigEntity> result = BigEntity.findByExample(e);
	
	BigEntity q = BigEntity.clone(e);
	render(all, result, q);
    }
}
