package com.alexjck.plugins.csv;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.graylog.plugins.pipelineprocessor.EvaluationContext;
import org.graylog.plugins.pipelineprocessor.ast.expressions.Expression;
import org.graylog.plugins.pipelineprocessor.ast.functions.Function;
import org.graylog.plugins.pipelineprocessor.ast.functions.FunctionArgs;
import org.graylog.plugins.pipelineprocessor.ast.functions.FunctionDescriptor;
import org.graylog2.plugin.Message;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CsvFunctionTest {
	
	FunctionArgs functionArgs;
	CsvFunction function;
	Message message;
	EvaluationContext evaluationContext;
	
    @Before
    public void setUp() {
    	function = new CsvFunction();
    	message = new Message("__dummy", "__dummy", DateTime.parse("2010-07-30T16:03:25Z"));
    	evaluationContext = new EvaluationContext(message);
    	functionArgs = new FunctionArgs(new Function<String>() {

			@Override
			public Object preComputeConstantArgument(FunctionArgs args, String name, Expression arg) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String evaluate(FunctionArgs args, EvaluationContext context) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public FunctionDescriptor<String> descriptor() {
				// TODO Auto-generated method stub
				return null;
			}
		}, null);
    }
 
    @Test
    public void testWithFieldsAndCsvValuesSameLengthAndDefaultSeparator(){
    	setFields("a,b,c,d,e,f", "a,b,c,d,e,f", null, null);
		
		@SuppressWarnings("unchecked")
		final Map<String, Object> resultMap = function.evaluate(functionArgs, evaluationContext);
		
		final Map<String, Object> expectedMap = new HashMap<String,Object>();
		expectedMap.put("a", "a");
		expectedMap.put("b", "b");
		expectedMap.put("c", "c");
		expectedMap.put("d", "d");
		expectedMap.put("e", "e");
		expectedMap.put("f", "f");
		
		assertEquals(resultMap, expectedMap);
	}
    
    @Test
    public void testWithFieldsWithQuotesAndCsvValuesSameLengthAndPipeSeparator(){
    	setFields("a|b|c|d|e|f", "\"a\"|b|c|\"d\"|e|\"f\"", "|", null);
		
		@SuppressWarnings("unchecked")
		final Map<String, Object> resultMap = function.evaluate(functionArgs, evaluationContext);
		
		final Map<String, Object> expectedMap = new HashMap<String,Object>();
		expectedMap.put("a", "a");
		expectedMap.put("b", "b");
		expectedMap.put("c", "c");
		expectedMap.put("d", "d");
		expectedMap.put("e", "e");
		expectedMap.put("f", "f");
		
		assertEquals(expectedMap, resultMap);
	}
    
    @Test
    public void testWithFieldsAndCsvValuesSameLengthAndPipeSeparator(){
    	setFields("a|b|c|d|e|f", "a|b|c|d|e|f", "|", null);
		
		@SuppressWarnings("unchecked")
		final Map<String, Object> resultMap = function.evaluate(functionArgs, evaluationContext);
		
		final Map<String, Object> expectedMap = new HashMap<String,Object>();
		expectedMap.put("a", "a");
		expectedMap.put("b", "b");
		expectedMap.put("c", "c");
		expectedMap.put("d", "d");
		expectedMap.put("e", "e");
		expectedMap.put("f", "f");
		
		assertEquals(expectedMap, resultMap);
	}
    
	@Test
	public void testWithFieldsAndCsvValuesSameLengthAndCommaSeparator(){
		
		setFields("a,b,c,d,e,f", "a,b,c,d,e,f", ",", null);
		@SuppressWarnings("unchecked")
		final Map<String, Object> resultMap = function.evaluate(functionArgs, evaluationContext);
		final Map<String, Object> expectedMap = new HashMap<String,Object>();
		expectedMap.put("a", "a");
		expectedMap.put("b", "b");
		expectedMap.put("c", "c");
		expectedMap.put("d", "d");
		expectedMap.put("e", "e");
		expectedMap.put("f", "f");
		
		assertEquals(expectedMap, resultMap);
	}
	
	public void setFields(String csv_fields, String csv_text, String csv_separator, String dummy_value) {
		functionArgs.setPreComputedValue("csv_fields", csv_fields);
		functionArgs.setPreComputedValue("csv_text", csv_text);
		if (csv_separator != null) {			
			functionArgs.setPreComputedValue("csv_separator", csv_separator);
		}
		if (dummy_value != null) {
			functionArgs.setPreComputedValue("dummy_value", dummy_value);
		}
	}
}
