package com.alexjck.plugins.csv;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.graylog.plugins.pipelineprocessor.EvaluationContext;
import org.graylog.plugins.pipelineprocessor.ast.expressions.Expression;
import org.graylog.plugins.pipelineprocessor.ast.functions.Function;
import org.graylog.plugins.pipelineprocessor.ast.functions.FunctionArgs;
import org.graylog.plugins.pipelineprocessor.ast.functions.FunctionDescriptor;
import org.graylog.plugins.pipelineprocessor.ast.functions.ParameterDescriptor;

@SuppressWarnings("rawtypes")
public class CsvFunction implements Function<Map> {

	public static final String NAME = "csv";
	private static final String PARAM1 = "csv_fields";
	private static final String PARAM2 = "csv_text";
	private static final String PARAM3 = "csv_separator";
	private static final String PARAM4 = "dummy_value";

	private final ParameterDescriptor<String, String> valueParam1 = ParameterDescriptor.string(PARAM1)
			.description("The csv fields, separated by \"comma\" or csv_separator").build();
	private final ParameterDescriptor<String, String> valueParam2 = ParameterDescriptor.string(PARAM2)
			.description("A String containing the csv text").build();
	private final ParameterDescriptor<String, String> valueParam3 = ParameterDescriptor.string(PARAM3).optional()
			.description("The character that separates the csv, default \"comma\"").build();
	private final ParameterDescriptor<String, String> valueParam4 = ParameterDescriptor.string(PARAM4).optional()
			.description("Dummy value, if there is dummy value in the csv_fields that we don't want to save").build();

	@Override
	public Object preComputeConstantArgument(FunctionArgs functionArgs, String s, Expression expression) {
		return expression.evaluateUnsafe(EvaluationContext.emptyContext());
	}

	@Override
	public Map evaluate(FunctionArgs functionArgs, EvaluationContext evaluationContext) {
		final String target1 = valueParam1.required(functionArgs, evaluationContext);
		final String target2 = valueParam2.required(functionArgs, evaluationContext);
		final String target3 = valueParam3.optional(functionArgs, evaluationContext).orElse(",");
		final String target4 = valueParam4.optional(functionArgs, evaluationContext).orElse("");

		final Map<String, Object> retMap = new HashMap<String, Object>();
		final String[] fields = target1.split(Pattern.quote(target3));

		int startIndex = 0;
		int fieldIndex = 0;
		boolean insideQuotes = false;

		final String csvString = target2.replaceAll("\"\"", "");

		for (int i = 0; i < csvString.length(); i++) {
			if (fields.length <= fieldIndex) {
				return new HashMap<String, Object>();
			}
			if (csvString.substring(i, i + 1).equals("\"")) {
				insideQuotes = !insideQuotes;
			}
			if (!insideQuotes && csvString.substring(i, i + 1).equals(target3)) {
				String value = csvString.substring(startIndex, i);
				final String key = fields[fieldIndex].trim().replace(" ", "_");
				if (!value.equals("") && !target4.equals(key)) {
					// Remove quotes from quoted strings					
					if (value.substring(0,1).equals("\"") && value.substring(value.length() - 1, value.length()).equals("\"")) {
						value = value.substring(1, value.length() - 1);
					}
					retMap.put(key, value);
				}
				fieldIndex++;
				startIndex = i + 1;
			}
		}

		if (startIndex != csvString.length()) {
			if (fields.length <= fieldIndex) {
				return new HashMap<String, Object>();
			}
			String value = csvString.substring(startIndex, csvString.length());
			final String key = fields[fieldIndex].trim().replace(" ", "_");
			if (!value.equals("") && !target4.equals(key)) {
				// Remove quotes from quoted strings					
				if (value.substring(0,1).equals("\"") && value.substring(value.length() - 1, value.length()).equals("\"")) {
					value = value.substring(1, value.length() - 1);
				}
				retMap.put(key, value);
			}
		}

		return retMap;
	}

	@Override
	public FunctionDescriptor<Map> descriptor() {
		return FunctionDescriptor.<Map>builder().name(NAME)
				.description("Returns a map with csv_fields as keys and parsed csv_text as values")
				.params(valueParam1, valueParam2, valueParam3, valueParam4).returnType(Map.class).build();
	}

}
