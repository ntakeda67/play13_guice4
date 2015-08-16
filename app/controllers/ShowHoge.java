package controllers;

import play.*;
import play.mvc.*;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Guice;

import log.HogeInterface;
import binding.HogeModule;
import binding.AnotherHoge;
import service.HogeService;

public class ShowHoge extends Controller {

    public static void index() {
	Injector injector = Guice.createInjector(new HogeModule());
	HogeService service = injector.getInstance(HogeService.class);
	String hogeIs = service.getHogeName();
	String anotherHogeIs = service.getAnotherHogeName();
	render(hogeIs, anotherHogeIs);
    }
}
