package com.capgemini.user.service.util;

import java.util.HashMap;
import java.util.Map;

public class MetadataHeaderThreadLocalHolder {
	
	public static enum MetadataHeaders {
		
		X_CORRELATION_ID("X-Correlation-Id"), X_MESSAGE_ID("X-Message-Id"), X_SYSTEM_ID("X-System-Id");
		
		private String headerName;
		
		private MetadataHeaders(String headerName){
			this.headerName = headerName;
		}
		
		public String getMetadataHeaderName(){
			return this.headerName;
		}
		
		public MetadataHeaders fromString(String metadataHeader){
			if(metadataHeader!=null && !metadataHeader.isEmpty()){
				for(MetadataHeaders metadataItem:MetadataHeaders.values()){
					if(metadataItem.getMetadataHeaderName().equals(metadataHeader)){
						return metadataItem;
					}
				}
			}
			return null;
		}
		
	}
	
	private static final ThreadLocal<Map<MetadataHeaders, String>> metadataHeaderHolder = new ThreadLocal<Map<MetadataHeaders, String>>(){
		protected java.util.Map<MetadataHeaders,String> initialValue() {
			return new HashMap<>();
		};
	};
	
	protected static Map<MetadataHeaders, String> metadataHolderMap(){
		return metadataHeaderHolder.get();
	}
	
	public static String getMetadatHeaderFromThreadLocal(MetadataHeaders metadataHeader){
		return metadataHolderMap().get(metadataHeader);
	}
	
	public static void putMetadatHeaderToThreadLocal(MetadataHeaders metadataHeader, String metadataHeaderValue){
		metadataHolderMap().put(metadataHeader, metadataHeaderValue);
	}
	
	public static void removeMetadataHeaderFromThreadLocal(MetadataHeaders metadataHeader){
		metadataHolderMap().remove(metadataHeader);
	}
	
	public static void cllearMetadataHeaderFromThreadLocal(){
		metadataHolderMap().clear();
	}

}
