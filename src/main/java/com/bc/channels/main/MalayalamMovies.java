package com.bc.channels.main;


public class MalayalamMovies extends ChannelBase {

	public MalayalamMovies() {
		super();
		//updateData(new Date());
		init();

	}
	
	@Override
	public void init(){
		channelList.add("asianet");
		twitterHandle.put("asianet", "@asianet");
		channelList.add("kochu-tv");
		
		channelList.add("asianet-plus");
		
		channelList.add("asianet-movies");
		twitterHandle.put("asianet-movies", "Asianet Movies");
		
		channelList.add("surya-tv");
		twitterHandle.put("surya-tv", "@suryatv");
		
		channelList.add("kiran-tv");
		channelList.add("amrita-tv");
		twitterHandle.put("amrita-tv", "@AmritaTv");
		
		channelList.add("kairali-tv");
		twitterHandle.put("kairali-tv", "@TheKairaliTV");
		
		channelList.add("kairali-people-tv");
		twitterHandle.put("kairali-people-tv", "Kairali People");
		
		channelList.add("kairali-people-tv");
		
		
		this.cosumerKey = "GTUlSLmwwJF7agDrY7GGuQ";
		this.consumerSecret = "0In56BzhM1MtFIK0eZikkA6K9nsZsL3P2LvxngoTvrI";
		this.oauthAccessToken = "2219461088-jiabyv7wV6vNDNB7lbbH17WWKaoiGXgOSbopPfz";
		this.oauthAccessTokenSecret = "02o4fXTzsMKQpnYyBHR57emMQXroibmD4oSXCIabsPRg7";
		this.programType = "Movie";
		super.init();
				
	}
	
	
}
