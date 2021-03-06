package org.jboss.edg.examples.carmartsingle.jsf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.jboss.edg.examples.carmartsingle.model.Car.Country;
import org.jboss.seam.faces.validation.InputField;

/**
 * 
 * @author Martin Gencur
 *
 */
@FacesValidator("carValidator")
public class CarValidator implements Validator
{
   private static Pattern czPattern1 = Pattern.compile("\\d[a-zA-Z]\\d\\s\\d{4}"); // e.g. "1B1 1216"
   private static Pattern czPattern2 = Pattern.compile("[a-zA-Z]{3}\\s\\d{2}-\\d{2}"); // e.g. "FML 24-27"
   
   @Inject
   @InputField
   private String numberPlate;
   
   @Inject
   @InputField
   private Country country;
   
   @Override
   public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
      String outcome = validateNumberPlate(country, numberPlate);
      if (outcome != null) {
         throw new ValidatorException(new FacesMessage(outcome));
      }
   }

   private String validateNumberPlate(Country country, String numberPlate) {
      if (country.equals(Country.CZECH_REPUBLIC)) {
         Matcher m1 = czPattern1.matcher(numberPlate);
         Matcher m2 = czPattern2.matcher(numberPlate);
         if (!(m1.matches() || m2.matches())) {
            return "You must enter the car's number in a valid pattern (e.g. FML 23-65 or 1B2 6565)"; 
         }
      }
      return null; //pattern OK -> return null
   }
}
