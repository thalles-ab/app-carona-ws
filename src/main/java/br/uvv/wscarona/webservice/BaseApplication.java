package br.uvv.wscarona.webservice;

import br.uvv.wscarona.webservice.filter.RequestFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/api")
public class BaseApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(RequestFilter.class);
        classes.add(StudentWebService.class);
        classes.add(LoginWebService.class);
        classes.add(PlaceWebService.class);
        classes.add(RideWebService.class);
        classes.add(SolicitationRideWebService.class);
        classes.add(StudentRideWebService.class);
        classes.add(StatisticWebService.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        final Set<Object> singletons = new HashSet<Object>();
        return singletons;
    }

    @Override
    public Map<String, Object> getProperties() {
        final Map<String, Object> properties = new HashMap<String, Object>();
        return properties;
    }
    
}