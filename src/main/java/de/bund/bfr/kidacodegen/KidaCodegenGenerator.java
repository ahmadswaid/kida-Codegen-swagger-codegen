package de.bund.bfr.kidacodegen;

import static io.swagger.codegen.v3.CodegenConstants.HAS_ENUMS_EXT_NAME;
import static io.swagger.codegen.v3.CodegenConstants.IS_ENUM_EXT_NAME;
import static io.swagger.codegen.v3.generators.handlebars.ExtensionHelper.getBooleanValue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import io.swagger.codegen.v3.CliOption;
import io.swagger.codegen.v3.CodegenConstants;
import io.swagger.codegen.v3.CodegenModel;
import io.swagger.codegen.v3.CodegenObject;
import io.swagger.codegen.v3.CodegenProperty;
import io.swagger.codegen.v3.CodegenType;
import io.swagger.codegen.v3.SupportingFile;
import io.swagger.codegen.v3.generators.javascript.JavaScriptClientCodegen;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.Schema;

public class KidaCodegenGenerator extends JavaScriptClientCodegen {

  // source folder where to write the files
  protected String sourceFolder = "src";
  protected String apiVersion = "1.0.0";
  private static ObjectMapper objectMapper = new ObjectMapper();
  String appFiles[] = {"app.editable.eventObserver.js", "app.editable.mt.SelectForm.js", "app.landingpage.js", 	"app.schemas.js", 		"app.utils.js",
		  "app.editable.mt.ArrayForm.js", "app.editable.mt.SimpleTable.js", "app.modal.js", 		"app.simulation.js",
		  "app.editable.mt.Dialog.js", "app.editable.mt.TablePanel.js", "app.modal.mt-details.js", 	"app.table.js",
		  "app.editable.mt.FormPanel.js", "app.editable.mt.TextareaForm.js", "app.modal.mt-simulations.js", "app.table.mt.js",
		  "app.editable.mt.InputForm.js", "app.editable.mt.details.js", "app.mt.details.js", 	"app.ui.js"};
  /**
   * Configures the type of generator.
   * 
   * @return  the CodegenType for this generator
   * @see     io.swagger.codegen.CodegenType
   */
  public CodegenType getTag() {
    return CodegenType.CLIENT;
  }

  /**
   * Configures a friendly name for the generator.  This will be used by the generator
   * to select the library with the -l flag.
   * 
   * @return the friendly name for the generator
   */
  public String getName() {
    return "kida-Codegen";
  }

  /**
   * Returns human-friendly help for the generator.  Provide the consumer with help
   * tips, parameters here
   * 
   * @return A string value for the help message
   */
  public String getHelp() {
    return "Generates a kida-Codegen client library.";
  }

