package views.FXML.items;

import javafx.util.StringConverter;
import models.list.ChauffeurList;

public class ChauffeurConverter extends StringConverter<ChauffeurList>
{
    // Method to convert a Person-Object to a String
    @Override
    public String toString(ChauffeurList person)
    {
        return person == null? null : person.getFullName();
    }
    // Method to convert a String to a Person-Object
    @Override
    public ChauffeurList fromString(String string)
    {
        return null;
    }
}

