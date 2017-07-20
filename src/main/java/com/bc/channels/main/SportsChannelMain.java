package com.bc.channels.main;


public class SportsChannelMain extends ChannelBase {

	public SportsChannelMain() {
		super();
		//updateData(new Date());
		init();

	}
	
	@Override
	public void init(){
		channelList.add("ten-sports");
		//channelList.add("star-sports");
		channelList.add("neo-sports");
		channelList.add("ten-action-plus");
		channelList.add("espn");
		
		this.cosumerKey = "3YX4IFQO6XPNLPxUtELH8Q";
		this.consumerSecret = "vn4GyERTXKfXPLAbRVZntG20x36eQLJ3agWTdlhOM";
		this.oauthAccessToken = "2222815861-NOXHEWFYTHfFNv4D24VOrLQ5hQhZzGySnzMlaco";
		this.oauthAccessTokenSecret = "pqft0Ro56r1S16qxYvZDmeD3NgHD047JP6pg2x3LJ0Hb8";
		this.programType = "Sports";
		super.init();
				
	}
	
	
}