  public KidaCodegenGenerator() {
    super();

    // set the output folder here
    outputFolder = "generated-code/kida-Codegen";
    /**
     * Models.  You can write model files using the modelTemplateFiles map.
     * if you want to create one template for file, you can do so here.
     * for multiple files for model, just put another entry in the `modelTemplateFiles` with
     * a different extension
     */
    modelTemplateFiles.put(
      "model.mustache", // the template to use
      ".js");       // the extension for each file to write
    
    modelTemplateFiles.put(
    	      "modelview.mustache", // the template to use
    	      "View.js");       // the extension for each file to write
    
    for(String fileName : appFiles) {
	    supportingFiles.add(new SupportingFile("app/"+fileName,   // the input template or file
	    	      "/src/io/swagger/client/app",                                                       // the destination folder, relative `outputFolder`
	    	      fileName)                                          // the output file
	    	    );
    }
    /**
     * Api classes.  You can write classes for each Api file with the apiTemplateFiles map.
     * as with models, add multiple entries with different extensions for multiple files per
     * class
     */
    apiTemplateFiles.put(
      "api.mustache",   // the template to use
      ".js");       // the extension for each file to write
    
    /**
     * Template Location.  This is the location which templates will be read from.  The generator
     * will use the resource stream to attempt to read the templates.
     */
    templateDir = "kida-Codegen";

    /**
     * Api Package.  Optional, if needed, this can be used in templates
     */
    apiPackage = "io.swagger.client.api";

    /**
     * Model Package.  Optional, if needed, this can be used in templates
     */
    modelPackage = "io.swagger.client.model";

    
    /**
     * Supporting Files.  You can write single files for the generator with the
     * entire object tree available.  If the input file has a suffix of `.mustache
     * it will be processed by the template engine.  Otherwise, it will be copied
     */
    //supportingFiles.add(new SupportingFile("myFile.mustache",   // the input template or file
    //  "",                                                       // the destination folder, relative `outputFolder`
    //  "myFile.js")                                          // the output file
    //);
    cliOptions.clear();
    cliOptions.add(new CliOption(CodegenConstants.SOURCE_FOLDER, CodegenConstants.SOURCE_FOLDER_DESC).defaultValue("src"));
    cliOptions.add(new CliOption(CodegenConstants.LOCAL_VARIABLE_PREFIX, CodegenConstants.LOCAL_VARIABLE_PREFIX_DESC));
    cliOptions.add(new CliOption(CodegenConstants.INVOKER_PACKAGE, CodegenConstants.INVOKER_PACKAGE_DESC));
    cliOptions.add(new CliOption(CodegenConstants.API_PACKAGE, CodegenConstants.API_PACKAGE_DESC));
    cliOptions.add(new CliOption(CodegenConstants.MODEL_PACKAGE, CodegenConstants.MODEL_PACKAGE_DESC));
    cliOptions.add(new CliOption(PROJECT_NAME,
            "name of the project (Default: generated from info.title or \"swagger-js-client\")"));
    cliOptions.add(new CliOption(MODULE_NAME,
            "module name for AMD, Node or globals (Default: generated from <projectName>)"));
    cliOptions.add(new CliOption(PROJECT_DESCRIPTION,
            "description of the project (Default: using info.description or \"Client library of <projectName>\")"));
    cliOptions.add(new CliOption(PROJECT_VERSION,
            "version of the project (Default: using info.version or \"1.0.0\")"));
    cliOptions.add(new CliOption(CodegenConstants.LICENSE_NAME,
            "name of the license the project uses (Default: using info.license.name)"));
    cliOptions.add(new CliOption(USE_PROMISES,
            "use Promises as return values from the client API, instead of superagent callbacks")
            .defaultValue(Boolean.FALSE.toString()));
    cliOptions.add(new CliOption(EMIT_MODEL_METHODS,
            "generate getters and setters for model properties")
            .defaultValue(Boolean.TRUE.toString()));
    cliOptions.add(new CliOption(EMIT_JS_DOC,
            "generate JSDoc comments")
            .defaultValue(Boolean.TRUE.toString()));
    cliOptions.add(new CliOption(USE_INHERITANCE,
            "use JavaScript prototype chains & delegation for inheritance")
            .defaultValue(Boolean.TRUE.toString()));
    cliOptions.add(new CliOption(CodegenConstants.HIDE_GENERATION_TIMESTAMP, CodegenConstants.HIDE_GENERATION_TIMESTAMP_DESC)
            .defaultValue(Boolean.TRUE.toString()));
    
    //additionalProperties.containsKey(USE_ES6)
    cliOptions.add(new CliOption(CodegenConstants.MODEL_PROPERTY_NAMING, CodegenConstants.MODEL_PROPERTY_NAMING_DESC).defaultValue("camelCase"));
    cliOptions.add(CliOption.newBoolean(LOAD_TEST_DATA_FROM_FILE, "Load test data from a JSON file"));
    cliOptions.add(CliOption.newString(TEST_DATA_FILE, "JSON file to contain test data"));
    cliOptions.add(CliOption.newString(PRESERVE_LEADING_PARAM_CHAR, "Preserves leading $ and _ characters in parameter names."));
    cliOptions.add(new CliOption(USE_ES6,
            "use JavaScript ES6 (ECMAScript 6) (beta). Default is ES5.")
            .defaultValue(Boolean.TRUE.toString()));
    setUseES6(useES6);
   
  }
  @Override
  public void setUseES6(boolean useES6) {
      this.useES6 = useES6;
      if (useES6) {
          embeddedTemplateDir = templateDir = "kida-Codegen/es6";
          System.out.println("Using JS ES6 templates");
      } else {
          embeddedTemplateDir = templateDir = "Javascript";
          System.out.println("Using JS ES5 templates");
      }
  }
  /**
   * Escapes a reserved word as defined in the `reservedWords` array. Handle escaping
   * those terms here.  This logic is only called if a variable matches the reserved words
   * 
   * @return the escaped term
   */
  @Override
  public String escapeReservedWord(String name) {
    return "_" + name;  // add an underscore to the name
  }

  /**
   * Location to write model files.  You can use the modelPackage() as defined when the class is
   * instantiated
   */
  public String modelFileFolder() {
    return outputFolder + "/" + sourceFolder + "/" + modelPackage().replace('.', File.separatorChar);
  }

  /**
   * Location to write api files.  You can use the apiPackage() as defined when the class is
   * instantiated
   */
  @Override
  public String apiFileFolder() {
    return outputFolder + "/" + sourceFolder + "/" + apiPackage().replace('.', File.separatorChar);
  }

