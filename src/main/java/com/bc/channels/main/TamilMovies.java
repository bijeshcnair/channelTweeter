package com.bc.channels.main;

public class TamilMovies extends ChannelBase {
	
	public TamilMovies() {
		// TODO Auto-generated method stub
		super();
		init();

	}
	
	@Override
	public void init(){
		channelList.add("sun-tv");
		twitterHandle.put("sun-tv", "@SunTv");
		channelList.add("jaya-movie");
		//twitterHandle.put("jaya-movie", value)
		
		channelList.add("jaya-tv");		
		channelList.add("ktv");
		//twitterHandle.put("asianet-movies", "Asianet Movies");
		
		channelList.add("raj-tv");
		channelList.add("star-vijay");
		
		channelList.add("polimer-tv");
		//twitterHandle.put("amrita-tv", "@AmritaTv");
		
		channelList.add("kalaignar-tv");
		//twitterHandle.put("kairali-tv", "@TheKairaliTV");
		channelList.add("gemini-movies");
		
		this.cosumerKey = "9EKMUFafLOCp3oO4cbPiw";
		this.consumerSecret = "3LbVEzSzrC9DqHx96aAjw9FqMsBBzkGoGaDQNsSz7Zk";
		this.oauthAccessToken = "2319175338-gEZxGLI3gSqqFEaKKRkPLeGAys2HOlq2gCUU7qh";
		this.oauthAccessTokenSecret = "c7eWYlBQqPak6i9eaxblMHLKSlAasrjNfn3pznZ6x0Thq";
		this.programType = "Movie";
		super.init();
				
	}
}
