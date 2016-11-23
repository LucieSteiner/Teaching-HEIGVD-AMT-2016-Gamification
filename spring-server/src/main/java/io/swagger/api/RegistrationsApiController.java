package io.swagger.api;

import io.swagger.model.RegistrationSummary;
import io.swagger.model.Registration;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-23T02:44:10.683Z")

@Controller
public class RegistrationsApiController implements RegistrationsApi {

    public ResponseEntity<List<RegistrationSummary>> registrationsGet() {
        // do some magic!
        return new ResponseEntity<List<RegistrationSummary>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> registrationsPost(@ApiParam(value = "The info required to register an application" ,required=true ) @RequestBody Registration registration) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
