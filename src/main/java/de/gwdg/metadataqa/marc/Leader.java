package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.utils.keygenerator.PositionalControlFieldKeyGenerator;

import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Leader implements Extractable, Validatable {

	private static final Logger logger = Logger.getLogger(Leader.class.getCanonicalName());

	private String tag = "Leader";
	private String mqTag = "Leader";

	public enum Type {
		BOOKS("Books"),
		CONTINUING_RESOURCES("Continuing Resources"),
		MUSIC("Music"),
		MAPS("Maps"),
		VISUAL_MATERIALS("Visual Materials"),
		COMPUTER_FILES("Computer Files"),
		MIXED_MATERIALS("Mixed Materials");

		String value;
		Type(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	private MarcRecord marcRecord;
	private Type type;
	private String content;
	private Map<ControlSubfield, String> valuesMap;
	private List<ControlValue> valuesList;

	private ControlValue recordLength;
	private ControlValue recordStatus;
	private ControlValue typeOfRecord;
	private ControlValue bibliographicLevel;
	private ControlValue typeOfControl;
	private ControlValue characterCodingScheme;
	private ControlValue indicatorCount;
	private ControlValue subfieldCodeCount;
	private ControlValue baseAddressOfData;
	private ControlValue encodingLevel;
	private ControlValue descriptiveCatalogingForm;
	private ControlValue multipartResourceRecordLevel;
	private ControlValue lengthOfTheLengthOfFieldPortion;
	private ControlValue lengthOfTheStartingCharacterPositionPortion;
	private ControlValue lengthOfTheImplementationDefinedPortion;
	private List<String> errors;
	private List<ValidationError> validationErrors;

	public Leader(String content) {
		this.content = content;
		valuesMap = new LinkedHashMap<>();
		valuesList = new ArrayList<>();
		process();
		setType();
	}

	private void process() {
		for (ControlSubfield subfield : LeaderSubfields.getSubfields()) {
			int end = Math.min(content.length(), subfield.getPositionEnd());
			try {
				String value = content.substring(subfield.getPositionStart(), end);
				ControlValue controlValue = new ControlValue(subfield, value);
				valuesList.add(controlValue);

				switch (subfield.getId()) {
					case "leader00": recordLength = controlValue; break;
					case "leader05": recordStatus = controlValue; break;
					case "leader06": typeOfRecord = controlValue; break;
					case "leader07": bibliographicLevel = controlValue; break;
					case "leader08": typeOfControl = controlValue; break;
					case "leader09": characterCodingScheme = controlValue; break;
					case "leader10": indicatorCount = controlValue; break;
					case "leader11": subfieldCodeCount = controlValue; break;
					case "leader12": baseAddressOfData = controlValue; break;
					case "leader17": encodingLevel = controlValue; break;
					case "leader18": descriptiveCatalogingForm = controlValue; break;
					case "leader19": multipartResourceRecordLevel = controlValue; break;
					case "leader20": lengthOfTheLengthOfFieldPortion = controlValue; break;
					case "leader21": lengthOfTheStartingCharacterPositionPortion = controlValue; break;
					case "leader22": lengthOfTheImplementationDefinedPortion = controlValue; break;
					default:
						break;
				}
				valuesMap.put(subfield, value);
			} catch (StringIndexOutOfBoundsException e) {
				logger.severe(String.format("Problem with processing Leader ('%s'). " +
						"The content length is only %d while reading position @%d-%d (for %s)",
					content,
					content.length(), subfield.getPositionStart(), subfield.getPositionEnd(), subfield.getLabel()));
			}
		}
	}

	private void setType() {
		if (typeOfRecord.getValue().equals("a") && bibliographicLevel.getValue().matches("^(a|c|d|m)$")) {
			type = Type.BOOKS;
		} else if (typeOfRecord.getValue().equals("a") && bibliographicLevel.getValue().matches("^(b|i|s)$")) {
			type = Type.CONTINUING_RESOURCES;
		} else if (typeOfRecord.getValue().equals("t")) {
			type = Type.BOOKS;
		} else if (typeOfRecord.getValue().matches("^[cdij]$")) {
			type = Type.MUSIC;
		} else if (typeOfRecord.getValue().matches("^[ef]$")) {
			type = Type.MAPS;
		} else if (typeOfRecord.getValue().matches("^[gkor]$")) {
			type = Type.VISUAL_MATERIALS;
		} else if (typeOfRecord.getValue().equals("m")) {
			type = Type.COMPUTER_FILES;
		} else if (typeOfRecord.getValue().equals("p")) {
			type = Type.MIXED_MATERIALS;
		} else {
			throw new IllegalArgumentException(
				String.format(
					"No type is detectable. typeOfRecord: '%s', bibliographicLevel: '%s'",
					typeOfRecord.getValue(), bibliographicLevel.getValue()));
		}
	}

	public String resolve(ControlSubfield key) {
		String value = valuesMap.get(key);
		String text = key.resolve(value);
		return text;
	}

	public String resolve(String key) {
		return resolve(LeaderSubfields.getByLabel(key));
	}

	public Map<ControlSubfield, String> getMap() {
		return valuesMap;
	}

	public String get(ControlSubfield key) {
		return valuesMap.get(key);
	}

	public String getByLabel(String key) {
		return get(LeaderSubfields.getByLabel(key));
	}
	public String getById(String key) {
		return get(LeaderSubfields.getById(key));
	}

	/**
	 * Return Tpye
	 * @return
	 */
	public Type getType() {
		return type;
	}

	public String getLeaderString() {
		return content;
	}

	public ControlValue getRecordLength() {
		return recordLength;
	}

	public ControlValue getRecordStatus() {
		return recordStatus;
	}

	public ControlValue getTypeOfRecord() {
		return typeOfRecord;
	}

	public ControlValue getBibliographicLevel() {
		return bibliographicLevel;
	}

	public ControlValue getTypeOfControl() {
		return typeOfControl;
	}

	public ControlValue getCharacterCodingScheme() {
		return characterCodingScheme;
	}

	public ControlValue getIndicatorCount() {
		return indicatorCount;
	}

	public ControlValue getSubfieldCodeCount() {
		return subfieldCodeCount;
	}

	public ControlValue getBaseAddressOfData() {
		return baseAddressOfData;
	}

	public ControlValue getEncodingLevel() {
		return encodingLevel;
	}

	public ControlValue getDescriptiveCatalogingForm() {
		return descriptiveCatalogingForm;
	}

	public ControlValue getMultipartResourceRecordLevel() {
		return multipartResourceRecordLevel;
	}

	public ControlValue getLengthOfTheLengthOfFieldPortion() {
		return lengthOfTheLengthOfFieldPortion;
	}

	public ControlValue getLengthOfTheStartingCharacterPositionPortion() {
		return lengthOfTheStartingCharacterPositionPortion;
	}

	public ControlValue getLengthOfTheImplementationDefinedPortion() {
		return lengthOfTheImplementationDefinedPortion;
	}

	protected void setMarcRecord(MarcRecord marcRecord) {
		this.marcRecord = marcRecord;
		for (ControlValue value : valuesList)
			value.setRecord(marcRecord);
	}

	public String toString() {
		String output = String.format( "type: %s\n", type.getValue());
		for (ControlSubfield key : LeaderSubfields.getSubfields()) {
			output += String.format("%s: %s\n", key.getLabel(), resolve(key));
		}
		return output;
	}

	@Override
	public Map<String, List<String>> getKeyValuePairs() {
		return getKeyValuePairs(SolrFieldType.MARC);
	}

	public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
		Map<String, List<String>> map = new LinkedHashMap<>();
		PositionalControlFieldKeyGenerator keyGenerator = new PositionalControlFieldKeyGenerator(
			tag, mqTag, type);
		map.put(keyGenerator.forTag(), Arrays.asList(content));
		for (ControlSubfield controlSubfield : valuesMap.keySet()) {
			String value = controlSubfield.resolve(valuesMap.get(controlSubfield));
			map.put(keyGenerator.forSubfield(controlSubfield), Arrays.asList(value));
		}
		return map;
	}

	@Override
	public boolean validate(MarcVersion marcVersion) {
		boolean isValid = true;
		errors = new ArrayList<>();
		validationErrors = new ArrayList<>();

		for (ControlValue controlValue : valuesList) {
			if (!controlValue.validate(marcVersion)) {
				errors.addAll(controlValue.getErrors());
				validationErrors.addAll(controlValue.getValidationErrors());
				isValid = false;
			}
		}

		return isValid;
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

}
