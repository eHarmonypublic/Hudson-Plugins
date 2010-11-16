package com.eharmony.hudson.plugins;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.export.Exported;
import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONObject;
import hudson.Extension;
import hudson.model.*;
import com.eharmony.hudson.plugins.Messages;
import java.util.ArrayList;

import java.util.List;
import java.util.Arrays;


/**
 * @author dmathew
 */
public class JobFilterChoiceParameterDefinition extends SimpleParameterDefinition {
    private  List<String> choices =new ArrayList<String>();
    
    private static final long serialVersionUID = -2946187268529865645L;


    @DataBoundConstructor
    public JobFilterChoiceParameterDefinition(String name, String choices, String description) {
        super(name, description);
        if (StringUtils.isNotBlank(choices))
        	this.choices = Arrays.asList(choices.split("\\r?\\n"));
	    else
          	throw new IllegalArgumentException("No choices found");
        
    }

    public JobFilterChoiceParameterDefinition(String name, String[] choices, String description) {
        super(name, description);
        if (choices != null && choices.length != 0)
            this.choices = new ArrayList<String>(Arrays.asList(choices));
	    else
          	throw new IllegalArgumentException("No choices found");
    }
    
    @Exported
    public final List<String> getChoices() {
    	// Add any validation if required .
        return choices;
    }

    public final String getChoicesText() {
        return StringUtils.join(choices, "\n");
    }

    @Override
    public final StringParameterValue getDefaultParameterValue() {
        return new StringParameterValue(getName(), choices.get(0), getDescription());
    }

   
    
    private StringParameterValue checkValue(StringParameterValue value) {
    	   	
        return value;
    }

    @Override
    public final ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        StringParameterValue value = req.bindJSON(StringParameterValue.class, jo);
        value.setDescription(getDescription());
        return checkValue(value);
    }

    public final StringParameterValue createValue(String value) {
        return checkValue(new StringParameterValue(getName(), value, getDescription()));
    }

    @Extension
    public static class DescriptorImpl extends ParameterDescriptor {
        @Override
        public final String getDisplayName() {
        	return Messages.JobFilterChoiceParameterDefinition_DisplayName();
		
        }

        @Override
        public final String getHelpFile() {
            return "/../plugin/jobfilter_choice_parameter/help/job-filter-choice-parameter.html";
        }
    }

}

