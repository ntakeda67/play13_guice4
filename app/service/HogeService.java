package service;

import com.google.inject.Inject;

import log.HogeInterface;
import binding.AnotherHoge;

public class HogeService {
    private HogeInterface hoge;
    private HogeInterface anotherHoge;

    @Inject
    public HogeService(HogeInterface hoge, @AnotherHoge HogeInterface anotherHoge){
	this.hoge = hoge;
	this.anotherHoge = anotherHoge;
    }

    public String getHogeName(){
	return hoge.name();
    }

    public String getAnotherHogeName(){
	return anotherHoge.name();
    }
}
