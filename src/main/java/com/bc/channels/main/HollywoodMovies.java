package com.bc.channels.main;

import java.util.HashMap;
import java.util.Map;


public class HollywoodMovies extends ChannelBase {
public Map<String, String> twitterHandleMap = new HashMap<String, String>();

	public HollywoodMovies() {
		super();
		//updateData(new Date());
		init();
		
	}
	
	@Override
	public void init(){
		/*channelList.add("star-movies-hd");
		twitterHandleMap.put("star-movies-hd", "Star Movies hd");
		*/
		channelList.add("axn");
		twitterHandle.put("axn", "@AXNIndia");
		
		channelList.add("star-movies");
		twitterHandle.put("star-movies", "@StarMoviesIndia");
		
		channelList.add("world-movies");
		twitterHandle.put("world-movies", "World Movies");
		
		channelList.add("movies-now");
		twitterHandle.put("movies-now", "@moviesnowtv");
		
		channelList.add("hbo");
		twitterHandle.put("hbo", "@HBOINDIA");
		
		channelList.add("sony-pix");
		twitterHandle.put("sony-pix", "@SonyPIX");
		
		channelList.add("zee-cafe");
		twitterHandleMap.put("zee-cafe", "@ZeeCafe");
		
		channelList.add("zee-studio");
		twitterHandleMap.put("zee-studio","@ZeeStudio");
		
	
		
		channelList.add("star-movies-action");
		twitterHandleMap.put("star-movies-action", "@StarMoviesAct");
		
		channelList.add("hbo-hits");
		twitterHandleMap.put("hbo-hits", "@HBOHITS");	
		
		channelList.add("hbo-defined");
		twitterHandleMap.put("hbo-defined","@hbo-defined");
		
		channelList.add("romedy-now");
		twitterHandleMap.put("romedy-now","@RomedyNow");
		
		channelList.add("mgm");
		
		
		
				
		this.cosumerKey = "890IAfYKAQawiw0CLzNSkQ";
		this.consumerSecret = "P1vEHK2fz63rzOufdWiMkrvx1AWo5o4bh4fdSculZQU";
		this.oauthAccessToken = "2267150058-ve0CeV3ZZ5P0EJxqhR8lAG1rOZOBRfcanvRiFkr";
		this.oauthAccessTokenSecret = "RaJmWNCViQlw1SgZRkcVdUB2YNfHcmBXeADNhLR9foDVP";
		this.programType = "Movie";
		
		super.init();
	}
	
	
}
