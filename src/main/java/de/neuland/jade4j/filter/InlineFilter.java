package de.neuland.jade4j.filter;

import java.io.IOException;
import java.util.Map;

import de.neuland.jade4j.lexer.Scanner;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.TemplateLoader;

public class InlineFilter implements Filter {
    
    private TemplateLoader templateLoader;
    private Scanner scanner;
    
    public InlineFilter()
    {
        templateLoader = new FileTemplateLoader("", "UTF-8");
    }
    
    public InlineFilter( TemplateLoader templateLoader)
    {
        this.templateLoader = templateLoader;
    }
    
    public void setTemplateLoader( TemplateLoader templateLoader )
    {
        this.templateLoader = templateLoader;
    }

	@Override
	public String convert(String source, Map<String, Object> attributes, Map<String, Object> model) {
        try
        {
            scanner = new Scanner(templateLoader.getReader( source ));
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        String inline = scanner.getInput();
		return  inline ;
	}

}
