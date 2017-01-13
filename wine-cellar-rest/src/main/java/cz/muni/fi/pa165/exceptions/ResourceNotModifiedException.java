package cz.muni.fi.pa165.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author MarekScholtz
 * @version 2016.12.15
 */
@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "The requested resource was not modified")
public class ResourceNotModifiedException extends RuntimeException {

} 
