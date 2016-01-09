package com.aws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MetadataRetrival {

	public static void main(String[] args) throws Exception {
		String metadataURLString="http://169.254.169.254/latest/meta-data/";
		URL metadataURL=new URL(metadataURLString);
		URLConnection metadataConnection=metadataURL.openConnection();
		List<String> values=new ArrayList<String>();
		try (
				InputStreamReader metadataInputStream=new InputStreamReader(metadataConnection.getInputStream());
				BufferedReader metadataListReader=new BufferedReader(metadataInputStream);
				) {
			String line="";
			while((line=metadataListReader.readLine())!=null) {
				line=line.trim();
				System.out.println(line);
				if(line.length()>0)
				values.add(line);
			}
		}catch( IOException ex ) {
			System.out.println("Error reading contents");
			ex.printStackTrace();
		}
		
		String currentTopic="public-ipv4";
		try(
				InputStreamReader consoleInputStream = new InputStreamReader(System.in);
				BufferedReader nextCommandReader = new BufferedReader(consoleInputStream);
				) {
		do {
			URL valueUrl=new URL(metadataURLString+currentTopic);
			URLConnection valueConnection=valueUrl.openConnection();
			InputStreamReader metadataInputStream = new InputStreamReader(valueConnection.getInputStream());
			BufferedReader metadataListReader = new BufferedReader(metadataInputStream);
			String line="";
			while((line=metadataListReader.readLine())!=null) {
				line=line.trim();
				System.out.println(currentTopic+" # "+line);
			}
			System.out.println("Enter next value to be printed or exit for exiting:");
			currentTopic = nextCommandReader.readLine();
		} while (currentTopic.length()>0&&!currentTopic.equals("exit"));
		}catch( IOException ex ) {
			System.out.println("Error reading contents");
			ex.printStackTrace();
		}
		System.out.println("Exiting... Bye");
	}

}
