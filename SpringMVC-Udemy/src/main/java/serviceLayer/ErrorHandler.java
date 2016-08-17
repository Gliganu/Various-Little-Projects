package serviceLayer;

import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
	
	private static String DEFAULT_ERROR_PAGE="error";
	private static String ACCESS_DENIED_PAGE="accessDenied";
	
	
	
	@ExceptionHandler(AccessDeniedException.class) 
	public String handleAccessDeniedException(Exception ex){
		ex.printStackTrace();
		return ACCESS_DENIED_PAGE;
	}
	
	@ExceptionHandler(DataAccessException.class) 
	public String handleDatabaseException(DataAccessException ex){
		ex.printStackTrace();
			return DEFAULT_ERROR_PAGE;
	}
	
}