  @Override
  public String getArgumentsLocation() {
    return null;
  }

  @Override
  protected String getTemplateDir() {
    return templateDir;
  }

  @Override
  public String getDefaultTemplateDir() {
    return templateDir;
  }
  
  @Override
  public KidaCodegenModel fromModel(String name, Schema schema, Map<String, Schema> allSchemas) {
      CodegenModel codegenModel = super.fromModel(name, schema, allSchemas);
      
      boolean hasEnums = getBooleanValue(codegenModel, HAS_ENUMS_EXT_NAME);
      if (allSchemas != null && codegenModel != null && codegenModel.parent != null && hasEnums) {
          final Schema parentModel = allSchemas.get(codegenModel.parentSchema);
          final CodegenModel parentCodegenModel = super.fromModel(codegenModel.parent, parentModel, allSchemas);
          codegenModel = KidaCodegenGenerator.reconcileInlineEnums(codegenModel, parentCodegenModel);
      }
      Gson gson = new Gson();
      KidaCodegenModel kidaCodegenModel = gson.fromJson(gson.toJson(codegenModel), KidaCodegenModel.class);
      if (schema instanceof ArraySchema) {
          final Schema items = ((ArraySchema) schema).getItems();
          if (items != null) {
        	  kidaCodegenModel.vendorExtensions.put("x-isArray", true);
              kidaCodegenModel.vendorExtensions.put("x-itemType", getSchemaType(items));
          }

      } else if (schema instanceof MapSchema && hasSchemaProperties(schema)) {
          if (schema.getAdditionalProperties() != null) {
              kidaCodegenModel.vendorExtensions.put("x-isMap", true);
              kidaCodegenModel.vendorExtensions.put("x-itemType", getSchemaType((Schema) schema.getAdditionalProperties()));
          } else {
              String type = schema.getType();
              if (isPrimitiveType(type)){
                  kidaCodegenModel.vendorExtensions.put("x-isPrimitive", true);
              }
          }
      }
     
      ArrayNode menus = buildTheMenus(kidaCodegenModel, objectMapper.createArrayNode(), gson, allSchemas);
      kidaCodegenModel.panels = buildThePanels(kidaCodegenModel, new ArrayList<>(), gson, allSchemas,"");
      
      kidaCodegenModel.menus = menus.toPrettyString();
      return kidaCodegenModel;
  }
  private ArrayNode buildTheMenus(CodegenObject codegenObject, ArrayNode menus, Gson gson, Map<String, Schema> allSchemas) {
	  
	  menus = objectMapper.createArrayNode();
	  ObjectNode menuItem = objectMapper.createObjectNode();
	  List<CodegenProperty> properties = null;
	  if(codegenObject instanceof CodegenModel) {
		  menuItem.put("label", ((CodegenModel)codegenObject).getTitle());
		  if(((CodegenModel)codegenObject).getTitle() != null)
			  menus.add(menuItem);
		  
		  properties = ((CodegenModel)codegenObject).getAllVars();
		  
		  
	  }
	  
	  if(properties != null &&
		 properties.size() > 0) {
		  for(CodegenProperty propertyCodegenModel: properties) {
			  if (propertyCodegenModel.getComplexType() != null) {
					ObjectNode subMenuItem = objectMapper.createObjectNode();
					subMenuItem.put("label", splitCamelCase(propertyCodegenModel.getNameInCamelCase()));
					subMenuItem.put("id", propertyCodegenModel.getName());
					menus.add(subMenuItem);
					if (StringUtils.isNotEmpty(propertyCodegenModel.getComplexType())) {
						String type = minimizeFirstLetter(propertyCodegenModel.getComplexType());
						Schema currentSchema = allSchemas.get(type);
						CodegenModel refCodegenModel = super.fromModel(type,
								currentSchema, allSchemas);
						if (refCodegenModel.getAllVars() != null) {
							ArrayNode subOfSubMenue = buildTheMenus(refCodegenModel, objectMapper.createArrayNode(),
									gson, allSchemas);
							if(subOfSubMenue.size() > 0)
								subMenuItem.set("submenus", subOfSubMenue);
						}
					}
					
				}
		  }
		  
	  }
	  
	  return menus;
  }

