package de.neuland.jade4j.filter;

import java.io.IOException;
import java.util.Map;

import de.neuland.jade4j.TestFileHelper;
import de.neuland.jade4j.compiler.Compiler;
import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.lexer.Scanner;
import de.neuland.jade4j.model.JadeModel;
import de.neuland.jade4j.parser.Parser;
import de.neuland.jade4j.parser.node.Node;
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
        Parser parser = null;
        try {
            parser = new Parser(source, this.templateLoader, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Node root = parser.parse();
        Compiler compiler = new Compiler(root);
        compiler.setPrettyPrint( true );
        JadeModel jmodel = new JadeModel(model);
        jmodel.addFilter("plain", new PlainFilter());
        jmodel.addFilter("inline", new InlineFilter(templateLoader));

        String html = null;
        try {
            html = compiler.compileToString(jmodel);
        } catch (JadeCompilerException e) {
            e.printStackTrace();
        }
        return html;
	    
//	    try
//        {
//            scanner = new Scanner(templateLoader.getReader( source ));
//        }
//        catch ( IOException e )
//        {
//            e.printStackTrace();
//        }
//        String inline = scanner.getInput();
//		return  inline ;
        
	}

}
