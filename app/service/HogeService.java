package service;

import com.google.inject.Inject;

import log.HogeInterface;
import binding.AnotherHoge;

public class HogeService {
    @Inject
    private HogeInterface hoge;
    @Inject @AnotherHoge
    private HogeInterface anotherHoge;

    public String getHogeName(){
	return hoge.name();
    }

    public String getAnotherHogeName(){
	return anotherHoge.name();
    }
}
