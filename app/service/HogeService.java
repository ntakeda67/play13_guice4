package service;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import play.Logger;

import log.HogeInterface;
import binding.AnotherHoge;

public class HogeService {
    @Inject
    private HogeInterface hoge;
    
    @Inject @Named("Another")
    private HogeInterface anotherHoge;

    public String getHogeName(){
	return hoge.name();
    }

    public String getAnotherHogeName(){
	return anotherHoge.name();
    }
}
