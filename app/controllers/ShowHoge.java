package controllers;

import play.*;
import play.mvc.*;

import com.google.inject.Injector;
import com.google.inject.Guice;

import log.HogeInterface;
import binding.HogeModule;

public class ShowHoge extends Controller {

    public static void index() {
	Injector injector = Guice.createInjector(new HogeModule());
	HogeInterface hoge = injector.getInstance(HogeInterface.class);
	String hogeIs = hoge.iam();
        render(hogeIs);
    }
}
