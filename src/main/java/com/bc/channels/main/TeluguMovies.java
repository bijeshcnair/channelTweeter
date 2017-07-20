package com.bc.channels.main;

import java.util.HashMap;
import java.util.Map;

public class TeluguMovies extends ChannelBase{
	
	public Map<String, String> twitterHandleMap = new HashMap<String, String>();
	public TeluguMovies(){
		
		super();
		//updateData(new Date());
		init();
	}
	@Override
	public void init(){
		
		channelList.add("gemini-movies");
		twitterHandle.put("gemini-movies","@GeminiMovies");
		
		channelList.add("maa-movies");
		channelList.add("maa-tv");
		twitterHandle.put("maa-tv","@MAATV");
		channelList.add("maa-gold");
		channelList.add("zee-telugu");
		twitterHandle.put("zee-telugu","@ZeeTVTelugu");
		
		channelList.add("sakshi-tv");
		twitterHandle.put("zee-telugu","@SakshiHDTV");
		
		
		this.cosumerKey = "0ZcqEAlf4OrA4THDOL87tUfUn";
		this.consumerSecret = "9PYx63FUnWjggiR38wgajC1sNnJKse14aFfm1fyNMQjVo0QNIV";
		this.oauthAccessToken = "3320388432-TD94H9BdpOGgFol9jD3enOGbwMuIcRmwE6DjnfE";
		this.oauthAccessTokenSecret = "Eu25hjbk1n0QyLSX31iF0zkamygEsE3fYeJF8JlAJD1tg";
		this.programType = "Movie";
		
		super.init();
	}
}
