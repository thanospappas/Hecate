package gr.uoi.cs.daintiness.hecate.output;
import gr.uoi.cs.daintiness.hecate.metrics.tables.MetricsOverVersion;

public class TableStatsExporter extends TableMetricsExporter{
	
	private int sumSize = 0;
	private int versionsAlive = 0;
	private MetricsOverVersion mov;
	private int versions;

	
	public TableStatsExporter(String path,int versions) {
		super(path, "table_stats.csv");
		this.versions = versions;
	}

	@Override
	public void writeChanges(MetricsOverVersion metricsOverVersion, int i) {
		this.mov = metricsOverVersion;
		sumSize += metricsOverVersion.getSize(i);
		
		if(i == metricsOverVersion.getBirth()){
			writeText(metricsOverVersion.getLife() + ";");
			writeText(metricsOverVersion.getBirth() + ";");

			writeText((metricsOverVersion.getDeath()==versions-1 ? "-" : metricsOverVersion.getDeath())
				+ ";");
	
		}
		versionsAlive++;
	}


	@Override
	public void writeLastColumn() {
		
		super.writeText(mov.getTotalChanges().getTotal() + ";");
		super.writeText(mov.getBirthSize() + ";");
		super.writeText(mov.getDeathSize() + ";");
		super.writeText(Float.toString((sumSize/(float)versionsAlive)));
		super.writeText("\n");
		versionsAlive = 0;
		sumSize= 0;	
	}

	@Override
	public void writeEmptyCells() {	}

	@Override
	public void writeHeader(int versions) {
		writeText("table;dur;birth;death;chngs;s@s;s@e;sAvg\n");
	}

}
