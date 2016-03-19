package br.uvv.wscarona.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseWebService {
	protected Gson gson;

	public BaseWebService() {
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		this.gson = builder.create();
	}

}
