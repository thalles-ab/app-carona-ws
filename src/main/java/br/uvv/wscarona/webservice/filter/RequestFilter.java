package br.uvv.wscarona.webservice.filter;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

import br.uvv.wscarona.dao.LoginDAO;
import br.uvv.wscarona.webservice.BaseWebService;
import br.uvv.wscarona.webservice.util.ListMessageException;
import br.uvv.wscarona.webservice.util.MessageBundle;
import br.uvv.wscarona.webservice.util.MessageException;

@PreMatching
@RequestScoped 
public class RequestFilter implements ContainerRequestFilter{
	private static final String AUTHENTICATION_HEADER = "Authorization";
	private static final String LANGUAGE_HEADER = "Accept-Language";
	private static final String URL_LOGIN = "login";
	@Inject private LoginDAO loginDAO;
	
	@Override
	public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
		UriInfo uriInfo = containerRequest.getUriInfo();
		String fullUri = uriInfo.getAbsolutePath().toString();
		String baseUri = uriInfo.getBaseUri().toString();
		Gson gson = BaseWebService.getGsonInstance();
		
		String locate = containerRequest.getHeaderString(LANGUAGE_HEADER);
		String[] language = new String[]{"pt", "BR"};
		
		if(locate != null){
			language = locate.split("-");
		}
		
		// DEFINE LANGUAGE PROPERTIES
		MessageBundle.locale = new Locale(language[0], language[1]);
		ResourceBundle resourceBundle = MessageBundle.getMessagesInstace();
		Status status = null;
		
		ListMessageException list = new ListMessageException();
		Response response = null;
		
		// CHECK TOKEN AUTHORIZATION
		String token = containerRequest.getHeaderString(AUTHENTICATION_HEADER);
		if((fullUri.replace(baseUri, "").equals(URL_LOGIN) && containerRequest.getMethod().equals(HttpMethod.POST)) ||
				(fullUri.replace(baseUri, "").equals("student") && containerRequest.getMethod().equals(HttpMethod.POST))){
			// TODO
		}else if(StringUtils.isNullOrEmpty(token)){
			list.getErros().add(new MessageException(resourceBundle.getString("error.no.token")));
			status = Status.FORBIDDEN; 
		}else{
			try {
				BaseWebService.setStudentContext(loginDAO.validateToken(token));
			} catch (ListMessageException e) {
				list = e;
				status = Status.FORBIDDEN;
			}
		}
		
		if(list.getErros().isEmpty() == false){
			response = Response.status(status)
					.type(MediaType.APPLICATION_JSON)
					.entity(gson.toJson(list)).build();
			throw new WebApplicationException(response);
		}
	}

}
