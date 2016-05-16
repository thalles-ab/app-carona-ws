package br.uvv.wscarona.webservice.util;

import com.google.gson.annotations.Expose;

/**
 * Created by luizguilhermepicorelli on 15/05/16.
 */
public class StatisticUtil {
    @Expose
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