	private List<KidaPanelCodegen> buildThePanels(CodegenObject codegenObject,
			List<KidaPanelCodegen> panels, Gson gson, Map<String, Schema> allSchemas, String parent) {
	  
	  List<CodegenProperty> properties = null;
	  if(codegenObject instanceof CodegenModel ) {
		  properties = ((CodegenModel)codegenObject).getAllVars();
		  
	  }
	  
	  if(properties != null &&
		 properties.size() > 0) {
		  for(CodegenProperty propertyCodegenModel: properties) {
			  if (propertyCodegenModel.getComplexType() != null) {
				    KidaPanelCodegen subPanel = new KidaPanelCodegen();
					subPanel.setFieldName(propertyCodegenModel.getName());
					subPanel.setSchemaName(propertyCodegenModel.getComplexType());
					subPanel.setMetadataSource(propertyCodegenModel.getName());
					subPanel.setLabel(splitCamelCase(propertyCodegenModel.getNameInCamelCase()));
					subPanel.setArray(propertyCodegenModel.getBaseType().equals("Array"));
					if(StringUtils.isNotBlank(parent))
						subPanel.setParent(parent+".");
					panels.add(subPanel);
					if (StringUtils.isNotEmpty(propertyCodegenModel.getComplexType())) {
						String type = minimizeFirstLetter(propertyCodegenModel.getComplexType());
						Schema currentSchema = allSchemas.get(type);
						CodegenModel refCodegenModel = super.fromModel(type,
								currentSchema, allSchemas);
						if (refCodegenModel.getAllVars() != null) {
							 buildThePanels(refCodegenModel, panels,
									gson, allSchemas, propertyCodegenModel.getName());
						}
					}
					
				}
		  }
		  
	  }
	  
	  return panels;
  }
	public static String splitCamelCase(String s) {
		// If the input string is null or empty, return an empty array
		if (s == null || s.isEmpty()) {
			return "";
		}

		// Split the string based on CamelCase using a regular expression
		// that matches any upper case character that is preceded by a lower
		// case character or a number, but not at the beginning of the string
		String[] parts = s.split("(?<=[a-z0-9])(?=[A-Z])");

		return String.join(" ", parts);
	}

	private static CodegenModel reconcileInlineEnums(CodegenModel codegenModel, CodegenModel parentCodegenModel) {
		// This generator uses inline classes to define enums, which breaks when
		// dealing with models that have subTypes. To clean this up, we will analyze
		// the parent and child models, look for enums that match, and remove
		// them from the child models and leave them in the parent.
		// Because the child models extend the parents, the enums will be available via
		// the parent.

		// Only bother with reconciliation if the parent model has enums.
		boolean hasEnums = getBooleanValue(parentCodegenModel, HAS_ENUMS_EXT_NAME);
		if (hasEnums) {

			// Get the properties for the parent and child models
			final List<CodegenProperty> parentModelCodegenProperties = parentCodegenModel.vars;
			List<CodegenProperty> codegenProperties = codegenModel.vars;

			// Iterate over all of the parent model properties
			boolean removedChildEnum = false;
			for (CodegenProperty parentModelCodegenProperty : parentModelCodegenProperties) {
				// Look for enums
				boolean isEnum = getBooleanValue(parentModelCodegenProperty, IS_ENUM_EXT_NAME);
				if (isEnum) {
					// Now that we have found an enum in the parent class,
					// and search the child class for the same enum.
					Iterator<CodegenProperty> iterator = codegenProperties.iterator();
					while (iterator.hasNext()) {
						CodegenProperty codegenProperty = iterator.next();
						if (getBooleanValue(codegenProperty, IS_ENUM_EXT_NAME)
								&& codegenProperty.equals(parentModelCodegenProperty)) {
							// We found an enum in the child class that is
							// a duplicate of the one in the parent, so remove it.
							iterator.remove();
							removedChildEnum = true;
						}
					}
				}
			}

			if (removedChildEnum) {
				// If we removed an entry from this model's vars, we need to ensure hasMore is
				// updated
				int count = 0, numVars = codegenProperties.size();
				for (CodegenProperty codegenProperty : codegenProperties) {
					count += 1;
					codegenProperty.getVendorExtensions().put(CodegenConstants.HAS_MORE_EXT_NAME,
							(count < numVars) ? true : false);
				}
				codegenModel.vars = codegenProperties;
			}
		}

		return codegenModel;
	}
	public static String minimizeFirstLetter(String input) {
	    if (input == null || input.isEmpty()) {
	        return input; // return the input string if it's null or empty
	    } else {
	        char firstLetter = Character.toLowerCase(input.charAt(0)); // get the first letter of the input string and convert it to lowercase
	        return firstLetter + input.substring(1); // concatenate the first letter and the rest of the string
	    }
	}
  private boolean isPrimitiveType(String type) {
      final String[] primitives = {"number", "integer", "string", "boolean", "null"};
      return Arrays.asList(primitives).contains(type);
  }
}