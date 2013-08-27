package de.neuland.jade4j.compiler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import de.neuland.jade4j.Jade4J;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.TestFileHelper;
import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.filter.CDATAFilter;
import de.neuland.jade4j.filter.InlineFilter;
import de.neuland.jade4j.filter.PlainFilter;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;

public class OriginalJadeTest {
   

	private String[] manualCompared = new String[] { "attrs", "attrs.js", "code.conditionals", "code.iteration", "comments",
			"escape-chars", "filters.coffeescript", "filters.less", "filters.markdown", "filters.stylus", "filters.cdata","html", "inline","include-only-text-body",
			"include-only-text", "include-with-text-head", "include-with-text", "mixin.blocks", "mixin.merge", "quotes", "script.whitespace", "scripts", "scripts.non-js",
			"source", "styles", "template", "text-block", "text", "vars", "yield-title", "doctype.default", "doctype.transitional" };
	
	//private String[] manualCompared = new String[] { "inline"};


	@Test
	public void test() throws IOException, JadeCompilerException {
		File folder = new File(TestFileHelper.getOriginalResourcePath(""));
		String defaultSeparator = "/";
		Collection<File> files = FileUtils.listFiles(folder, new String[] { "jade" }, false);

		JadeConfiguration jade = new JadeConfiguration();
		jade.setMode(Jade4J.Mode.XHTML); // original jade uses xhtml by default
		jade.setFilter("plain", new PlainFilter());
		jade.setFilter("cdata", new CDATAFilter());
		jade.setFilter("inline", new InlineFilter(new FileTemplateLoader(TestFileHelper.getOriginalResourcePath(""), "UTF-8")));

		for (File file : files) {
		    //if (!ArrayUtils.contains(manualCompared, file.getName().replace(".jade", ""))) continue;
		    // avoid on windows systems exception of type java.lang.IllegalArgumentException: Illegal character in opaque part at index 2... at java.net.URI.create 
			JadeTemplate template = jade.getTemplate(file.getPath().replaceAll( "\\"+ File.separator, defaultSeparator ));
			Writer writer = new StringWriter();
			jade.renderTemplate(template, new HashMap<String, Object>(), writer);
			String html = writer.toString();

			String expected = readFile(file.getPath().replace(".jade", ".html"));

			if (!ArrayUtils.contains(manualCompared, file.getName().replace(".jade", ""))) {
			    assertEquals(file.getName(), expected, html);
			} else {
			    // just compare the length as first approximation of correctness  
			    try {
			        assertEquals(file.getName(), expected.replaceAll( "\\s", "").length(), html.replaceAll( "\\s", "").length());
			    } catch (AssertionError e) {
			       stdOutput( file, html, expected );
			       throw e;
			    }
			}
		}
	}

    private void stdOutput( File file, String html, String expected )
    {
        	System.out.println("\nactual>> " + file.getName());
        	System.out.println(html);
        	System.out.println("expected-- " + file.getName());
        	System.out.println(expected);
        	System.out.println("<< " + file.getName());
        	System.out.println("cleaned::\n" + html.replaceAll( "\\s", "") + "\n" + expected.replaceAll( "\\s", ""));
        	System.out.println("length:: " + html.replaceAll( "\\s", "").length() + "<>" + expected.replaceAll( "\\s", "").length() );
        	System.out.println(":: " + expected.equals( html ));
    }

	private String readFile(String fileName) {
		try {
			return FileUtils.readFileToString(new File(fileName));
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return "";
	}
}
