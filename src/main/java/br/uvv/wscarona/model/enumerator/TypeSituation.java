package br.uvv.wscarona.model.enumerator;

import com.google.gson.annotations.SerializedName;

public enum TypeSituation {
	ENABLE,
	DISABLE,
	PENDING;

	public static TypeSituation value(Integer item){
		TypeSituation[] values = TypeSituation.values();
		return values[item.intValue()];
	}
}
