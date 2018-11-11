package com.douliao.model.response;

import lombok.Data;

@Data
public class CreateChannel {
	private int userId;
	private String ChannelKey;
	private int channel;
	
}
