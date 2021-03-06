package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.solr.client.solrj.SolrServerException;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.Validator [MARC21 file]
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class RecordIterator {

	private static final Logger logger = Logger.getLogger(RecordIterator.class.getCanonicalName());
	private static Options options;
	private MarcFileProcessor processor;

	public RecordIterator(MarcFileProcessor processor) {
		this.processor = processor;
	}

	public void start() {

		long start = System.currentTimeMillis();
		processor.beforeIteration();
		String[] inputFileNames = processor.getParameters().getArgs();

		int i = 0;
		String lastKnownId = "";
		for (String inputFileName : inputFileNames) {
			Path path = Paths.get(inputFileName);
			String fileName = path.getFileName().toString();

			if (processor.getParameters().doLog())
				logger.info("processing: " + fileName);

			try {
				MarcReader reader = ReadMarc.getReader(path.toString());
				while (reader.hasNext()) {
					Record marc4jRecord = reader.next();
					i++;
					if (isUnderOffset(processor.getParameters().getOffset(), i)) {
						continue;
					}
					if (isOverLimit(processor.getParameters().getLimit(), i)) {
						break;
					}

					if (marc4jRecord.getControlNumber() == null) {
						logger.severe("No record number at " + i + ", last known ID: " + lastKnownId);
						System.err.println(marc4jRecord);
						continue;
					} else {
						lastKnownId = marc4jRecord.getControlNumber();
					}

					if (processor.getParameters().hasId() && !marc4jRecord.getControlNumber().equals(processor.getParameters().getId()))
						continue;

					try {
						MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
						processor.processRecord(marcRecord, i);

						if (i % 100000 == 0 && processor.getParameters().doLog())
							logger.info(String.format("%s/%d (%s)", fileName, i, marcRecord.getId()));
					} catch (IllegalArgumentException e) {
						if (marc4jRecord.getControlNumber() == null)
							logger.severe("No record number at " + i);
						if (processor.getParameters().doLog())
							logger.severe(String.format("Error with record '%s'. %s", marc4jRecord.getControlNumber(), e.getMessage()));
						continue;
					}
				}
				if (processor.getParameters().doLog())
					logger.info(String.format("Finished processing file. Validated %d records.", i));

			} catch(SolrServerException ex){
				if (processor.getParameters().doLog())
					logger.severe(ex.toString());
				System.exit(0);
			} catch(Exception ex){
				if (processor.getParameters().doLog())
					logger.severe(ex.toString());
				ex.printStackTrace();
				System.exit(0);
			}
		}

		processor.afterIteration();

		long end = System.currentTimeMillis();
		long duration = (end - start) / 1000;
		if (processor.getParameters().doLog())
			logger.info(String.format("Bye! It took: %s",
				LocalTime.MIN.plusSeconds(duration).toString()));

		System.exit(0);
	}

	private static boolean isOverLimit(int limit, int i) {
		return limit > -1 && i > limit;
	}

	private static boolean isUnderOffset(int offset, int i) {
		return offset > -1 && i < offset;
	}

	private static void printHelp(Options opions) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -cp metadata-qa-marc.jar de.gwdg.metadataqa.marc.cli.Validator [options] [file]",
			opions);
	}
}