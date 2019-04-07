package controllers.Validation;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@SessionScoped
@FacesValidator("ScriptValidator")
public class ScriptValidacija implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String poruka = "";

        if (value.toString().toLowerCase().contains("<script>") || value.toString().toLowerCase().contains("&lt;script&gt;")) {
            poruka = "Taj film nesh gledat!";

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            throw new ValidatorException(msg);
        } 
    }

}
