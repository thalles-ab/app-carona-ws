package br.uvv.wscarona.webservice.util;

import com.google.gson.annotations.Expose;

/**
 * Created by luizguilhermepicorelli on 15/05/16.
 */
public class StatisticUtil {
    @Expose
    private Long amountTakenRides;
    @Expose
    private Long amountGivenRides;

    public Long getAmountTakenRides() {
        return amountTakenRides;
    }

    public void setAmountTakenRides(Long amountTakenRides) {
        this.amountTakenRides = amountTakenRides;
    }

    public Long getAmountGivenRides() {
        return amountGivenRides;
    }

    public void setAmountGivenRides(Long amountGivenRides) {
        this.amountGivenRides = amountGivenRides;
    }
}
