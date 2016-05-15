package br.uvv.wscarona.webservice;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import br.uvv.wscarona.dao.StudentDAO;
import br.uvv.wscarona.model.Place;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.webservice.util.AuthenticatorUtil;
import br.uvv.wscarona.webservice.util.ListMessageException;
import com.mysql.jdbc.StringUtils;

@Path("/student")
@RequestScoped
public class StudentWebService extends BaseWebService {
	@Inject
	private StudentDAO studentDAO;

    @GET
    public Response getStudent() throws ListMessageException {
        return successRequest(BaseWebService.getStudentContext());
    }

    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") String id) throws ListMessageException {
        try{
            Long idLong = Long.parseLong(id);
            Student student = studentDAO.getStudent(idLong);
            return successRequest(student);
        }
        catch (Exception e){
            return badRequest(erros);
        }
    }

	@PUT
	public Response save(String json) {
        Student user = this.gson.fromJson(json, Student.class);
        Student contextUser = StudentWebService.studentContext;
        try {
            contextUser.setPhoto(user.getPhoto());
            contextUser.setName(user.getName());
            contextUser.setEmail(user.getEmail());
            contextUser.setCellPhone(user.getCellPhone());
            studentDAO.saveOrUpdate(contextUser);
            return successRequest();
        } catch (ListMessageException list) {
            return badRequest(list);
        }
    }


	@POST
    public Response create(String json){
        Student user = this.gson.fromJson(json, Student.class);
        user.setId(0L);
        try{
            user.setPassword(AuthenticatorUtil.getPassword(user.getPassword()));
            studentDAO.saveOrUpdate(user);
            return successRequest();
        }
        catch (ListMessageException list){
            return badRequest(list);
        } catch (NoSuchAlgorithmException e) {
            return badRequest();
        } catch (UnsupportedEncodingException e) {
            return badRequest();
        }
    }

	@POST
	@Path("/lostPassword")
	public Response lostPassword() {
		try{
			studentDAO.lostPassword(BaseWebService.studentContext);
			return successRequest();
		}catch(ListMessageException list){
			return badRequest(list);
		}
	}
}
